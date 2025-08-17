import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set the Swing look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the main window
        SwingUtilities.invokeLater(() -> {
            TypingPracticePanel panel = new TypingPracticePanel();
            JFrame frame = new JFrame("LearnTypingApp");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}