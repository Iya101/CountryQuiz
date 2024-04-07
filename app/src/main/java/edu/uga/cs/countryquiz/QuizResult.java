package edu.uga.cs.countryquiz;

public class QuizResult {
    private long id;
    private String quizDate;
    private int quizScore;

    // Constructor, getters, and setters
    public QuizResult(long id, String quizDate, int quizScore) {
        this.id = id;
        this.quizDate = quizDate;
        this.quizScore = quizScore;
    }

    public long getId() { return id; }
    public String getQuizDate() { return quizDate; }
    public int getQuizScore() { return quizScore; }

    // Additional methods if necessary
}
