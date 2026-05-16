package com.pucpr.medxf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medico")
public class Medico {

    @GetMapping("/home")
    public String paginaHomeMedico() {
        return "/html/home-medico/home-medico";
    }

    @GetMapping("/login")
    public String paginaLoginMedico() {
        return "/html/login-medico/login-medico";
    }

    @GetMapping("/triagem")
    public String paginaNovaTriagemMedico() {
        return "html/nova-triagem/nova-triagem";
    }

}
