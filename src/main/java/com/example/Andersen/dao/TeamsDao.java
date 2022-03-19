package com.example.Andersen.dao;

import com.example.Andersen.entity.Student;
import com.example.Andersen.entity.rowmappers.StudentWithMarksRowMapper;
import com.example.Andersen.entity.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class TeamsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentDao studentDao;



    public void createTeam(String leader){
        jdbcTemplate.update("INSERT INTO teams (leader) VALUES (?)", leader);
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
            ArrayList<Student> students =  (ArrayList<Student>) jdbcTemplate.query("SELECT students.id , name , primaryscore, score, date_id, (SELECT answer \n" +
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
                    "WHERE teams.id = ? AND date = ? AND status = 1", new StudentWithMarksRowMapper(), date, date, firstNumber, date);
            if (students.size() != 0) {
                Teams team = new Teams(i, students, getLeader(firstNumber));
                team.setRepo(getRepo(firstNumber));
                teams.add(team);
            }
            i++;
        }
        return teams;
    }

    public void setRepo(String repo, int id){
        jdbcTemplate.update("UPDATE teams SET repo = ? WHERE id = ?", repo, id);
    }

    public String getRepo(int id){
        return jdbcTemplate.queryForObject("SELECT repo FROM teams WHERE id=?", new Object[]{id}, String.class);
    }


    public void addToTeam(int student_id, int team_id){
        jdbcTemplate.update("INSERT INTO student_team (STUDENT_ID, TEAM_ID) VALUES (?, ?)", student_id, team_id);
    }

    public void saveTeams(ArrayList<Teams> teams){
        final int[] pk = {1};
        for (Teams t : teams){
            createTeam(t.getLeader());
            studentDao.saveList(t.getStudents());
            jdbcTemplate.batchUpdate("INSERT INTO student_team(student_id, team_id) VALUES (?,?)", new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1,pk[0]);
                    ps.setInt(2,t.getNumberOfTeam());
                    pk[0]++;
                }

                @Override
                public int getBatchSize() {
                    return t.getStudents().size();
                }
            });

        }
    }

    public Integer getNumbersOfTeams(){
        return jdbcTemplate.queryForObject("SELECT COUNT(id) FROM teams", Integer.class);
    }

    public int getFirstPKofTeam(){
        Integer j = jdbcTemplate.queryForObject("SELECT id FROM teams ORDER BY 1 ASC LIMIT 1", Integer.class);
        if (j == null) return 1;
        return j;
    }


}
