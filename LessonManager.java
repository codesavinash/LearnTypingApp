import java.util.ArrayList;
import java.util.List;

public class LessonManager {
    private List<TypingLesson> lessons;

    public LessonManager() {
        lessons = new ArrayList<>();
        loadLessons();
    }

    private void loadLessons() {
        String lesson1 = "Typing is a fundamental skill in the modern world. "
                + "Practicing daily can significantly improve your typing speed and accuracy. "
                + "Start by focusing on the home row keys and gradually include more keys as you progress. "
                + "Maintaining proper posture and hand placement ensures long-term comfort and efficiency. "
                + "Consistency is key; even 15 minutes a day can lead to noticeable improvements over time. "
                + "Avoid looking at the keyboard and try to memorize key positions. "
                + "This will enhance both speed and accuracy, making you a proficient typist over time. "
                + "Typing exercises should include a mix of letters, numbers, and punctuation marks to simulate real-world text. "
                + "Once you feel confident, try typing paragraphs or articles to develop rhythm and endurance.\n";

        String lesson2 = "Advanced typing practice involves increasing complexity and speed gradually. "
                + "Incorporate common words, uncommon words, and technical vocabulary to challenge yourself. "
                + "Using proper finger techniques reduces mistakes and fatigue. "
                + "Always start slow, ensuring accuracy, then gradually increase speed without sacrificing correctness. "
                + "Online typing tests and exercises provide structured paths for improvement. "
                + "Pay attention to difficult key combinations and practice them repeatedly. "
                + "Track your daily progress and aim for incremental improvements rather than extreme jumps in speed. "
                + "Mindful practice helps reinforce muscle memory, which is the foundation for fast typing. "
                + "By combining consistency, correct techniques, and challenging exercises, typing becomes a fluid and effortless skill.\n";

        String lesson3 = "Typing proficiency is not just about speed, but also about endurance and accuracy. "
                + "Daily practice improves cognitive coordination between eyes, brain, and fingers. "
                + "Typing longer paragraphs, essays, or code can help build stamina. "
                + "Focus on accuracy first; speed naturally increases with practice. "
                + "Avoid unnecessary movements and maintain a relaxed posture. "
                + "Take short breaks during long practice sessions to prevent strain. "
                + "Experiment with different typing exercises, including rhythm drills and timed tests. "
                + "Challenge yourself with difficult words, punctuation, and numbers. "
                + "Keeping track of high scores and personal bests can motivate continual improvement. "
                + "Over time, these techniques lead to confident and efficient typing skills.\n";

        String lesson4 = "Professional typing requires mastery of multiple skills simultaneously. "
                + "Developing a rhythm and consistent pacing is crucial for efficiency. "
                + "Learning to type without looking at the keyboard significantly improves overall speed. "
                + "Incorporate proofreading exercises to simultaneously enhance accuracy. "
                + "Typing for long periods requires endurance and attention to detail. "
                + "Incorporate exercises that simulate real typing tasks such as emails, reports, or coding exercises. "
                + "Track errors and review commonly mistyped letters to target weak areas. "
                + "Gradual improvement, patience, and dedication are the hallmarks of an expert typist. "
                + "By combining technique, practice, and mindful repetition, typing becomes a seamless and productive skill.\n";

        String lesson5 = "Typing is an essential skill for students, professionals, and anyone working with computers. "
                + "Mastering typing can save significant time and enhance productivity. "
                + "Regular practice enhances memory, cognitive function, and hand-eye coordination. "
                + "Diversify practice sessions with letters, numbers, punctuation, and symbols. "
                + "Timed exercises help measure progress and provide motivation to improve further. "
                + "Advanced typists often focus on speed, accuracy, and endurance simultaneously. "
                + "Incorporating real-world texts, coding exercises, and articles helps simulate actual typing scenarios. "
                + "Remember to maintain good posture, take breaks, and gradually increase speed without sacrificing accuracy. "
                + "With dedication and structured practice, typing becomes a powerful and efficient tool in daily life.\n";

        lessons.add(new TypingLesson("Lesson 1", lesson1, true));
        lessons.add(new TypingLesson("Lesson 2", lesson2, false));
        lessons.add(new TypingLesson("Lesson 3", lesson3, false));
        lessons.add(new TypingLesson("Lesson 4", lesson4, false));
        lessons.add(new TypingLesson("Lesson 5", lesson5, false));
    }

    public List<TypingLesson> getLessons() {
        return lessons;
    }
}