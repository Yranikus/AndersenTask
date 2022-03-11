package com.example.Andersen.entity;

public class Student {

    private int id;
    private String name;
    private int primaryScore;
    private int score;
    private int check;

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public Student() {
    }

    public Student(int id, String name, int primaryScore, int score) {
        this.id = id;
        this.name = name;
        this.primaryScore = primaryScore;
        this.score = score;
        this.check = 0;
    }


    public Student(String name, int primaryScore, int score) {
        this.name = name;
        this.primaryScore = primaryScore;
        this.score = score;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrimaryScore() {
        return primaryScore;
    }

    public void setPrimaryScore(int primaryScore) {
        this.primaryScore = primaryScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", primaryScore=" + primaryScore +
                ", score=" + score +
                ", check=" + check +
                '}';
    }
}
