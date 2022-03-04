package com.example.Andersen.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PresentStudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        int i = rs.getInt("date_id");
        if (i != 0 ) student.setCheck(1);
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setScore(rs.getInt("score"));
        student.setPrimaryScore(rs.getInt("primaryscore"));
        return student;
    }
}
