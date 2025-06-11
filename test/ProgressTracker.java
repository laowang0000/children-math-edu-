import java.io.*;
import java.util.*;

public class ProgressTracker {
    private String username;
    private int totalQuizzes;
    private int totalQuestions;
    private int correctAnswers;

    private static final String PROGRESS_FILE = "progress.txt";

    public ProgressTracker(String username) {
        this.username = username;
        loadProgress();
    }

    // Load progress for this user from the progress file.
    private void loadProgress() {
        File file = new File(PROGRESS_FILE);
        if (!file.exists()) {
            totalQuizzes = 0;
            totalQuestions = 0;
            correctAnswers = 0;
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                // Format: username,totalQuizzes,totalQuestions,correctAnswers
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].equals(username)) {
                    totalQuizzes = Integer.parseInt(parts[1]);
                    totalQuestions = Integer.parseInt(parts[2]);
                    correctAnswers = Integer.parseInt(parts[3]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                totalQuizzes = 0;
                totalQuestions = 0;
                correctAnswers = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            totalQuizzes = 0;
            totalQuestions = 0;
            correctAnswers = 0;
        }
    }

    // Save the current progress for this user to the progress file.
    private void saveProgress() {
        // Read all progress records into a map.
        Map<String, String> progressMap = new HashMap<>();
        File file = new File(PROGRESS_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        progressMap.put(parts[0], line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Update the current user's record.
        String newRecord = username + "," + totalQuizzes + "," + totalQuestions + "," + correctAnswers;
        progressMap.put(username, newRecord);

        // Write back all records.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String record : progressMap.values()) {
                bw.write(record);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Call this method after each quiz to update progress.
    public void addQuizResult(int correct, int total) {
        totalQuizzes++;
        totalQuestions += total;
        correctAnswers += correct;
        saveProgress();
        // For debugging:
        System.out.println("Updated Progress for " + username + ": Quizzes = " + totalQuizzes +
                           ", Total Questions = " + totalQuestions + ", Correct = " + correctAnswers);
    }
    
    // Clear the current progress history.
    public void clearHistory() {
        totalQuizzes = 0;
        totalQuestions = 0;
        correctAnswers = 0;
        saveProgress();
    }

    public int getTotalQuizzes() {
        return totalQuizzes;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getScore() {
        return correctAnswers;
    }

    public double getAccuracy() {
        return totalQuestions == 0 ? 0.0 : ((double) correctAnswers / totalQuestions) * 100;
    }
}
