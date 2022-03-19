package com.example.Andersen;

import com.example.Andersen.dao.StudentDao;
import com.example.Andersen.dao.TeamsDao;
import com.example.Andersen.dao.VisitsDao;
import com.example.Andersen.entity.Student;
import com.example.Andersen.entity.Teams;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DAOtests extends Assert {

	@Autowired
	private StudentDao studentDao;
	@Autowired
	private TeamsDao teamsDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private VisitsDao visitsDao;

	private ArrayList<Student> students = new ArrayList<>();
	private ArrayList<Teams> teams = new ArrayList<>();

	@BeforeAll
	public void createStudents(){
		Student student1 = new Student("Игорь", 10, 15);
		Student student2 = new Student("Федор", 15, 20);
		Student student3 = new Student("Саня", 20, 35);
		Student student4 = new Student("Маша", 30, 55);
		Student student5 = new Student("Гриша", 40, 65);
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
	void uploadListOfStudentsTest() {
		List<Student> studentsFromDB = studentDao.getAll();
		assertEquals(studentsFromDB.get(0).getName(),students.get(0).getName());
		assertEquals(studentsFromDB.get(1).getName(),students.get(1).getName());
		assertEquals(studentsFromDB.get(2).getName(),students.get(2).getName());
		assertEquals(studentsFromDB.get(3).getName(),students.get(3).getName());
	}

	@Order(2)
	@Test
	void activeStudentsTest(){
		studentDao.deactivateOrActivateStudent(1,0);
		studentDao.deactivateOrActivateStudent(2,0);
		assertEquals(studentDao.getAllActiveStudents().size(),3);
		studentDao.deactivateOrActivateStudent(2,1);
		assertEquals(studentDao.getAllActiveStudents().size(),4);
		assertEquals(studentDao.getAll().size(),students.size());
		studentDao.deactivateOrActivateStudent(1,1);
	}

	@Order(3)
	@Test
	void UpdateVisitsTest(){
		int[] check = new int[students.size() - 1];
		for (int i = 0; i < check.length; i++){
			check[i] = i + 1;
		}
		Date today = new Date();
		visitsDao.newDateAndCheck(new Date(), check);
		List<Student> presents = visitsDao.getPresentStudents(today);
		System.out.println(presents);
		assertEquals(presents.size(), check.length);
		assertEquals(teamsDao.getTeams(today).size(), teams.size());
	}

	@Order(4)
	@Test
	void exitsDateTest(){
		Date date = new Date();
		Date getDate = visitsDao.dateExist(date).getDate();
		assertEquals(getDate.getDay(),date.getDay());
		assertEquals(getDate.getDate(),date.getDate());
		assertEquals(getDate.getMonth(),date.getMonth());
	}

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
