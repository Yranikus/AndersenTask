package com.example.Andersen.dao;

import com.example.Andersen.entity.Student;
import com.example.Andersen.entity.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public ArrayList<Teams> getTeams() {
        ArrayList<Teams> teams = new ArrayList<>();
        int firstNumber = getFirstPKofTeam();
        int i = 1;
        int numbers = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM teams", Integer.class) + firstNumber;
        for (; firstNumber < numbers; firstNumber++){
            Teams team = new Teams(i, (ArrayList<Student>) jdbcTemplate.query("SELECT students.id , name , primaryscore, score FROM students\n" +
                    "INNER JOIN student_team \n" +
                    "ON students.id = student_team.student_id\n" +
                    "INNER JOIN teams\n" +
                    "ON teams.id = student_team.team_id\n" +
                    "WHERE team_id = ?", new Object[]{firstNumber} , new BeanPropertyRowMapper<>(Student.class)), getLeader(firstNumber)
                    );
            teams.add(team);
            i++;
        }
        return teams;
    }

    public void addToTeam(int student_id, int team_id){
        jdbcTemplate.update("INSERT INTO student_team (STUDENT_ID, TEAM_ID) VALUES (?, ?)", student_id, team_id);
    }

    public int getFirstPKofTeam(){
        Integer j = jdbcTemplate.queryForObject("SELECT id FROM teams LIMIT 1", Integer.class);
        if (j == null) return 1;
        return j;
    }


}
