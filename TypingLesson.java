public class TypingLesson {
    private String title;
    private String content;
    private boolean unlocked;

    public TypingLesson(String title, String content, boolean unlocked) {
        this.title = title;
        this.content = content;
        this.unlocked = unlocked;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public boolean isUnlocked() { return unlocked; }
    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }
}
