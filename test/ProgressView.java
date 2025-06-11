import javax.swing.*;
import java.awt.*;

public class ProgressView extends JFrame {
    private ProgressTracker progressTracker;
    private JLabel quizzesLabel;
    private JLabel progressLabel;
    private JLabel accuracyLabel;
    private JProgressBar progressBar;

    public ProgressView(ProgressTracker progressTracker) {
        this.progressTracker = progressTracker;

        setTitle("Progress View");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
        setLayout(new BorderLayout());

        // Create a panel for text labels.
        JPanel labelPanel = new JPanel(new GridLayout(3, 1));
        quizzesLabel = new JLabel("Quizzes Taken: 0", SwingConstants.CENTER);
        progressLabel = new JLabel("Total Questions: 0", SwingConstants.CENTER);
        accuracyLabel = new JLabel("Accuracy: 0%", SwingConstants.CENTER);
        labelPanel.add(quizzesLabel);
        labelPanel.add(progressLabel);
        labelPanel.add(accuracyLabel);
        add(labelPanel, BorderLayout.NORTH);

        // Create and add a progress bar.
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        add(progressBar, BorderLayout.CENTER);

        // Create a panel for buttons at the bottom.
        JPanel buttonPanel = new JPanel();
        JButton clearButton = new JButton("Clear History");
        clearButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to clear your history?",
                    "Confirm Clear History",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                progressTracker.clearHistory();
                updateProgressDisplay();
            }
        });
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(clearButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateProgressDisplay();
    }

    public void updateProgressDisplay() {
        int totalQuestions = progressTracker.getTotalQuestions();
        int score = progressTracker.getScore();
        int quizzes = progressTracker.getTotalQuizzes();
        double accuracy = progressTracker.getAccuracy();

        quizzesLabel.setText("Quizzes Taken: " + quizzes);
        progressLabel.setText("Total Questions: " + totalQuestions);
        accuracyLabel.setText("Accuracy: " + String.format("%.2f", accuracy) + "%");
        progressBar.setValue((int) accuracy);
    }
}
