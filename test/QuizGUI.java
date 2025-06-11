import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizGUI extends JFrame {
    private JLabel questionLabel;
    private JTextField answerField;
    private JButton submitButton;
    private QuizManager quizManager;

    // Save user, progressTracker, and userManager so we can return to the menu later.
    private User user;
    private ProgressTracker progressTracker;
    private UserManager userManager;

    public QuizGUI(UserManager userManager, User user, ProgressTracker progressTracker, int quizType, int difficulty) {
        this.userManager = userManager;
        this.user = user;
        this.progressTracker = progressTracker;

        setTitle("Math Quiz");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        questionLabel = new JLabel("Question will appear here...", SwingConstants.CENTER);
        answerField = new JTextField();
        submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            try {
                int userAnswer = Integer.parseInt(answerField.getText());
                quizManager.checkAnswer(userAnswer);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
            answerField.setText(""); // Clear the answer field.
        });

        add(questionLabel);
        add(answerField);
        add(submitButton);

        setVisible(true);

        // Initialize QuizManager.
        quizManager = new QuizManager(this, user, progressTracker, quizType, difficulty);
    }

    public void displayQuestion(String question, QuizManager manager) {
        this.quizManager = manager;
        questionLabel.setText("Question: " + question);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showResult(int score, int total) {
        JOptionPane.showMessageDialog(this, "Quiz Completed! Your score: " + score + "/" + total);
        dispose();
        // Return to the User Menu with the same user and progressTracker.
        new UserMenu(userManager, user);
    }
}
