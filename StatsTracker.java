import java.util.*;

public class StatsTracker {

    // Daily stats container
    private DailyStats todayStats = new DailyStats();

    // Static method to calculate WPM
    public static int calculateWPM(String typedText, long elapsedMillis) {
        if (typedText.isEmpty()) return 0;
        double minutes = elapsedMillis / 60000.0;
        if (minutes <= 0) minutes = 1.0 / 60; // prevent division by zero
        int words = typedText.trim().split("\\s+").length;
        return (int)(words / minutes);
    }

    // Static method to calculate Accuracy
    public static int calculateAccuracy(String typedText, int errors, int totalChars) {
        if (totalChars == 0) return 100;
        int correctChars = totalChars - errors;
        return (int)(((double) correctChars / totalChars) * 100);
    }

    // Record a typing session
    public void recordSession(String lessonTitle, int wpm, int errors, int seconds) {
        todayStats.timePracticedSeconds += seconds;
        todayStats.totalErrors += errors;
        if (wpm > todayStats.highWPM) todayStats.highWPM = wpm;
    }

    // Get today's stats
    public DailyStats getTodayStats() {
        return todayStats;
    }

    // Inner class for daily stats
    public static class DailyStats {
        int timePracticedSeconds = 0;
        int highWPM = 0;
        int totalErrors = 0;
    }
}