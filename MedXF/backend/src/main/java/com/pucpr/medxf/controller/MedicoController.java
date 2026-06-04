package com.pucpr.medxf.controller;

import com.pucpr.medxf.domain.medico.dto.AvaliacaoMedico;
import com.pucpr.medxf.domain.medico.dto.CadastroPaciente;
import com.pucpr.medxf.domain.medico.dto.InformacoesPerfil;
import com.pucpr.medxf.domain.medico.service.MedicoService;
import com.pucpr.medxf.domain.paciente.Paciente;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/medico")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

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
        Paciente paciente = medicoService.cadastrarPaciente(cadastroPaciente);
        return "redirect:/medico/avaliacao?pacienteId=" + paciente.getId();
    }

    @GetMapping("/avaliacao")
    public String paginaAvaliacaoMedico(@RequestParam Integer pacienteId, Model model) {
        model.addAttribute("pacienteId", pacienteId);
        return "html/avaliacao-medico/avaliacao-medico";
    }

    @PostMapping("/avaliacao")
    public String registrarAvaliacao(AvaliacaoMedico avaliacaoMedico) {
        medicoService.cadastrarAvaliacao(avaliacaoMedico);
        return "redirect:/medico/home";
    }

    @GetMapping("/gerenciar/paciente")
    public String paginaGerenciarPacienteMedico() {
        return "html/gerenciar-paciente/gerenciar-paciente";
    }

    @GetMapping("/pacientes")
    public String paginaPacientesCadastradosMedico(Model model) {
        var pacientes = medicoService.listarPacientes();
        model.addAttribute("pacientes", pacientes);
        return "html/pacientes-cadastrados/pacientes-cadastrados";
    }

    @GetMapping("/perfil")
    public String paginaPerfilMedico(Model model) {
        var medico = medicoService.informacoesMedico();
        model.addAttribute("medico", medico);
        return "html/perfil-medico/perfil-medico";
    }

    @PostMapping("/perfil")
    public String editarPerfil(InformacoesPerfil informacoesPerfil) {
        medicoService.editarInformacoesMedico(informacoesPerfil);
        return "redirect:/inicio/login";
    }

}
