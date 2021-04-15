package com.example.quiz;

import java.util.List;

public class Question {
    private long id;
    private String question;
    private List<Option> options;
    private int correct;

    public Question(long id, String question, List<Option> options, int correct) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correct = correct;
    }

    public long getId() { return id; }
    public String getQuestion() { return question; }
    public List<Option> getOptions() { return options; }
    public int getCorrect() { return correct; }

    public void setId(long id) { this.id = id; }
}
