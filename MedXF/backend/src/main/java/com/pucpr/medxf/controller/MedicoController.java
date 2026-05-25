package com.pucpr.medxf.controller;

import com.pucpr.medxf.domain.medico.dto.CadastroPaciente;
import com.pucpr.medxf.domain.paciente.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medico")
@RequiredArgsConstructor
public class MedicoController {

    private final PacienteService pacienteService;

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

    @PostMapping("/triagem")
    public String cadastrarPaciente(@Valid CadastroPaciente cadastroPaciente) {

        pacienteService.cadastrarPaciente(cadastroPaciente);

        return "redirect:/medico/pacientes";
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
    public String paginaPacientesCadastradosMedico(Model model) {

        var pacientes = pacienteService.listarPacientes();

        model.addAttribute("pacientes", pacientes);

        return "html/pacientes-cadastrados/pacientes-cadastrados";
    }

    @GetMapping("/perfil")
    public String paginaPerfilMedico() {
        return "html/perfil-medico/perfil-medico";
    }

}
