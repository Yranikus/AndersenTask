package com.example.Andersen;

import com.example.Andersen.dao.StudentDao;
import com.example.Andersen.dao.TeamsDao;
import com.example.Andersen.dao.VisitsDao;
import com.example.Andersen.entity.Student;
import com.example.Andersen.entity.Teams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestTests extends Assert {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeamsDao teamsDao;
    @Autowired
    private VisitsDao visitsDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TestRestTemplate restTemplate;


    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Teams> teams = new ArrayList<>();
    private Date today = new Date();

    @BeforeAll
    public void createStudents(){
        Student student1 = new Student("Игорь", 10, 15);
        student1.setStatus(1);
        Student student2 = new Student("Федор", 15, 20);
        Student student3 = new Student("Саня", 20, 35);
        Student student4 = new Student("Маша", 30, 55);
        Student student5 = new Student("Гриша", 40, 65);
        student2.setStatus(1);
        student3.setStatus(1);
        student4.setStatus(1);
        student5.setStatus(1);
        students.addAll(List.of(student1,student2,student3,student4,student5));
        ArrayList<Student> firstTeam = new ArrayList<>();
        firstTeam.add(student1);
        ArrayList<Student> secondTeam = new ArrayList<>();
        secondTeam.addAll(List.of(student2,student3));
        ArrayList<Student> thirdTeam = new ArrayList<>();
        thirdTeam.addAll(List.of(student4,student5));
        Teams team1 = new Teams(1,firstTeam,firstTeam.get(0).getName());
        Teams team2 = new Teams(2,secondTeam,secondTeam.get(0).getName());
        Teams team3 = new Teams(3,thirdTeam,thirdTeam.get(0).getName());
        teams.addAll(List.of(team1,team2,team3));
        teamsDao.saveTeams(teams);
    }

    @Order(1)
    @Test
    void getStudents() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8081/getlistofstudents", HttpMethod.GET,
                new HttpEntity<String>(new HttpHeaders()),String.class);
        System.out.println(responseEntity.getBody());
        Student[] studentsFromController = objectMapper.readValue(responseEntity.getBody(), Student[].class);
        assertEquals(responseEntity.getStatusCode().value(), 200);
        for (int i = 0; i < students.size(); i++){
            assertEquals(studentsFromController[i].getName(), students.get(i).getName());
        }
    }

//    @Order(2)
//    @Test
//    void getTeams() throws JsonProcessingException {
//        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8081/getteams?date=" + 2022 + "-"
//                + today.getMonth() + "-" + today.getDay(), HttpMethod.GET,
//                new HttpEntity<String>(new HttpHeaders()),String.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(responseEntity.getBody());
//        objectMapper.readValue(responseEntity.getBody(), Teams[].class);
//    }



    @AfterAll
    void clean(){
        jdbcTemplate.execute("DROP TABLE IF EXISTS marksforlessons");
        jdbcTemplate.execute("DROP TABLE IF EXISTS user_dates");
        jdbcTemplate.execute("DROP TABLE IF EXISTS dates");
        jdbcTemplate.execute("DROP TABLE IF EXISTS student_team");
        jdbcTemplate.execute("DROP TABLE IF EXISTS students");
        jdbcTemplate.execute("DROP TABLE IF EXISTS teams");
        jdbcTemplate.execute("DROP TABLE IF EXISTS databasechangelog");
        jdbcTemplate.execute("DROP TABLE IF EXISTS databasechangeloglock");
    }

}
