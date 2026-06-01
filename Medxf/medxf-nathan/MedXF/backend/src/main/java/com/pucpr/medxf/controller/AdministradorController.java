package com.pucpr.medxf.controller;

import com.pucpr.medxf.domain.admin.dto.CadastroMedico;
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
    public String paginaHomeAdmin() {
        return "html/home-admin/home-admin";
    }

    @GetMapping("/login")
    public String paginaLoginAdmin() {
        return "html/login-admin/login-admin";
    }

    @GetMapping("/gerenciar/medicos")
    public String paginaGerenciarMedicos(Model model) {
        var medicos = adminService.listarMedicos();
        model.addAttribute("medicos", medicos);
        return "html/gerenciar-medicos/gerenciar-medicos";
    }

}
