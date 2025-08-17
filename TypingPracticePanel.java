import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TypingPracticePanel extends JPanel {

    private JTextPane lessonTextPane;
    private JTextArea typingArea;
    private JLabel wpmLabel, accuracyLabel, errorsLabel, goalLabel;
    private JLabel lineProgressLabel;
    private JButton prevButton, nextButton, resetButton, highScoreButton, dailyProgressButton;
    private JComboBox<String> lessonSelector;
    private LessonManager lessonManager;
    private TypingLesson currentLesson;
    private int currentLessonIndex;
    private long startTime;
    private int totalErrors;

    private JComboBox<String> goalSelector;
    private JProgressBar goalProgressBar;
    private int dailyGoalMinutes = 15;

    private JComboBox<String> themeSelector;
    private Color bgColor, panelColor, buttonColor, buttonHover, textCorrect, textError, textDark;

    private java.util.List<String> lessonLines;
    private int currentLineIndex = 0;
    private StringBuilder typedSoFar = new StringBuilder();

    private Map<String, Integer> highScores;
    private final File highScoreFile = new File("highscores.txt");

    private StatsTracker statsTracker = new StatsTracker();

    public TypingPracticePanel() {
        loadHighScores();
        setTheme("Light");

        setLayout(new BorderLayout(15, 15));
        setBackground(bgColor);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        lessonManager = new LessonManager();

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(panelColor);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        lessonSelector = new JComboBox<>();
        for (TypingLesson lesson : lessonManager.getLessons()) {
            lessonSelector.addItem(lesson.isUnlocked() ? lesson.getTitle() : "🔒 " + lesson.getTitle());
        }
        topPanel.add(lessonSelector);

        prevButton = createStyledButton("Prev");
        nextButton = createStyledButton("Next");
        resetButton = createStyledButton("Reset");
        topPanel.add(prevButton);
        topPanel.add(nextButton);
        topPanel.add(resetButton);

        themeSelector = new JComboBox<>(new String[]{"Light", "Dark"});
        topPanel.add(themeSelector);
        add(topPanel, BorderLayout.NORTH);

        // Center panel with lesson pane and progress label
        JPanel lessonPanel = new JPanel(new BorderLayout());
        lessonPanel.setBackground(panelColor);

        lessonTextPane = new JTextPane();
        lessonTextPane.setEditable(false);
        lessonTextPane.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lessonTextPane.setBackground(panelColor);
        lessonTextPane.setForeground(textDark);
        lessonTextPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        lessonPanel.add(new JScrollPane(lessonTextPane), BorderLayout.CENTER);

        lineProgressLabel = new JLabel("Line 1 of 1");
        lineProgressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lineProgressLabel.setForeground(textDark);
        lineProgressLabel.setBorder(new EmptyBorder(4, 4, 4, 4));
        lessonPanel.add(lineProgressLabel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBackground(bgColor);
        centerPanel.add(lessonPanel);

        typingArea = new JTextArea();
        typingArea.setLineWrap(true);
        typingArea.setWrapStyleWord(true);
        typingArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        typingArea.setBackground(panelColor);
        typingArea.setForeground(textDark);
        typingArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        centerPanel.add(new JScrollPane(typingArea));
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        bottomPanel.setBackground(panelColor);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        statsPanel.setBackground(panelColor);
        wpmLabel = createStatLabel("WPM: 0");
        accuracyLabel = createStatLabel("Accuracy: 100%");
        errorsLabel = createStatLabel("Errors: 0");
        statsPanel.add(wpmLabel);
        statsPanel.add(accuracyLabel);
        statsPanel.add(errorsLabel);

        highScoreButton = createStyledButton("High Scores");
        statsPanel.add(highScoreButton);

        dailyProgressButton = createStyledButton("Daily Progress");
        statsPanel.add(dailyProgressButton);

        JPanel goalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        goalPanel.setBackground(panelColor);
        goalLabel = createStatLabel("Daily Goal: 15 min");
        goalSelector = new JComboBox<>(new String[]{"15", "20", "30"});
        goalSelector.setSelectedIndex(0);
        goalProgressBar = new JProgressBar(0, dailyGoalMinutes * 60);
        goalProgressBar.setStringPainted(true);
        goalProgressBar.setForeground(new Color(0x20c997));
        goalPanel.add(goalLabel);
        goalPanel.add(goalSelector);
        goalPanel.add(goalProgressBar);

        bottomPanel.add(statsPanel);
        bottomPanel.add(goalPanel);
        add(bottomPanel, BorderLayout.SOUTH);

        currentLessonIndex = 0;
        loadLesson(currentLessonIndex);

        addListeners();
        addKeyboardShortcuts();
        updateTheme();
    }

    private void addListeners() {
        lessonSelector.addActionListener(e -> {
            int index = lessonSelector.getSelectedIndex();
            java.util.List<TypingLesson> lessons = lessonManager.getLessons();
            if (lessons.get(index).isUnlocked()) {
                currentLessonIndex = index;
                loadLesson(currentLessonIndex);
            }
        });

        prevButton.addActionListener(e -> {
            if (currentLessonIndex > 0) {
                currentLessonIndex--;
                lessonSelector.setSelectedIndex(currentLessonIndex);
                loadLesson(currentLessonIndex);
            }
        });

        nextButton.addActionListener(e -> {
            java.util.List<TypingLesson> lessons = lessonManager.getLessons();
            if (currentLessonIndex < lessons.size() - 1
                    && lessons.get(currentLessonIndex + 1).isUnlocked()) {
                currentLessonIndex++;
                lessonSelector.setSelectedIndex(currentLessonIndex);
                loadLesson(currentLessonIndex);
            }
        });

        resetButton.addActionListener(e -> loadLesson(currentLessonIndex));

        typingArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateTypingFeedback();
            }
        });

        goalSelector.addActionListener(e -> {
            dailyGoalMinutes = Integer.parseInt((String) goalSelector.getSelectedItem());
            goalLabel.setText("Daily Goal: " + dailyGoalMinutes + " min");
            goalProgressBar.setMaximum(dailyGoalMinutes * 60);
        });

        themeSelector.addActionListener(e -> {
            setTheme((String) themeSelector.getSelectedItem());
            updateTheme();
        });

        highScoreButton.addActionListener(e -> showHighScores());
        dailyProgressButton.addActionListener(e -> showDailyProgress());
    }

    private void addKeyboardShortcuts() {
        InputMap im = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), "nextLesson");
        am.put("nextLesson", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextButton.doClick();
            }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), "prevLesson");
        am.put("prevLesson", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prevButton.doClick();
            }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "resetLesson");
        am.put("resetLesson", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetButton.doClick();
            }
        });
    }

    // ========== CORE METHODS ==========
    private void loadLesson(int index) {
        java.util.List<TypingLesson> lessons = lessonManager.getLessons();
        if (index >= 0 && index < lessons.size()) {
            currentLesson = lessons.get(index);
            lessonLines = Arrays.asList(currentLesson.getContent().split("\n"));
            currentLineIndex = 0;
            typedSoFar = new StringBuilder();
            typingArea.setText("");
            typingArea.setEditable(true);
            typingArea.requestFocus();
            totalErrors = 0;
            startTime = System.currentTimeMillis();
            updateLessonTextPane();
            updateStats();
        }
    }

    private void updateLessonTextPane() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < currentLineIndex; i++) {
            sb.append(lessonLines.get(i)).append("\n");
        }
        if (currentLineIndex < lessonLines.size())
            sb.append(lessonLines.get(currentLineIndex));
        lessonTextPane.setText(sb.toString());

        lineProgressLabel.setText("Line " + Math.min(currentLineIndex + 1, lessonLines.size()) + " of " + lessonLines.size());
    }

    private void updateTypingFeedback() {
        if (!typingArea.isEditable()) {
            return;
        }

        if (currentLesson == null || lessonLines == null || currentLineIndex > lessonLines.size())
            return;

        String typed = typingArea.getText();

        // Check if errors exceed the limit
        if (totalErrors >= 100) {
            JOptionPane.showMessageDialog(this,
                    "You have reached 100 errors. Please retry the lesson.",
                    "Too Many Errors", JOptionPane.WARNING_MESSAGE);
            loadLesson(currentLessonIndex);
            return;
        }

        if (currentLineIndex == lessonLines.size()) {
            // Full lesson done, check accuracy
            double accuracy = StatsTracker.calculateAccuracy(
                    typedSoFar.toString(),
                    totalErrors,
                    currentLesson.getContent().length()
            );
            if (accuracy == 100D) {
                typingArea.setEditable(false);
                unlockNextLesson();
                JOptionPane.showMessageDialog(this,
                        "Congratulations! You have completed " + currentLesson.getTitle() +
                                " with 100% accuracy. The next lesson is now unlocked.",
                        "Lesson Completed", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "You finished " + currentLesson.getTitle() +
                                " with some errors. Please try again to unlock the next lesson.",
                        "Try Again", JOptionPane.WARNING_MESSAGE);
                loadLesson(currentLessonIndex);
            }
            return;
        }

        String currentLine = lessonLines.get(currentLineIndex);

        StyledDocument doc = lessonTextPane.getStyledDocument();
        SimpleAttributeSet correctAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(correctAttr, textCorrect);
        SimpleAttributeSet wrongAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(wrongAttr, textError);
        SimpleAttributeSet defaultAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(defaultAttr, textDark);

        doc.setCharacterAttributes(0, lessonTextPane.getText().length(), defaultAttr, true);

        int offset = 0;
        for (int i = 0; i < currentLineIndex; i++) {
            String line = lessonLines.get(i) + "\n";
            doc.setCharacterAttributes(offset, line.length(), correctAttr, false);
            offset += line.length();
        }

        int lineErrors = 0;
        for (int i = 0; i < typed.length(); i++) {
            if (i >= currentLine.length()) break;
            if (typed.charAt(i) == currentLine.charAt(i)) {
                doc.setCharacterAttributes(offset + i, 1, correctAttr, false);
            } else {
                doc.setCharacterAttributes(offset + i, 1, wrongAttr, false);
                lineErrors++;
            }
        }

        // Highlight next char to type, if any
        if (typed.length() < currentLine.length()) {
            int nextCharPos = offset + typed.length();
            SimpleAttributeSet nextCharAttr = new SimpleAttributeSet();
            StyleConstants.setBackground(nextCharAttr, new Color(0xFFFF99)); // subtle yellow
            doc.setCharacterAttributes(nextCharPos, 1, nextCharAttr, false);
        }

        totalErrors = lineErrors;

        if (typed.equals(currentLine)) {
            typedSoFar.append(currentLine).append("\n");
            currentLineIndex++;
            typingArea.setText("");
            updateLessonTextPane();
        }

        updateStats();
    }

    private void unlockNextLesson() {
        int nextIndex = currentLessonIndex + 1;
        java.util.List<TypingLesson> lessons = lessonManager.getLessons();
        if (nextIndex < lessons.size()) {
            TypingLesson nextLesson = lessons.get(nextIndex);
            if (!nextLesson.isUnlocked()) {
                nextLesson.setUnlocked(true);
                lessonSelector.removeItemAt(nextIndex);
                lessonSelector.insertItemAt(nextLesson.getTitle(), nextIndex);
            }
        }
        saveHighScores();
    }

    private void updateStats() {
        long elapsedMillis = System.currentTimeMillis() - startTime;
        String typedText = typedSoFar.toString() + typingArea.getText();

        int wpm = StatsTracker.calculateWPM(typedText, elapsedMillis);
        int accuracy = StatsTracker.calculateAccuracy(typedText, totalErrors, currentLesson.getContent().length());

        wpmLabel.setText("WPM: " + Math.max(0, wpm));
        accuracyLabel.setText("Accuracy: " + Math.max(0, Math.min(100, accuracy)) + "%");
        errorsLabel.setText("Errors: " + totalErrors);

        int elapsedSeconds = (int) (elapsedMillis / 1000);
        goalProgressBar.setValue(Math.min(elapsedSeconds, dailyGoalMinutes * 60));
        goalProgressBar.setString((goalProgressBar.getValue() / 60) + " / " + dailyGoalMinutes + " min");

        statsTracker.recordSession(currentLesson.getTitle(), wpm, totalErrors, elapsedSeconds);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x6C757D), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { button.setBackground(buttonHover); }
            public void mouseExited(MouseEvent evt) { button.setBackground(buttonColor); }
        });
        return button;
    }

    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(textDark);
        return label;
    }

    private void setTheme(String theme) {
        switch (theme) {
            case "Dark":
                bgColor = Color.decode("#343a40");
                panelColor = Color.decode("#495057");
                buttonColor = Color.decode("#17A2B8");
                buttonHover = Color.decode("#117a8b");
                textCorrect = Color.decode("#28a745");
                textError = Color.decode("#dc3545");
                textDark = Color.WHITE;
                break;
            default:
                bgColor = Color.decode("#F8F9FA");
                panelColor = Color.WHITE;
                buttonColor = Color.decode("#007BFF");
                buttonHover = Color.decode("#0056b3");
                textCorrect = Color.decode("#28a745");
                textError = Color.decode("#dc3545");
                textDark = Color.decode("#212529");
        }
    }

    private void updateTheme() {
        setBackground(bgColor);
        lessonTextPane.setBackground(panelColor);
        lessonTextPane.setForeground(textDark);
        typingArea.setBackground(panelColor);
        typingArea.setForeground(textDark);
        prevButton.setBackground(buttonColor);
        nextButton.setBackground(buttonColor);
        resetButton.setBackground(buttonColor);
        highScoreButton.setBackground(buttonColor);
        dailyProgressButton.setBackground(buttonColor);
        prevButton.setForeground(Color.WHITE);
        nextButton.setForeground(Color.WHITE);
        resetButton.setForeground(Color.WHITE);
        highScoreButton.setForeground(Color.WHITE);
        dailyProgressButton.setForeground(Color.WHITE);
        repaint();
    }

    private void showHighScores() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : highScores.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" WPM\n");
        }
        JOptionPane.showMessageDialog(this,
                sb.length() > 0 ? sb.toString() : "No high scores yet!",
                "High Scores",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showDailyProgress() {
        StatsTracker.DailyStats stats = statsTracker.getTodayStats();
        String message = "Today's Progress:\n"
                + "Time Practiced: " + (stats.timePracticedSeconds / 60) + " min\n"
                + "Highest WPM: " + stats.highWPM + "\n"
                + "Total Errors: " + stats.totalErrors;
        JOptionPane.showMessageDialog(this, message, "Daily Progress", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadHighScores() {
        highScores = new HashMap<>();
        if (!highScoreFile.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(highScoreFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) highScores.put(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void saveHighScores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(highScoreFile))) {
            for (Map.Entry<String, Integer> entry : highScores.entrySet()) {
                bw.write(entry.getKey() + "=" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}
