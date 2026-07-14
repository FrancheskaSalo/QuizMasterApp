package ph.edu.dlsu.lbycpob.quizmaster.controller;

// QuizController.java
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ph.edu.dlsu.lbycpob.quizmaster.model.QuizModel;
import ph.edu.dlsu.lbycpob.quizmaster.utils.AudioClipManager;
import ph.edu.dlsu.lbycpob.quizmaster.utils.SoundConfig;
import ph.edu.dlsu.lbycpob.quizmaster.view.QuizView;
import ph.edu.dlsu.lbycpob.quizmaster.model.UserProgress;
import ph.edu.dlsu.lbycpob.quizmaster.model.Question;

import java.util.Objects;

public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private QuizModel model;
    private QuizView view;

    // Sound effects
    private final AudioClip wrongSound;
    private final AudioClip correctSound;

    public QuizController() {
        // Initialize Sound Effects
        wrongSound = loadSound(SoundConfig.WRONG_GUESS_SOUND);
        correctSound = loadSound(SoundConfig.CORRECT_GUESS_SOUND);

    }

    public void start(Stage primaryStage) {
        model = new QuizModel();
        view = new QuizView(primaryStage);

        setupEventHandlers();
        view.setCategories(model.getCategories());
        view.showMenuScene();
        primaryStage.show();
    }

    private void setupEventHandlers() {
        // Menu handlers
        view.getStartButton().setOnAction(e -> startQuiz());
        view.getStatsButton().setOnAction(e -> showStatistics());

        // Quiz handlers
        view.getSubmitButton().setOnAction(e -> submitAnswer());
        view.getNextButton().setOnAction(e -> nextQuestion());

        // Result handlers
        view.getRestartButton().setOnAction(e -> restartQuiz());
        view.getBonusButton().setOnAction(e -> startBonusRound());
        view.getMenuButton().setOnAction(e -> returnToMenu());
    }

    private void startQuiz() {
        logger.info("Starting Quiz");
        String selectedCategory = view.getCategoryComboBox().getValue();
        if (selectedCategory == null) {
            showAlert("Please select a category!");
            return;
        }

        model.startQuiz(selectedCategory);
        view.showQuizScene();
        loadNextQuestion();
    }

    /**
     * Loads a sound from the specified file and returns it as an AudioClip.
     * <p>
     * The method attempts to locate the sound file in the directory specified by the
     * {@code SoundConfig.SOUND_PATH} constant and creates an {@code AudioClip} object from it.
     * If the file cannot be found or an error occurs during loading, a warning is printed
     * and the method returns {@code null}.
     *
     * @param soundFile The name of the sound file to be loaded, including its extension.
     * @return The {@code AudioClip} object corresponding to the loaded sound file,
     * or {@code null} if the sound file could not be loaded.
     */
    private AudioClip loadSound(String soundFile) {
        try {
            return new
            AudioClip(Objects.requireNonNull(getClass().getResource(SoundConfig.SOUND_PATH + soundFile)).toExternalForm());
        } catch (Exception e) {
            logger.error("Warning: Could not load sound file: {}", soundFile);
            return null;
        }
    }

    /**
     * Play a game sound clip
     *
     * @param sound The sound to play
     */
    private void playSound(AudioClip sound) {
        logger.info("Playing sound: {}", sound);
        AudioClipManager.getInstance().playSound(sound);
    }


    private void loadNextQuestion() {
        logger.info("Loading next question");
        Question question = model.getCurrentQuestion();
        if (question != null) {
            view.updateQuestion(question, model.getCurrentQuestionNumber(), model.getTotalQuestions());
            view.updateScore(model.getScore(), model.getCurrentQuestionNumber() - 1);
        } else {
            showResults();
        }
    }

    private void submitAnswer() {
        logger.info("Submitting answer");
        RadioButton selected = (RadioButton) view.getOptionsGroup().getSelectedToggle();
        if (selected == null) {
            showAlert("Please select an answer!");
            return;
        }

        int selectedIndex = view.getOptionButtons().indexOf(selected);
        boolean isCorrect = model.answerQuestion(selectedIndex);
        if (isCorrect) {
            playSound(correctSound);
        } else {
            playSound(wrongSound);
        }
        Question currentQuestion = model.getPreviousQuestion();
        view.showAnswerResult(isCorrect, currentQuestion.getCorrectAnswer());
        view.updateScore(model.getScore(), model.getCurrentQuestionNumber() - 1);
    }

    private void nextQuestion() {
        logger.info("Next question");
        if (model.hasNextQuestion()) {
            loadNextQuestion();
        } else {
            showResults();
        }
    }

    private void showResults() {
        logger.info("Showing results");
        UserProgress progress = model.getUserProgress();
        view.updateResults(model.getScore(), model.getTotalQuestions(), progress, model.hasMistakeQuestions());
        view.showResultScene();

        // Clear achievements after displaying
        progress.clearRecentAchievements();
    }

    private void restartQuiz() {
        logger.info("Restarting quiz");
        view.showMenuScene();
    }

    private void startBonusRound() {
        logger.info("Starting bonus round");
        model.startMistakeQuiz();
        view.showQuizScene();
        loadNextQuestion();
    }

    private void returnToMenu() {
        logger.info("Returning to menu");
        view.showMenuScene();
    }

    private void showStatistics() {
        logger.info("Showing statistics");
        UserProgress progress = model.getUserProgress();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Statistics");
        alert.setHeaderText("Your Performance Statistics");

        StringBuilder stats = new StringBuilder();
        stats.append(" Total Questions: ").append(progress.getTotalQuestionsAnswered()).append("\n");
        stats.append(" Correct Answers: ").append(progress.getCorrectAnswers()).append("\n");
        stats.append(" Accuracy: ").append(String.format("%.1f%%", progress.getAccuracy())).append("\n");
        stats.append(" Total Points: ").append(progress.getTotalPoints()).append("\n\n");

        if (!progress.getBadges().isEmpty()) {
            stats.append(" Badges Earned:\n");
            for (String badge : progress.getBadges()) {
                stats.append("• ").append(badge).append("\n");
            }
            stats.append("\n");
        }

        stats.append("Recommendation:\n").append(progress.getRecommendation());

        alert.setContentText(stats.toString());
        alert.showAndWait();
    }

    private void showAlert(String message) {
        logger.info("Showing alert: {}", message);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}