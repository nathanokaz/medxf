package com.pucpr.medxf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medico")
public class MedicoController {

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

    @GetMapping("/avaliacao")
    public String paginaAvaliacaoMedico() {
        return "html/avaliacao-medico/avaliacao-medico";
    }

    @GetMapping("/gerenciar/paciente")
    public String paginaGerenciarPacienteMedico() {
        return "html/gerenciar-paciente/gerenciar-paciente";
    }
    @GetMapping("/pacientes")
    public String paginaPacientesCadastradosMedico() {
        return "html/pacientes-cadastrados/pacientes-cadastrados";
    }

    @GetMapping("/perfil")
    public String paginaPerfilMedico() {
        return "html/perfil-medico/perfil-medico";
    }
}
