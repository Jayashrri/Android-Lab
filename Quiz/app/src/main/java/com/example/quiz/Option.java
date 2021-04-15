package com.example.quiz;

public class Option {
    private int index;
    private int question;
    private String option;

    public Option(int index, int question, String option) {
        this.index = index;
        this.question = question;
        this.option = option;
    }

    public int getIndex() { return index; }
    public int getQuestion() { return question; }
    public String getOption() { return option; }
}
