package com.example.Andersen.controllers;


import com.example.Andersen.dao.MarksDao;
import com.example.Andersen.dao.StudentDao;
import com.example.Andersen.dao.TeamsDao;
import com.example.Andersen.dao.VisitsDao;
import com.example.Andersen.entity.*;
import com.example.Andersen.parser.ExcelParser;
import com.example.Andersen.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class Restcontroller {



    @Autowired
    private ExcelParser excelParser;
    @Autowired
    private StudentService studentService;
    @Autowired
    private VisitsDao visitsDao;
    @Autowired
    private MarksDao marksDaoDao;
    @Autowired
    private TeamsDao teamsDao;
    @Autowired
    private StudentDao studentDao;


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



    @PostMapping("/updateVisits")
    public void presentDate(@RequestBody PresentStudents s){
        DateEntity dateEntity = visitsDao.dateExist(s.getDate());
        if (dateEntity == null){
            visitsDao.newDateAndCheck(s.getDate(), s.getPresentStudents());
            return;
        }
        visitsDao.updateExistDate(dateEntity.getId(), s.getPresentStudents());

    }


    @PostMapping("/updateMarks")
    public void updateMarks(@RequestBody PresentStudentsWithMarks s){
        DateEntity dateEntity = visitsDao.dateExist(s.getDate());
        marksDaoDao.createNewDateInJournal(dateEntity.getDate(),s.getStudentsWithMarks());
    }


    @GetMapping("/teams")
    public List<Teams> getTeams(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date){
        return teamsDao.getTeams(date);
    }

}
