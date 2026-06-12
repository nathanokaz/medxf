package com.pucpr.medxf.controller;

import com.pucpr.medxf.domain.paciente.Paciente;
import com.pucpr.medxf.domain.paciente.dto.EdicaoPaciente;
import com.pucpr.medxf.domain.paciente.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


@Controller
@RequestMapping("/paciente")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping("/home")
    public String paginaHomePaciente(Model model) {
        var paciente = pacienteService.pegarInfosPaciente();
        var foto = pacienteService.pegarFoto();
        model.addAttribute("paciente", paciente);
        model.addAttribute("pacienteFoto", foto);
        model.addAttribute("nivelAvaliacao", pacienteService.pegarNivelAvaliacao());
        model.addAttribute("ultimaConsulta", pacienteService.pegarUltimaConsulta());
        return "html/home-paciente/home-paciente";
    }

    @GetMapping("/triagem")
    public String paginaTriagemRealizada(Model model) {
        Paciente paciente = pacienteService.pegarPaciente().getPaciente();
        model.addAttribute("pacienteNome", paciente.getNome());
        model.addAttribute("respostas", pacienteService.pegarTriagem());
        return "html/triagem-paciente/triagem-paciente";
    }

    @GetMapping("/perfil")
    public String paginaPerfilPaciente(Model model) {
        var infosPaciente = pacienteService.pegarInfosPaciente();
        var pacienteFoto = pacienteService.pegarFoto();
        model.addAttribute("paciente", infosPaciente);
        model.addAttribute("pacienteFoto", pacienteFoto);
        return "html/perfil-paciente/perfil-paciente";
    }

    @PostMapping("/perfil")
    public String editarPerfilPaciente(EdicaoPaciente edicaoPaciente, @RequestParam("foto") MultipartFile foto, RedirectAttributes redirectAttributes
    ) throws IOException {
        pacienteService.editarInformacoesPaciente(edicaoPaciente, foto);
        redirectAttributes.addFlashAttribute("perfilAtualizado", true);
        return "redirect:/paciente/perfil";
    }

}
