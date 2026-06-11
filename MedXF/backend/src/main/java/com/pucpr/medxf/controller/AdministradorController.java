package com.pucpr.medxf.controller;

import com.pucpr.medxf.domain.admin.dto.CadastroMedico;
import com.pucpr.medxf.domain.admin.dto.InformacoesPerfilAdmin;
import com.pucpr.medxf.domain.admin.service.AdminService;
import com.pucpr.medxf.domain.medico.dto.InformacoesPaciente;
import com.pucpr.medxf.domain.medico.service.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import java.io.IOException;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdminService adminService;
    private final MedicoService medicoService;


    @GetMapping("/cadastrar/medico")
    public String paginaCadastrarMedico() {
        return "html/cadastro-medico/cadastro-medico";
    }

    @PostMapping("/cadastrar/medico")
    public String cadastrarMedico(@Valid CadastroMedico cadastroMedico, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("erro", bindingResult.getFieldError().getDefaultMessage());
            return "html/cadastro-medico/cadastro-medico";
        }
        try {
            adminService.cadastrarMedico(cadastroMedico);
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "html/cadastro-medico/cadastro-medico";
        }
        return "redirect:/admin/home?cadastroSucesso=true";
    }

    @GetMapping("/home")
    public String paginaHomeAdmin(@RequestParam(required = false) Boolean cadastroSucesso, Model model) {
        var informacoes = adminService.informacoesNumericasHome();
        var medicos = adminService.listarMedicos();
        var adminInfos = adminService.InformacoesAdmin();
        var foto = adminService.pegarFotoAdmin();
        model.addAttribute("infos", informacoes);
        model.addAttribute("medicos", medicos);
        model.addAttribute("infosAdmin", adminInfos);
        model.addAttribute("foto", foto);
        model.addAttribute("cadastroSucesso", cadastroSucesso);
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
    public String editarInformacoesMedico(@PathVariable Integer id, CadastroMedico cadastroMedico, RedirectAttributes redirectAttributes) {
        adminService.editarMedico(id, cadastroMedico);
        redirectAttributes.addFlashAttribute("perfilAtualizado", true);
        return "redirect:/admin/editar/medico/" + id;
    }

    @GetMapping("/gerenciar/pacientes")
    public String paginaGerenciarPacientes(Model model) {
        var pacientes = adminService.listarPacientes();
        var foto = adminService.pegarFotoAdmin();
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("foto", foto);
        return "html/gerenciar-pacientes-admin/gerenciar-pacientes-admin";
    }

    @GetMapping("/editar/paciente/{id}")
    public String paginaEditarPaciente(@PathVariable Integer id, Model model) {
        var paciente = adminService.informacoesPaciente(id);
        model.addAttribute("paciente", paciente);
        return "html/perfil-paciente-admin/perfil-paciente-admin";
    }

    @PostMapping("/editar/paciente/{id}")
    public String editarPaciente(@PathVariable Integer id, InformacoesPaciente informacoesPaciente, RedirectAttributes redirectAttributes) {
        medicoService.editarInformacoesPaciente(id, informacoesPaciente);
        redirectAttributes.addFlashAttribute("perfilAtualizado", true);
        return "redirect:/admin/editar/paciente/" + id;
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
    public String editarPerfil(InformacoesPerfilAdmin informacoesPerfilAdmin, @RequestParam("foto") MultipartFile foto, RedirectAttributes redirectAttributes
    ) throws IOException {
        adminService.editarDadosAdmin(informacoesPerfilAdmin, foto);
        redirectAttributes.addFlashAttribute("perfilAtualizado", true);
        return "redirect:/admin/perfil";
    }
}