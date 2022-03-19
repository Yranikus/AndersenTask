package com.example.Andersen.controllers;

import com.example.Andersen.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class UploadsControllers {

    @Autowired
    private StudentService studentService;

    @PostMapping("/uploadecxel")
    public String uplodFile(@RequestParam("file") MultipartFile file){
        try {
            studentService.saveListOfUsers(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:createTeams";
    }

    @GetMapping("/createTeams")
    public String createTeams(){
        studentService.createTeams();
        return "redirect:listofstudents";
    }

    @PostMapping("/uploadTeams")
    public String uploadTeams(@RequestParam("file") MultipartFile file){
        try {
            studentService.saveListOfTeams(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:listofstudents";
    }

    @GetMapping("/listofstudents")
    public String students(){
        return "listofstudents";
    }

    @PostMapping("/activateordeactivate")
    public String students(@RequestParam int id, @RequestParam int status){
        studentService.updateStatus(id,status);
        return "redirect:listofstudents";
    }


    @PostMapping("/addStudent")
    public String addStudent(@RequestParam String name, @RequestParam int primaryscore, @RequestParam int numberofteam){
        studentService.addStudent(name, primaryscore, numberofteam);
        return "redirect:listofstudents";
    }


}
