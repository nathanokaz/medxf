package com.pucpr.medxf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/paciente")
@RequiredArgsConstructor
public class PacienteController {

    @GetMapping("/home")
    public String paginaHomePaciente() {
        return "html/home-paciente/home-paciente";
    }

}
