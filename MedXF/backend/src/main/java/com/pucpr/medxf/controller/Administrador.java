package com.pucpr.medxf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class Administrador {

    @GetMapping("/cadastrar/medico")
    public String paginaCadastrarMedico() {
        return "html/cadastro-medico/cadastro-medico";
    }

    @GetMapping("/home")
    public String paginaHomeAdmin() {
        return "html/home-admin/home-admin";
    }

    @GetMapping("/login")
    public String paginaLoginAdmin() {
        return "html/login-admin/login-admin";
    }

}
