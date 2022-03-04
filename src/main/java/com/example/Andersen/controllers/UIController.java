package com.example.Andersen.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class UIController {

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/visits")
    public String visits(){
        return "schedule";
    }

}
