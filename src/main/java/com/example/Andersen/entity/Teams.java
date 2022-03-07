package com.example.Andersen.entity;

import java.util.ArrayList;

public class Teams {

    private int numberOfTeam;
    private ArrayList students;
    private String leader;

    public Teams() {
    }

    public Teams(int numberOfTeam, ArrayList students, String leader) {
        this.leader = leader;
        this.numberOfTeam = numberOfTeam;
        this.students = students;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    public int getNumberOfTeam() {
        return numberOfTeam;
    }

    public void setNumberOfTeam(int numberOfTeam) {
        this.numberOfTeam = numberOfTeam;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
