📚 LearnTypingApp

LearnTypingApp is a Java desktop typing tutor that helps users improve typing speed, accuracy, and confidence.
It includes lessons, practice mode, progress tracking, and optional gamification features.

⸻

✨ Features

Core Features
	•	📝 Typing Lessons: Beginner-friendly structured lessons (home row, top row, bottom row, numbers, symbols)
	•	🎯 Practice Mode: Free typing practice using random words, custom text, or imported articles
	•	⏱ Speed & Accuracy Tracking: Real-time Words Per Minute (WPM) and error count
	•	📊 Progress Charts: Track improvement over time

Enhancement Features
	•	🏆 Gamification: Unlock achievements or levels
	•	🎮 Mini-Games: Typing challenges, time attacks, or quote competitions
	•	✍️ Custom Lessons: Users can create their own practice sets
	•	🚨 Mistake Highlighting: Highlight errors in real-time
	•	🎹 Virtual Keyboard Display: Shows which finger to use for each key
	•	📅 Daily Goals & Reminders: Set goals and receive pop-ups

 📂 Project Structure

 LearnTypingApp/
│
├── Main.java                 // Entry point
├── TypingLesson.java         // Represents a typing lesson
├── LessonManager.java        // Manages lessons and user progress
├── TypingPracticePanel.java  // UI panel for typing practice
├── StatsTracker.java         // Tracks speed, accuracy, and progress
├── NotificationManager.java  // Handles popup reminders (optional)
├── Utils.java                // Helper methods
│
├── data/
│   └── lessons.json          // Lessons content in JSON format
│
└── resources/
    └── fonts/, images/       // Custom fonts or UI assets

 ⚙️ Installation

Prerequisites
	•	Install Java JDK 17+
	•	Install Git
	•	(Optional) Install VS Code

⸻

Steps to Run Locally

1.	Clone the repository

git clone https://github.com/your-username/LearnTypingApp.git
cd LearnTypingApp

2.	Compile all Java files

	javac *.java

3.	Run the app

	java Main

4.	Optional: Create a JAR file

	jar cfe LearnTypingApp.jar Main *.class
	java -jar LearnTypingApp.jar

🎮 Usage Guide
	1.	Launch the app.
	2.	Choose between:
	•	Lessons Mode → Structured lessons
	•	Practice Mode → Free typing with random words, custom text, or imported files
	3.	Type the displayed text in the input box.
	4.	Review your speed, accuracy, and error count after completing the session.
	5.	Progress is automatically saved for future sessions.

 🎓 Lessons
	•	Lessons are stored in data/lessons.json.
	•	Example format:
 {
  "lesson1": {
    "title": "Home Row",
    "content": "asdf jkl;"
  },
  "lesson2": {
    "title": "Top Row",
    "content": "qwer uiop"
  }
}

	•	You can add custom lessons by editing lessons.json.

 📊 Stats & Progress
	•	WPM (Words Per Minute) → Typing speed
	•	Accuracy (%) → Correct characters / total characters
	•	Error Count → Mistyped keys
	•	Progress Charts → Visual graphs saved locally

Stats are saved automatically in data/stats.json.

⸻

❓ FAQ

Q: Can I run this without VS Code?
A: Yes, just compile with javac *.java and run with java Main.

Q: Can I import my own text for practice?
A: Yes! Paste text into Practice Mode or add it as a lesson in lessons.json.

Q: Where are my progress stats saved?
A: In data/stats.json.

⸻

🤝 Contributing
	1.	Fork the repository
	2.	Create a branch:
 git checkout -b feature/new-feature

 	3.	Commit your changes:

  git commit -m "Added new feature"

  4.	Push and create a pull request

     📸 Screenshots
