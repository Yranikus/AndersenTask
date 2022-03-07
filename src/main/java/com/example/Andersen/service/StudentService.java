package com.example.Andersen.service;

import com.example.Andersen.dao.StudentDao;
import com.example.Andersen.dao.TeamsDao;
import com.example.Andersen.entity.Student;
import com.example.Andersen.parser.ExcelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeamsDao teamsDao;
    @Autowired
    private ExcelParser excelParser;

    public void saveListOfUsers(InputStream inputStream) throws IOException {
        studentDao.saveList(excelParser.parse(inputStream));
    }

    public void createTeams(){
        List<Student> students = studentDao.getAllDesc();
        int teams;
        if (students.size() % 4 == 0) {
            teams = students.size() / 4;
        }
        else {
            teams = students.size() / 4 + 1;
        }
        int counter = 0;
        for (int i = 0; i < teams; i++) {
            teamsDao.createTeam(students.get(counter));
            counter++;
        }
        for (; counter < students.size();) {
            int j = teamsDao.getFirstPKofTeam();
            int teamsNumber = j + teams - 1;
            for (; teamsNumber >= j; j++) {
                teamsDao.addToTeam(students.get(counter).getId(), j);
                counter++;
                if (counter == students.size()) break;
            }
        }
    }

}
