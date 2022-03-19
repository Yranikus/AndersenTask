package com.example.Andersen.controllers;


import com.example.Andersen.dao.MarksDao;
import com.example.Andersen.dao.StudentDao;
import com.example.Andersen.dao.TeamsDao;
import com.example.Andersen.dao.VisitsDao;
import com.example.Andersen.entity.*;
import com.example.Andersen.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class Restcontroller {


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
        return studentDao.getAllActiveStudents();
    }

    @GetMapping("/getlistofstudents")
    public List<Student> getStudents(){
        return studentDao.getAll();
    }

    @GetMapping("/getactivelistofstudents")
    public List<Student> getActiveStudents(){
        return studentDao.getAllActiveStudents();
    }

    @PostMapping("/setrepo")
    public void setRepo(@RequestParam int id, @RequestParam String repo){
        teamsDao.setRepo(repo, id);
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
        System.out.println(s.toString());
        marksDaoDao.createNewDateInJournal(dateEntity.getDate(),s.getStudentsWithMarks());
    }


    @GetMapping("/getteams")
    public List<Teams> getTeams(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date){
        return teamsDao.getTeams(date);
    }

}
