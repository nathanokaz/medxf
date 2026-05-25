package com.pucpr.medxf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inicio")
public class AplicativoController {

    @GetMapping
    public String paginaInicio() {
        return "html/inicio/inicio";
    }

    @GetMapping("/login")
    public String paginaLoginMedico() {
        return "/html/login-medico/login-medico";
    }

}
