package com.example.Andersen.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class UIController {


    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/teams")
    public String teams(){
        return "teams";
    }

    @GetMapping("/visits")
    public String visits(){
        return "schedule";
    }

    @GetMapping("/quiz")
    public String quiz(){
        return "quiz";
    }


}
