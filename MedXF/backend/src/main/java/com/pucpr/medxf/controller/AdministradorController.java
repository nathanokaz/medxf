package com.pucpr.medxf.controller;

import com.pucpr.medxf.domain.admin.dto.CadastroMedico;
import com.pucpr.medxf.domain.admin.dto.InformacoesPerfilAdmin;
import com.pucpr.medxf.domain.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdminService adminService;

    @GetMapping("/cadastrar/medico")
    public String paginaCadastrarMedico() {
        return "html/cadastro-medico/cadastro-medico";
    }

    @PostMapping("/cadastrar/medico")
    public String cadastrarMedico(@Valid CadastroMedico cadastroMedico) {
        adminService.cadastrarMedico(cadastroMedico);
        return "redirect:/admin/home";
    }

    @GetMapping("/home")
    public String paginaHomeAdmin(Model model) {
        var informacoes = adminService.informacoesNumericasHome();
        var medicos = adminService.listarMedicos();
        var adminInfos = adminService.InformacoesAdmin();
        model.addAttribute("infos", informacoes);
        model.addAttribute("medicos", medicos);
        model.addAttribute("infosAdmin", adminInfos);
        return "html/home-admin/home-admin";
    }

    @GetMapping("/gerenciar/medicos")
    public String paginaGerenciarMedicos(Model model) {
        var medicos = adminService.listarMedicos();
        model.addAttribute("medicos", medicos);
        return "html/gerenciar-medicos/gerenciar-medicos";
    }

    @GetMapping("/gerenciar/pacientes")
    public String paginaGerenciarPacientes(Model model) {
        var pacientes = adminService.listarPacientes();
        model.addAttribute("pacientes", pacientes);
        return "html/gerenciar-pacientes-admin/gerenciar-pacientes-admin";
    }

    @GetMapping("/perfil")
    public String paginaPerfilAdmin(Model model) {
        var admin = adminService.pegarDadosAdmin();
        model.addAttribute("admin", admin);
        return "html/perfil-admin/perfil-admin";
    }

    @PostMapping("/perfil")
    public String editarPerfil(InformacoesPerfilAdmin informacoesPerfilAdmin) {
        adminService.editarDadosAdmin(informacoesPerfilAdmin);
        return "redirect:/inicio/login";
    }

}
