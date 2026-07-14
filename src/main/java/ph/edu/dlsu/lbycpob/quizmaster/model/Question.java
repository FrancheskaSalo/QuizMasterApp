package ph.edu.dlsu.lbycpob.quizmaster.model;

import java.util.List;

public class Question {
    private String category;
    private String questionText;
    private List<String> options;
    private int correctAnswer;
    private String equation;

    // Question.java
    public Question(String category, String questionText, List<String> options, int correctAnswer, String equation) {
        // TODO: Add the data attributes
        this.category = category;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.equation = equation;
    }

    // Alternative Constructor (in case your code calls it with correctAnswer first)
    public Question(int correctAnswer, String category, String questionText, List<String> options, String equation) {
        // TODO:
        this.correctAnswer = correctAnswer;
        this.category = category;
        this.questionText = questionText;
        this.options = options;
        this.equation = equation;
    }

    // Getters
    // TODO:

    public String getCategory() {
        return category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getEquation() {
        return equation;
    }

    // Setters
    // TODO:

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    @Override
    public String toString() {
        // TODO:
        return "Question{" +
                "category='" + category + '\'' +
                ", questionText='" + questionText + '\'' +
                ", options=" + options +
                ", correctAnswerIndex=" + correctAnswer +
                ", equation='" + equation + '\'' +
                '}';
    }
}