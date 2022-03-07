package com.example.Andersen.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PresentStudentsWithMarks {

    private Date date;
    private ArrayList<StudentWithMarks> studentsWithMarks;


    public PresentStudentsWithMarks() {
    }

    public PresentStudentsWithMarks(String dateString, ArrayList<StudentWithMarks> studentsWithMarks) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.studentsWithMarks = studentsWithMarks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<StudentWithMarks> getStudentsWithMarks() {
        return studentsWithMarks;
    }

    public void setStudentsWithMarks(ArrayList<StudentWithMarks> studentsWithMarks) {
        this.studentsWithMarks = studentsWithMarks;
    }
}
