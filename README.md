📚 LearnTypingApp

A Java-based Typing Tutor to help users improve typing speed, accuracy, and confidence. Built with Java Swing for a simple desktop experience.

⸻

✨ Features

Core Features
	•	📝 Typing Lessons: Structured lessons (home row, top row, bottom row, numbers, symbols).
	•	🎯 Practice Mode: Free typing practice using random words, custom text, or imported articles.
	•	⏱ Speed & Accuracy Tracking: Real-time WPM (Words Per Minute) and error count display after each session.
	•	📊 Progress Charts: Visual analytics of improvement over time.

Enhancement Features
	•	🏆 Gamification: Unlock achievements as you improve.
	•	🎮 Mini-Games: Typing challenges and time attacks for motivation.
	•	✍️ Custom Lessons: Create your own practice sets (coding keywords, tricky words).
	•	🚨 Mistake Highlighting: Instantly highlight errors while typing.
	•	🎹 Virtual Keyboard: Shows which finger to use for each key.
	•	📅 Daily Goals & Reminders: Set practice goals and get reminders.
 
 🗂 Project Structure

 LearnTypingApp/
│
├── src/
│   ├── Main.java                 // Entry point
│   ├── TypingLesson.java         // Represents a typing lesson
│   ├── LessonManager.java        // Manages lessons and user progress
│   ├── TypingPracticePanel.java  // UI panel for typing practice
│   ├── StatsTracker.java         // Tracks speed, accuracy, and progress
│   ├── NotificationManager.java  // Handles popup reminders
│   └── Utils.java                // Helper methods
│
├── data/
│   └── lessons.json              // Lessons content in JSON format
│
└── resources/
    └── fonts/, images/           // Custom fonts or UI assets

    ⚙️ Installation

Prerequisites
	•	Install Java JDK 17+
	•	Install Git
	•	(Optional) Install VS Code with Java Extension Pack

⸻

Steps to Run Locally
	1.	Clone the repository

 git clone https://github.com/your-username/LearnTypingApp.git
cd LearnTypingApp

javac -d bin src/*.java

java -cp bin Main

Run with VS Code
	1.	Open the LearnTypingApp folder in VS Code.
	2.	Make sure the Java Extension Pack is installed.
	3.	Open Main.java → click the green Run ▶️ button.

 📌 Roadmap
	•	Add more lessons (numbers, symbols)
	•	Implement gamification (levels & badges)
	•	Add mistake highlighting in real-time
	•	Build progress charts with JFreeChart or JavaFX

 🤝 Contributing

Contributions are welcome! Fork the repo, create a branch, and submit a pull request.

📜 License

This project is licensed under the MIT License – feel free to use and modify.
