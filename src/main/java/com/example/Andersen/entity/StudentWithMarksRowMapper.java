package com.example.Andersen.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentWithMarksRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        StudentWithMarks student = new StudentWithMarks();
        MarksForLesson marksForLesson = new MarksForLesson();
        marksForLesson.setAnswer(rs.getInt("answer"));
        marksForLesson.setQuestion(rs.getInt("question"));
        int i = rs.getInt("date_id");
        if (i != 0 ) student.setCheck(1);
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setScore(rs.getInt("score"));
        student.setPrimaryScore(rs.getInt("primaryscore"));
        student.setMarksForLesson(marksForLesson);
        return student;
    }



}
