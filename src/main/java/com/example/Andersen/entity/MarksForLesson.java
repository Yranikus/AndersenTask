package com.example.Andersen.entity;

public class MarksForLesson {

    private int answer;
    private int question;


    @Override
    public String toString() {
        return "MarksForLesson{" +
                "answer=" + answer +
                ", question=" + question +
                '}';
    }

    public MarksForLesson(int answer, int question) {
        this.answer = answer;
        this.question = question;
    }

    public MarksForLesson() {
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }
}
