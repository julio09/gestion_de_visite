package com.example.ProjetM1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {

    @GetMapping("/home")
    public String showHome(){
        System.out.println("Main controller");
        return "index";
    }

    @GetMapping("/login")
    public String visiter(){
        return "login";
    }

}
