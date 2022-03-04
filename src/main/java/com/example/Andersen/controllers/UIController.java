package com.example.Andersen.controllers;


import com.example.Andersen.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class UIController {

    @Autowired
    private StudentService studentService;


    @GetMapping
    public String index(){
        return "index";
    }

    @PostMapping("/uploadecxel")
    public String uplodFile(@RequestParam("file") MultipartFile file){
        try {
            studentService.saveListOfUsers(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect: visits";
    }


    @GetMapping("/visits")
    public String visits(){
        return "schedule";
    }

}
