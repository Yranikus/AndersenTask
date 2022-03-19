package com.example.Andersen.entity;


public class MarksForLesson {

    private Double answer;
    private Double question;


    @Override
    public String toString() {
        return "MarksForLesson{" +
                "answer=" + answer +
                ", question=" + question +
                '}';
    }

    public MarksForLesson(Double answer, Double question) {
        this.answer = answer;
        this.question = question;
    }

    public MarksForLesson() {
    }

    public Double getAnswer() {
        return answer;
    }

    public void setAnswer(Double answer) {
        this.answer = answer;
    }

    public Double getQuestion() {
        return question;
    }

    public void setQuestion(Double question) {
        this.question = question;
    }
}
