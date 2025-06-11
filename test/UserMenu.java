import javax.swing.*;
import java.awt.*;

public class UserMenu extends JFrame {
    private UserManager userManager;
    private User user;
    private ProgressTracker progressTracker; // Progress tracker for this user

    public UserMenu(UserManager userManager, User user) {
        this.userManager = userManager;
        this.user = user;
        // Create (or load) the progress tracker for the current user.
        this.progressTracker = new ProgressTracker(user.getUsername());

        setTitle("User Menu - " + user.username);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        JButton takeQuizButton = new JButton("Take a Quiz");
        JButton viewProgressButton = new JButton("View Progress");
        JButton tutorialButton = new JButton("Tutorial");
        JButton logoutButton = new JButton("Logout");

        takeQuizButton.addActionListener(e -> {
            String[] quizTypes = {"Addition", "Subtraction", "Multiplication", "Division"};
            String[] difficultyLevels = {"Easy", "Medium", "Hard"};

            int quizType = JOptionPane.showOptionDialog(
                    this,
                    "Choose a quiz type:",
                    "Quiz Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    quizTypes,
                    quizTypes[0]);
            quizType++; // Adjust from 0-based index.

            int difficulty = JOptionPane.showOptionDialog(
                    this,
                    "Select Difficulty:",
                    "Difficulty Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    difficultyLevels,
                    difficultyLevels[0]);
            difficulty++;

            if (quizType >= 1 && quizType <= 4 && difficulty >= 1 && difficulty <= 3) {
                dispose(); // Close the user menu.
                // Start the quiz with the same user and progress tracker.
                new QuizGUI(userManager, user, progressTracker, quizType, difficulty);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid selection. Please try again.");
            }
        });

        viewProgressButton.addActionListener(e -> {
            ProgressView progressView = new ProgressView(progressTracker);
            progressView.setVisible(true);
        });

        tutorialButton.addActionListener(e -> {
            JFrame tutorialFrame = new JFrame("Tutorial");
            tutorialFrame.setSize(1000, 800);
            tutorialFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            ImageIcon icon = new ImageIcon("Table.jpg");
            JLabel tutorialImage = new JLabel(icon);
            JScrollPane scrollPane = new JScrollPane(tutorialImage);
            
            tutorialFrame.add(scrollPane);
            tutorialFrame.setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged out!");
            dispose();
            new MathGUI(userManager);  // Return to the login/register screen.
        });

        add(takeQuizButton);
        add(viewProgressButton);
        add(tutorialButton);
        add(logoutButton);

        setVisible(true);
    }
}

