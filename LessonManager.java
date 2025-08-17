import java.io.*;
import java.util.*;

public class LessonManager {
    private List<TypingLesson> lessons;

    public LessonManager() {
        lessons = new ArrayList<>();
        loadLessons();
    }

    private void loadLessons() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/lessons.json"))) {
            String line;
            StringBuilder jsonText = new StringBuilder();
            while ((line = br.readLine()) != null) {
                jsonText.append(line.trim());
            }

            // Very simple parsing assuming JSON is well formatted
            String content = jsonText.toString();

            // Remove outer braces { }
            if (content.startsWith("{") && content.endsWith("}")) {
                content = content.substring(1, content.length() - 1);
            }

            // Split lessons by "},"
            String[] lessonBlocks = content.split("\\},");
            for (String block : lessonBlocks) {
                // Add back missing closing brace if needed
                if (!block.endsWith("}")) {
                    block += "}";
                }

                // Extract title
                String title = extractValue(block, "title");
                String lessonContent = extractValue(block, "content");

                if (title != null && lessonContent != null) {
                    lessons.add(new TypingLesson(title, lessonContent));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractValue(String block, String key) {
        try {
            String[] parts = block.split("\"" + key + "\":");
            if (parts.length < 2) return null;
            String value = parts[1].split(",")[0].replace("\"", "").replace("}", "").trim();
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public List<TypingLesson> getLessons() {
        return lessons;
    }
}