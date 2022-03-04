package com.example.Andersen.dao;

import com.example.Andersen.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Student> getAllDesc(){
        return jdbcTemplate.query("SELECT id, name, primaryscore, score FROM students ORDER BY 3 DESC", new BeanPropertyRowMapper<>(Student.class));
    }

    public List<Student> getAll(){
        return jdbcTemplate.query("SELECT id, name, primaryscore, score FROM students", new BeanPropertyRowMapper<>(Student.class));
    }

    public void saveList(ArrayList<Student> students) {

        jdbcTemplate.batchUpdate("INSERT INTO students(name,primaryscore,score) VALUES (?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Student student = students.get(i);
                ps.setString(1, student.getName());
                ps.setInt(2,student.getPrimaryScore());
                ps.setInt(3, student.getScore());
            }

            @Override
            public int getBatchSize() {
                return students.size();
            }
        });

    }


}
