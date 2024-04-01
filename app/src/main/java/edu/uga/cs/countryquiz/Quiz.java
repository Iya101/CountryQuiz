package edu.uga.cs.countryquiz;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private long  id;
    private String quizDate;
    private int quizResult;
    private int questionsAnswered;
    private List<Question> questions; // References to 6 Question objects.


    public Quiz()
    {
        this.id = -1;
        this.quizDate = null;
        this.quizResult = 0;
        this.questionsAnswered = 0;
        this.questions = new ArrayList<>(6);
    }

    public Quiz(String quizDate, int quizResult, int questionsAnswered) {
        this.id = -1;
        this.quizDate = quizDate;
        this.quizResult = quizResult;
        this.questionsAnswered = questionsAnswered;
        this.questions = new ArrayList<>(6);
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }


    public String getQuizDate()
    {
        return quizDate;
    }

    public void setQuizDate(String quizDate)
    {
        this.quizDate = quizDate;
    }


    // Getter and setter for questionsAnswered
    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void setQuestionsAnswered(int questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }

    // Methods to manage the questions list
    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        if (this.questions.size() < 6) {
            this.questions.add(question);
        }
    }

    // Increment score and questions answered
    public void correctAnswer() {
        quizResult++;
        questionsAnswered++;
    }

    // Increment questions answered only
    public void wrongAnswer() {
        questionsAnswered++;
    }

}