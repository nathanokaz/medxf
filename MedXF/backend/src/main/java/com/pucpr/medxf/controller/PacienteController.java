package com.pucpr.medxf.controller;

import com.pucpr.medxf.domain.paciente.Paciente;
import com.pucpr.medxf.domain.paciente.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/paciente")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping("/home")
    public String paginaHomePaciente(Model model) {
        var paciente = pacienteService.pegarInfosPaciente();
        model.addAttribute("paciente", paciente);
        return "html/home-paciente/home-paciente";
    }

    @GetMapping("/triagem")
    public String paginaTriagemRealizada(Model model) {
        Paciente paciente = pacienteService.pegarPaciente().getPaciente();
        model.addAttribute("pacienteNome", paciente.getNome());
        model.addAttribute("respostas", pacienteService.pegarTriagem());
        return "html/triagem-paciente/triagem-paciente";
    }


}
