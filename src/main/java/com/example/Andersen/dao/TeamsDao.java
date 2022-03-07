package com.example.Andersen.dao;

import com.example.Andersen.entity.Student;
import com.example.Andersen.entity.StudentWithMarksRowMapper;
import com.example.Andersen.entity.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TeamsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void createTeam(Student leader){
        jdbcTemplate.update("INSERT INTO teams (leader) VALUES (?)", leader.getName());
        jdbcTemplate.update("INSERT INTO student_team (STUDENT_ID, TEAM_ID) VALUES (?, (SELECT MAX(id) FROM teams))", leader.getId());
    }

    public String getLeader(int id){
       return jdbcTemplate.queryForObject("SELECT leader FROM TEAMS WHERE id=? LIMIT 1", new Object[]{id}, String.class);
    }

    public ArrayList<Teams> getTeams(Date date) {
        ArrayList<Teams> teams = new ArrayList<>();
        int firstNumber = getFirstPKofTeam();
        int i = 1;
        int numbers = getNumbersOfTeams() + firstNumber;
        for (; firstNumber < numbers; firstNumber++){
            Teams team = new Teams(i, (ArrayList<Student>) jdbcTemplate.query("SELECT students.id , name , primaryscore, score, date_id, (SELECT answer \n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t  \t\tFROM marksforlessons\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t \t\t LEFT JOIN dates ON dates.id = date_id\n" +
                    " \t\t\t\t\t\t\t\t\t\t\t\t \t\t WHERE student_id = students.id AND date = ?) answer,\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t \t\t (SELECT question \n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t \t\t  FROM marksforlessons\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t \t\t  LEFT JOIN dates ON dates.id = date_id\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t \t\t  WHERE student_id = students.id AND date = ?) question  FROM students\n" +
                    "LEFT JOIN student_team\n" +
                    "ON students.id = student_team.student_id\n" +
                    "LEFT JOIN teams\n" +
                    "ON teams.id = student_team.team_id\n" +
                    "LEFT JOIN user_dates\n" +
                    "ON user_dates.user_id = students.id\n" +
                    "LEFT JOIN dates\n" +
                    "ON user_dates.date_id = dates.id\n" +
                    "WHERE teams.id = ? AND date = ?", new StudentWithMarksRowMapper(), date, date, firstNumber, date), getLeader(firstNumber)
                    );
            teams.add(team);
            i++;
        }
        return teams;
    }

    public void addToTeam(int student_id, int team_id){
        jdbcTemplate.update("INSERT INTO student_team (STUDENT_ID, TEAM_ID) VALUES (?, ?)", student_id, team_id);
    }

    public Integer getNumbersOfTeams(){
        return jdbcTemplate.queryForObject("SELECT COUNT(id) FROM teams", Integer.class);
    }

    public int getFirstPKofTeam(){
        Integer j = jdbcTemplate.queryForObject("SELECT id FROM teams LIMIT 1", Integer.class);
        if (j == null) return 1;
        return j;
    }


}
