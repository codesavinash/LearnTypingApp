import javax.swing.*;

public class NotificationManager {
    public static void showNotification(String message) {
        JOptionPane.showMessageDialog(null, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }
}
