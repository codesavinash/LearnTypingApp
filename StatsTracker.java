public class StatsTracker {

    // Daily stats container
    private DailyStats todayStats = new DailyStats();

    // Static method to calculate WPM (standard 5 chars = 1 word)
    public static int calculateWPM(String typedText, long elapsedMillis) {
        if (typedText.isEmpty()) return 0;
        double minutes = elapsedMillis / 60000.0;
        if (minutes <= 0) minutes = 1.0 / 60; // prevent division by zero
        int words = typedText.length() / 5;
        return (int)(words / minutes);
    }

    // Static method to calculate Accuracy with clamping
    public static int calculateAccuracy(String typedText, int errors, int totalChars) {
        if (totalChars == 0) return 100;
        int correctChars = Math.max(0, totalChars - errors);
        int safeTotal = Math.max(1, totalChars);
        return (int)(((double) correctChars / safeTotal) * 100);
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
