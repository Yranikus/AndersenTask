package com.example.Andersen.entity;

public class StudentWithMarks extends Student{



    private MarksForLesson marksForLesson;



    public StudentWithMarks() {

    }

    @Override
    public String toString() {
        return "StudentWithMarks{" +
                "marksForLesson=" + marksForLesson +
                '}';
    }

    public StudentWithMarks(int id, String name, int primaryScore, int score, MarksForLesson marksForLesson) {
        super(id, name, primaryScore, score);
        this.marksForLesson = marksForLesson;
    }

    public StudentWithMarks(String name, int primaryScore, int score, MarksForLesson marksForLesson) {
        super(name, primaryScore, score);
        this.marksForLesson = marksForLesson;
    }

    public MarksForLesson getMarksForLesson() {
        return marksForLesson;
    }

    public void setMarksForLesson(MarksForLesson marksForLesson) {
        this.marksForLesson = marksForLesson;
    }



}
