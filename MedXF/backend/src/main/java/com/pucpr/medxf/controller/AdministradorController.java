package com.pucpr.medxf.controller;

import com.pucpr.medxf.domain.admin.dto.CadastroMedico;
import com.pucpr.medxf.domain.admin.dto.InformacoesPerfilAdmin;
import com.pucpr.medxf.domain.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        var foto = adminService.pegarFotoAdmin();
        model.addAttribute("infos", informacoes);
        model.addAttribute("medicos", medicos);
        model.addAttribute("infosAdmin", adminInfos);
        model.addAttribute("foto", foto);
        return "html/home-admin/home-admin";
    }

    @GetMapping("/gerenciar/medicos")
    public String paginaGerenciarMedicos(Model model) {
        var medicos = adminService.listarMedicos();
        var foto = adminService.pegarFotoAdmin();
        model.addAttribute("medicos", medicos);
        model.addAttribute("foto", foto);
        return "html/gerenciar-medicos/gerenciar-medicos";
    }

    @GetMapping("/editar/medico/{id}")
    public String paginaEditarMedico(@PathVariable Integer id, Model model) {
        var medico = adminService.informacoesMedico(id);
        model.addAttribute("medico", medico);
        return "html/perfil-medico-admin/perfil-medico-admin";
    }

    @PostMapping("/editar/medico/{id}")
    public String editarInformacoesMedico(@PathVariable Integer id, CadastroMedico cadastroMedico) {
        adminService.editarMedico(id, cadastroMedico);
        return "redirect:/admin/home";
    }

    @GetMapping("/gerenciar/pacientes")
    public String paginaGerenciarPacientes(Model model) {
        var pacientes = adminService.listarPacientes();
        var foto = adminService.pegarFotoAdmin();
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("foto", foto);
        return "html/gerenciar-pacientes-admin/gerenciar-pacientes-admin";
    }

    @GetMapping("/perfil")
    public String paginaPerfilAdmin(Model model) {
        var admin = adminService.pegarDadosAdmin();
        var foto = adminService.pegarFotoAdmin();
        model.addAttribute("admin", admin);
        model.addAttribute("foto", foto);
        return "html/perfil-admin/perfil-admin";
    }

    @PostMapping("/perfil")
    public String editarPerfil(InformacoesPerfilAdmin informacoesPerfilAdmin, @RequestParam("foto") MultipartFile foto) throws IOException {
        adminService.editarDadosAdmin(informacoesPerfilAdmin, foto);
        return "redirect:/inicio/login";
    }

}