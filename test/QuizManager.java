import java.util.Random;

public class QuizManager {
    private static final Random random = new Random();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int numQuestions = 5;
    private int correctAnswer;
    private int quizType;
    private int difficulty;
    private QuizGUI quizGUI;
    private User user;
    private ProgressTracker progressTracker;

    public QuizManager(QuizGUI quizGUI, User user, ProgressTracker progressTracker, int quizType, int difficulty) {
        this.quizGUI = quizGUI;
        this.user = user;
        this.progressTracker = progressTracker;
        this.quizType = quizType;
        this.difficulty = difficulty;
        startQuiz();
    }

    public void startQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        askNextQuestion();
    }

    public void askNextQuestion() {
        if (currentQuestionIndex >= numQuestions) {
            // Quiz is over—first update the progress tracker, then show results.
            progressTracker.addQuizResult(score, numQuestions);
            quizGUI.showResult(score, numQuestions);
            return;
        }

        int num1 = generateNumber(difficulty);
        int num2 = generateNumber(difficulty);
        String question = generateQuestion(num1, num2);

        quizGUI.displayQuestion(question, this);
        currentQuestionIndex++;
    }

    private String generateQuestion(int num1, int num2) {
        switch (quizType) {
            case 1:
                correctAnswer = num1 + num2;
                return num1 + " + " + num2;
            case 2:
                correctAnswer = num1 - num2;
                return num1 + " - " + num2;
            case 3:
                correctAnswer = num1 * num2;
                return num1 + " * " + num2;
            case 4:
                // For division, ensure an exact result.
                while (num2 == 0 || num1 % num2 != 0) {
                    num1 = generateNumber(difficulty);
                    num2 = generateNumber(difficulty);
                }
                correctAnswer = num1 / num2;
                return num1 + " / " + num2;
            default:
                return "Invalid quiz type";
        }
    }

    public void checkAnswer(int userAnswer) {
        if (userAnswer == correctAnswer) {
            score++;
            quizGUI.showMessage("Correct!");
        } else {
            quizGUI.showMessage("Incorrect! The correct answer was " + correctAnswer);
        }
        askNextQuestion();
    }

    private int generateNumber(int difficulty) {
        switch (difficulty) {
            case 1:
                return random.nextInt(10) + 1;
            case 2:
                return random.nextInt(50) + 1;
            case 3:
                return random.nextInt(100) + 1;
            default:
                return 0;
        }
    }
}
