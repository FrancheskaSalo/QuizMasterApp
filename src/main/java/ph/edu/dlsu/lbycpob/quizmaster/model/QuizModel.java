package ph.edu.dlsu.lbycpob.quizmaster.model;

// QuizModel.java

import ph.edu.dlsu.lbycpob.quizmaster.utils.QuestionLoader;
import java.util.*;
import java.util.stream.Collectors;

public class QuizModel {
    private List<Question> questions;
    private List<Question> mistakeQuestions;
    int currentQuestionIndex;
    private int score;
    private int totalQuestions;
    private String currentCategory;
    private UserProgress userProgress;
    private List<String> categories;

    public QuizModel() {
        this.questions = new ArrayList<>();
        this.mistakeQuestions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.userProgress = new UserProgress();
        initializeQuestions();
    }

    private void initializeQuestions() {
        // Algebra questions
        questions.add(new Question("Algebra", "Solve: 2x + 5 = 13",
                Arrays.asList("x = 4", "x = 3", "x = 5", "x = 6"), 0, "2x + 5 = 13"));
        questions.add(new Question("Algebra", "What is the value of x in: 3x - 7 = 14?",
                Arrays.asList("x = 7", "x = 5", "x = 9", "x = 6"), 0, "3x - 7 = 14"));
        questions.add(new Question("Algebra", "Simplify: (x + 2)(x - 3)",
                Arrays.asList("x² - x - 6", "x² + x - 6", "x² - 5x + 6", "x² + 5x - 6"), 0, "(x + 2)(x - 3)"));
        questions.add(new Question("Algebra", "Leah is 6 years older than Sue, John is 5 years older than Leah, and the total of their ages is 41. How old is Sue?",
                Arrays.asList("8", "10", "14", "19"), 0, "(s+6)+5 = s+11"));
        questions.add(new Question("Algebra", "Alfred wants to invest $4,000 at 6% simple interest rate for five years. How much interest will he receive?",
                Arrays.asList("$240", "$480", "$720", "$1,200"), 3, "I = Prt"));
        questions.add(new Question("Algebra", "If r = 5z and 15z = 3y, then r = ?",
                Arrays.asList("y", "2y", "4y", "10y"), 0, "r = 5z"));
        questions.add(new Question("Algebra", "If 300 jelly beans cost you x dollars, how many jelly beans can you purchase for 50 cents at the same rate?",
                Arrays.asList("150/x", "150x", "6x", "1500/x"), 0, "300/x * 1/2 = 150/x"));
        questions.add(new Question("Algebra", "If 8x+5x+2x+4x=114, then 5x+3= ?",
                Arrays.asList("12", "25", "33", "47"), 2, "x = 6, 5(6)+3 = 33"));


        // Geometry questions
        questions.add(new Question("Geometry", "Area of a circle with radius 5",
                Arrays.asList("25π", "10π", "50π", "5π"), 0, "A = πr²"));
        questions.add(new Question("Geometry", "Sum of angles in a triangle",
                Arrays.asList("180°", "360°", "90°", "270°"), 0, "∠A + ∠B + ∠C = 180°"));
        questions.add(new Question("Geometry", "Pythagorean theorem: a² + b² = ?",
                Arrays.asList("c²", "2c", "c", "c³"), 0, "a² + b² = c²"));
        questions.add(new Question("Geometry", "A rectangle has a length of 12 meters and a width of 4 meters. What is its perimeter?",
                Arrays.asList("16 meters", "32 meters", "48 meters", "64 meters"), 1, "Perimeter = 2 * (length + width)"));
        questions.add(new Question("Geometry", "Two interior angles of triangle measure 50° and 70°. What is the measure of the third angle?",
                Arrays.asList("60°", "80°", "90°", "120°"), 0, "180°-(50°+70°) = 180°-120° = 60°"));
        questions.add(new Question("Geometry", "What is the circumference of a circle that has a diameter of 10 centimeters?",
                Arrays.asList("15.7cm", "31.4cm", "62.8cm", "78.5cm"), 1, "Circumference = pi * diameter"));
        questions.add(new Question("Geometry", "A triangle has a base of 6 centimeters and a height of 10 centimeters. What is the area of the triangle?",
                Arrays.asList("16 square centimeters", "30 square centimeters", "60 square centimeters", "12 square centimeters"), 1, "Area = 1/2 * base * height"));
        questions.add(new Question("Geometry", "A right-angled triangle has two shorter sides (legs) measuring 3 centimeters and 4 centimeters. What is the length of the longest side (the hypotenuse)?",
                Arrays.asList("5cm", "7cm", "12cm", "25cm"), 0, "a²+b²=c²"));

        // Calculus questions
        questions.add(new Question("Calculus", "Derivative of x²",
                Arrays.asList("2x", "x", "x²", "2x²"), 0, "d/dx(x²) = 2x"));
        questions.add(new Question("Calculus", "∫x dx = ?",
                Arrays.asList("x²/2 + C", "x + C", "x²", "2x + C"), 0, "∫x dx"));
        questions.add(new Question("Calculus", "Limit of (sin x)/x as x→0",
                Arrays.asList("1", "0", "∞", "undefined"), 0, "lim(x→0) sin(x)/x"));
        questions.add(new Question("Calculus", "∫cos⁻¹(2x) dx , What is the best choice for u and dv here?",
                Arrays.asList("u = cos⁻¹(x) and dv = dx", "u = 1 and dv = cos⁻¹(2x)dx", "u = cos⁻¹(x) and dv = 2dx", "u = cos⁻¹(2x) and dv = dx"), 3, "∫cos⁻¹(2x) dx"));
        questions.add(new Question("Calculus", "What is ∫sinx dx?",
                Arrays.asList("-cosx + C", "cosx + C", "cosxsinx + C", "-sinx + C"), 0, "∫sinx dx"));
        questions.add(new Question("Calculus", "What trigonometric function(s) should be present to use the Wallis Formula?",
                Arrays.asList("sinxcosx", "secxtanx", "cscxcotx", "tanxsinx"), 0, "∫ sinⁿ(x) cosᵐ(x) dx"));
        questions.add(new Question("Calculus", "Evaluate ∫15x²⁹cosx¹⁵ dx",
                Arrays.asList("x¹⁵sinx¹⁵ + cosx¹⁵ + C", "x¹⁵sinx¹⁵-cosx¹⁵ + C", "x¹⁶sinx¹⁴ + cosx¹⁴ + C", "x²⁹sinx¹⁵ + cosx¹⁵ + C"), 0, "∫15x²⁹cosx¹⁵ dx"));
        questions.add(new Question("Calculus", "Evaluate: ∫(3x+1)/(6x²-14x-12) dx",
                Arrays.asList("5/11 ln|2x-6| + 1/22 ln|3x+2| + C", "2/7 ln|6x-2| + 1/6 ln|x+6| + C", "5/7 ln|3x-4| - 3/5 ln|2x+3| + C", "(ln|6x²-14x-12|)/4 + C"), 0, "∫(3x+1)/(6x²-14x-12)"));

        Collections.shuffle(questions);

        // Load from file
        questions.addAll(QuestionLoader.loadQuestionsFromCSVSafe("/questions.csv"));
        initializeCategories();
    }

    private void initializeCategories() {
        categories = questions.stream()
                .map(Question::getCategory)  // or obj -> obj.getName()
                .distinct()
                .collect(Collectors.toList());
    }

    public void startQuiz(String category) {
        this.currentCategory = category;
        if ("All".equals(category)) {
            Collections.shuffle(questions);
        } else {
            questions = questions.stream()
                    .filter(q -> q.getCategory().equals(category))
                    .collect(Collectors.toList());
            Collections.shuffle(questions);
        }
        showQuestions();
        this.totalQuestions = questions.size();
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public void showQuestions() {
        for (Question question : questions) {
            System.out.println(question);
        }
    }

    public Question getCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public Question getPreviousQuestion() {
        if (currentQuestionIndex <= questions.size()) {
            return questions.get(currentQuestionIndex - 1);
        }
        return null;
    }

    public boolean answerQuestion(int selectedAnswer) {
        if (currentQuestionIndex < questions.size()) {
            Question current = questions.get(currentQuestionIndex);
            boolean correct = current.getCorrectAnswer() == selectedAnswer;

            if (correct) {
                score++;
                userProgress.addCorrectAnswer();
            } else {
                mistakeQuestions.add(current);
                userProgress.addIncorrectAnswer();
            }

            currentQuestionIndex++;
            return correct;
        }
        return false;
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }

    public boolean hasMistakeQuestions() {
        return !mistakeQuestions.isEmpty();
    }

    public void startMistakeQuiz() {
        questions = new ArrayList<>(mistakeQuestions);
        mistakeQuestions.clear();
        currentQuestionIndex = 0;
        Collections.shuffle(questions);
    }

    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCurrentQuestionNumber() { return currentQuestionIndex + 1; }
    public UserProgress getUserProgress() { return userProgress; }
    public String getCurrentCategory() { return currentCategory; }
    public List<Question> getQuestions() {
        return questions;
    }

    public List<String> getCategories() {
        Arrays.asList("All", "Algebra", "Geometry", "Calculus");
        return categories;
    }
}