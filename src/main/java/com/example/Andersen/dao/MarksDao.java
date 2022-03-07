package com.example.Andersen.dao;


import com.example.Andersen.entity.DateEntity;
import com.example.Andersen.entity.PresentStudentsWithMarks;
import com.example.Andersen.entity.StudentWithMarks;
import com.example.Andersen.entity.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MarksDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void createNewDateInJournal(Date date, ArrayList<StudentWithMarks> s){
        DateEntity dateEntity = jdbcTemplate.queryForObject("SELECT * FROM dates WHERE date = ?",
                new Object[]{date}, new BeanPropertyRowMapper<>(DateEntity.class));
        jdbcTemplate.batchUpdate("INSERT INTO marksforlessons(answer, question, student_id, date_id) VALUES (?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StudentWithMarks student = s.get(i);
                System.out.println(student);
                ps.setInt(1, student.getMarksForLesson().getAnswer());
                ps.setInt(2, student.getMarksForLesson().getQuestion());
                ps.setInt(3, student.getId());
                ps.setInt(4, dateEntity.getId());
                jdbcTemplate.update("UPDATE students SET score = (SELECT score FROM students WHERE id = ?) + ? WHERE ID = ?", student.getId(),
                        student.getMarksForLesson().getAnswer() + student.getMarksForLesson().getQuestion(), student.getId());
            }

            @Override
            public int getBatchSize() {
                return s.size();
            }
        });
    }


}
