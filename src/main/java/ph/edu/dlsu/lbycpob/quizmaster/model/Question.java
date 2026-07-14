package ph.edu.dlsu.lbycpob.quizmaster.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Question.java
public class Question {
   // TODO: Add the data attributes
    private int correctAnswer;
    private String category;
    private String questionText;
    private List<String> options;
    private String equation;


    public Question(int correctAnswer, String category, String questionText, List<String> options, String equation) {
        // TODO:
// this.correctAnswer = correctAnswer;
        this.category = category;
        this.questionText = questionText;
        this.options = options;
        this.equation = equation;
    }

    public Question(String category, String questionText, List<String> options, int correctAnswer, String equation) {

    }

    // Getters
    // TODO:


    public int getCorrectAnswer(){
        return correctAnswer;
    }

    public String getCategory(){
        Arrays.asList("All", "Algebra", "Geometry", "Calculus");
        return category;
    }

    public String getQuestionText(){
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getEquation(){
        return equation;
    }

    // Setters
    // TODO:
    public String setCategory(){
        return this.category = category;
    }

    public String setQuestionText(){
        return this.questionText = questionText;
    }

    public List<String> setOptions() {
        return this.options = options;
    }

    public String setEquation(){
        return this.equation = equation;
    }

    @Override
    public String toString() {
        return " "; // TODO:
    }
}

