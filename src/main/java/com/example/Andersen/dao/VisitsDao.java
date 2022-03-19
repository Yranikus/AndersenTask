package com.example.Andersen.dao;

import com.example.Andersen.entity.DateEntity;
import com.example.Andersen.entity.rowmappers.PresentStudentRowMapper;
import com.example.Andersen.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class VisitsDao {

    private static Logger logger = Logger.getLogger(VisitsDao.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public DateEntity dateExist(Date date){
        DateEntity date1 = jdbcTemplate.query("SELECT * FROM dates WHERE date=?", new Object[]{date}, new BeanPropertyRowMapper<>(DateEntity.class))
                .stream().findAny().orElse(null);
        if (date1 == null) {
            return null;
        }
        return date1;
    }

    public List<Student> getPresentStudents(Date date){
       return jdbcTemplate.query("SELECT students.id, name, primaryscore, score, user_dates.date_id FROM students RIGHT JOIN user_dates ON id = user_dates.user_id LEFT JOIN dates d on d.id = user_dates.date_id WHERE status=1",
               new PresentStudentRowMapper());
    }

    public void newDateAndCheck(Date date, int[] check){
        jdbcTemplate.update("INSERT INTO dates(date) VALUES(?)",date);
        jdbcTemplate.batchUpdate("INSERT INTO user_dates(user_id, date_id) VALUES (?, (SELECT MAX(id) FROM dates))", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, check[i]);
            }

            @Override
            public int getBatchSize() {
                return check.length;
            }
        });
    }

    public void updateExistDate(int dateId, int[] check){
        try {

            jdbcTemplate.batchUpdate("INSERT INTO user_dates(user_id, date_id) VALUES (?, ?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, check[i]);
                    ps.setInt(2, dateId);
                }

                @Override
                public int getBatchSize() {
                    return check.length;
                }
            });
        }
        catch (Exception e){
            logger.log(Level.WARNING, "Попытка вставить повторяющиеся значения!");
        }
    }

}
