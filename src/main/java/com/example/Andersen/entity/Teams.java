package com.example.Andersen.entity;

import java.util.ArrayList;

public class Teams {

    private int numberOfTeam;
    private ArrayList students;
    private String leader;
    private String repo;


    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public Teams() {
    }

    public Teams(int index) {
        this.numberOfTeam = index;
        this.students = new ArrayList<Student>();
    }


    public Teams(int numberOfTeam, ArrayList students, String leader) {
        this.leader = leader;
        this.numberOfTeam = numberOfTeam;
        this.students = students;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
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

    @Override
    public String toString() {
        return "Teams{" +
                "numberOfTeam=" + numberOfTeam +
                ", students=" + students +
                ", leader='" + leader + '\'' +
                '}';
    }
}
