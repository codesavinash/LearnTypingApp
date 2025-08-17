import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TypingPracticePanel extends JPanel {
    private JTextPane lessonTextPane;
    private JTextArea typingArea;
    private JLabel wpmLabel, accuracyLabel, errorsLabel;
    private JComboBox<String> lessonSelector;
    private LessonManager lessonManager;
    private TypingLesson currentLesson;
    private long startTime;
    private int totalErrors;

    public TypingPracticePanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));

        lessonManager = new LessonManager();

        // Top panel for lesson selection
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(230, 230, 250));
        topPanel.add(new JLabel("Select Lesson:"));

        lessonSelector = new JComboBox<>();
        for (TypingLesson lesson : lessonManager.getLessons()) {
            lessonSelector.addItem(lesson.getTitle());
        }
        topPanel.add(lessonSelector);
        add(topPanel, BorderLayout.NORTH);

        // Center panel for lesson text and typing area
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        lessonTextPane = new JTextPane();
        lessonTextPane.setEditable(false);
        lessonTextPane.setFont(new Font("Monospaced", Font.BOLD, 16));
        lessonTextPane.setBackground(new Color(250, 250, 210));
        centerPanel.add(new JScrollPane(lessonTextPane));

        typingArea = new JTextArea();
        typingArea.setLineWrap(true);
        typingArea.setWrapStyleWord(true);
        typingArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        typingArea.setBackground(Color.WHITE);
        centerPanel.add(new JScrollPane(typingArea));

        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for stats
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statsPanel.setBackground(new Color(230, 230, 250));
        wpmLabel = new JLabel("WPM: 0");
        accuracyLabel = new JLabel("Accuracy: 100%");
        errorsLabel = new JLabel("Errors: 0");
        statsPanel.add(wpmLabel);
        statsPanel.add(Box.createHorizontalStrut(15));
        statsPanel.add(accuracyLabel);
        statsPanel.add(Box.createHorizontalStrut(15));
        statsPanel.add(errorsLabel);

        add(statsPanel, BorderLayout.SOUTH);

        // Load the first lesson by default
        loadLesson(0);

        // Lesson selection listener
        lessonSelector.addActionListener(e -> loadLesson(lessonSelector.getSelectedIndex()));

        // Typing listener for real-time feedback
        typingArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateTypingFeedback();
            }
        });
    }

    private void loadLesson(int index) {
        List<TypingLesson> lessons = lessonManager.getLessons();
        if (index >= 0 && index < lessons.size()) {
            currentLesson = lessons.get(index);
            lessonTextPane.setText(currentLesson.getContent());
            typingArea.setText("");
            typingArea.requestFocus();
            totalErrors = 0;
            startTime = System.currentTimeMillis();
            updateStats();
        }
    }

    private void updateTypingFeedback() {
        String typed = typingArea.getText();
        String lessonContent = currentLesson.getContent();

        StyledDocument doc = lessonTextPane.getStyledDocument();
        SimpleAttributeSet correctAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(correctAttr, Color.GREEN.darker());

        SimpleAttributeSet wrongAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(wrongAttr, Color.RED);

        // Clear formatting
        doc.setCharacterAttributes(0, lessonContent.length(), new SimpleAttributeSet(), true);

        totalErrors = 0;
        for (int i = 0; i < typed.length(); i++) {
            if (i >= lessonContent.length()) break;
            if (typed.charAt(i) == lessonContent.charAt(i)) {
                doc.setCharacterAttributes(i, 1, correctAttr, false);
            } else {
                doc.setCharacterAttributes(i, 1, wrongAttr, false);
                totalErrors++;
            }
        }

        updateStats();
    }

    private void updateStats() {
        long elapsedMillis = System.currentTimeMillis() - startTime;
        double minutes = elapsedMillis / 60000.0;

        String typedText = typingArea.getText();
        int wordsTyped = typedText.split("\\s+").length;

        int wpm = (int)(wordsTyped / minutes);
        int totalChars = currentLesson.getContent().length();
        int correctChars = Math.max(0, typedText.length() - totalErrors);
        int accuracy = (int)((correctChars / (double)totalChars) * 100);

        wpmLabel.setText("WPM: " + Math.max(0, wpm));
        accuracyLabel.setText("Accuracy: " + Math.max(0, Math.min(100, accuracy)) + "%");
        errorsLabel.setText("Errors: " + totalErrors);
    }
}