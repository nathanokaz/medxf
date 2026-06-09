package com.pucpr.medxf.controller;

import com.pucpr.medxf.domain.medico.dto.*;
import com.pucpr.medxf.domain.medico.service.MedicoService;
import com.pucpr.medxf.domain.paciente.Paciente;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medico")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;


    @GetMapping("/home")
    public String paginaHomeMedico(Model model) {
        model.addAttribute("medico", medicoService.informacoesMedico());
        model.addAttribute("infos", medicoService.informacoesNumericasHome());
        model.addAttribute("pacientesHome", medicoService.listarPacientesHome());
        return "/html/home-medico/home-medico";
    }

    @GetMapping("/triagem")
    public String paginaNovaTriagemMedico() {
        return "html/nova-triagem/nova-triagem";
    }

    @PostMapping("/triagem")
    public String cadastrarPaciente(@Valid CadastroPaciente cadastroPaciente) {
        Paciente paciente = medicoService.cadastrarPaciente(cadastroPaciente);
        return "redirect:/medico/triagem/socioeconomica?pacienteId=" + paciente.getId();
    }

    @GetMapping("/triagem/socioeconomica")
    public String paginaTriagemSocioeconomicaMedico(@RequestParam Integer pacienteId, Model model) {
        model.addAttribute("pacienteId", pacienteId);
        return "html/triagem-socioeconomica/triagem-socioeconomica";
    }

    @PostMapping("/triagem/socioeconomica")
    public String cadastrarSocioeconomicaPaciente(@ModelAttribute CadastroSocioeconomico dados) {
        medicoService.salvarSocioeconomica(dados);
        return "redirect:/medico/avaliacao?pacienteId=" + dados.pacienteId();
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

    @GetMapping("/gerenciar/paciente/{id}")
    public String paginaGerenciarPacienteMedico(@PathVariable Integer id, Model model) {
        model.addAttribute("paciente", medicoService.informacoesPaciente(id));
        var triagens = medicoService.buscarTriagensPorPaciente(id);
        var respostas = (triagens == null || triagens.isEmpty())
                ? java.util.Collections.emptyList()
                : medicoService.buscarRespostasPorTriagens(triagens);
        model.addAttribute("respostas", respostas);
        return "html/gerenciar-paciente/gerenciar-paciente";
    }

    @PostMapping("/gerenciar/paciente/{id}")
    public String editarPaciente(@PathVariable Integer id, InformacoesPaciente informacoesPaciente) {
        medicoService.editarInformacoesPaciente(id, informacoesPaciente);
        return "redirect:/medico/home";
    }

    @GetMapping("/pacientes")
    public String paginaPacientesCadastradosMedico(Model model) {
        model.addAttribute("pacientes", medicoService.listarPacientes());
        return "html/pacientes-cadastrados/pacientes-cadastrados";
    }

    @GetMapping("/perfil")
    public String paginaPerfilMedico(Model model) {
        model.addAttribute("medico", medicoService.informacoesMedico());
        return "html/perfil-medico/perfil-medico";
    }

    @PostMapping("/perfil")
    public String editarPerfil(InformacoesPerfil informacoesPerfil) {
        medicoService.editarInformacoesMedico(informacoesPerfil);
        return "redirect:/inicio/login";
    }
}