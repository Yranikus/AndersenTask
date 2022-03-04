package com.example.Andersen.controllers;


import com.example.Andersen.dao.StudentDao;
import com.example.Andersen.dao.VisitsDao;
import com.example.Andersen.entity.DateEntity;
import com.example.Andersen.entity.PresentStudents;
import com.example.Andersen.entity.Student;
import com.example.Andersen.parser.ExcelParser;
import com.example.Andersen.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/")
public class Restcontroller {

    @Autowired
    private StudentService studentService;
    @Autowired
    private VisitsDao visitsDao;

    @Autowired
    private StudentDao studentDao;

    @PostMapping("/uploadecxel")
    public void uplodFile(@RequestParam("file") MultipartFile file){
        try {
            studentService.saveListOfUsers(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getpresentstudents")
    public List<Student> getPresentStudents(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date){
        System.out.println(date);
        if (visitsDao.dateExist(date) != null){
            return visitsDao.getPresentStudents(date);
        }
        return studentDao.getAll();
    }

    @GetMapping("/getlistofstudents")
    public List<Student> getStudents(){
        return studentDao.getAll();
    }


    @GetMapping("/createTeams")
    public void createTeams(){
        studentService.createTeams();
    }

    @PostMapping("/updateVisits")
    public void presentDate(@RequestBody PresentStudents s){
        DateEntity dateEntity = visitsDao.dateExist(s.getDate());
        if (dateEntity == null){
            visitsDao.newDateAndCheck(s.getDate(), s.getPresentStudents());
            return;
        }
        visitsDao.updateExistDate(dateEntity.getId(), s.getPresentStudents());

    }



}
