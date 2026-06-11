package com.pucpr.medxf.domain.paciente.service;

import com.pucpr.medxf.domain.paciente.Paciente;
import com.pucpr.medxf.domain.paciente.dto.EdicaoPaciente;
import com.pucpr.medxf.domain.paciente.repository.PacienteRepository;
import java.util.Optional;
import com.pucpr.medxf.domain.triagem.Triagem;
import com.pucpr.medxf.domain.user.User;
import com.pucpr.medxf.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.pucpr.medxf.domain.respostas.Resposta;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final UserRepository userRepository;

    public List<String> pegarInfosPaciente() {
        var paciente = pegarPaciente();
        List<String> lista = new ArrayList<>();
        lista.add(paciente.getPaciente().getNome());
        lista.add(paciente.getEmail());
        lista.add(paciente.getPaciente().getTelefone());
        return lista;
    }

    public List<Triagem> pegarTriagem() {
        var paciente = pegarPaciente().getPaciente();
        return paciente.getTriagens();
    }

    public String pegarFoto() {
        var paciente = pegarPaciente();
        return paciente.getPaciente().getFotoPerfil();
    }

    @Transactional
    public void editarInformacoesPaciente(EdicaoPaciente edicaoPaciente, MultipartFile foto) throws IOException {
        var paciente = pegarPaciente().getPaciente();
        var user = pegarPaciente();
        if (edicaoPaciente.email() != null && !edicaoPaciente.email().isBlank()) {
            if (!edicaoPaciente.email().equals(user.getEmail())
                    && userRepository.existsByEmail(edicaoPaciente.email())) {
                throw new RuntimeException("Esse email já existe.");
            }
            user.setEmail(edicaoPaciente.email());
        }
        if (edicaoPaciente.senha() != null && !edicaoPaciente.senha().isBlank()) {
            var senhaCriptografada = new BCryptPasswordEncoder().encode(edicaoPaciente.senha());
            user.setSenha(senhaCriptografada);
        }
        if (edicaoPaciente.telefone() != null && !edicaoPaciente.telefone().isBlank()) {
            if (!edicaoPaciente.telefone().equals(paciente.getTelefone())
                    && pacienteRepository.existsByTelefone(edicaoPaciente.telefone())) {
                throw new RuntimeException("Esse telefone já existe.");
            }
            paciente.setTelefone(edicaoPaciente.telefone());
        }
        if (!foto.isEmpty()) {
            String nomeArquivo = UUID.randomUUID() + "_" + foto.getOriginalFilename();
            Path caminho = Paths.get("uploads/" + nomeArquivo);
            Files.createDirectories(caminho.getParent());
            Files.write(caminho, foto.getBytes());
            paciente.setFotoPerfil(nomeArquivo);
        }
    }

    public User pegarPaciente() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return pacienteRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User não encontrado")).getUser();
    }

    public String pegarNivelAvaliacao() {
        var triagens = pegarPaciente().getPaciente().getTriagens();
        if (triagens == null || triagens.isEmpty()) {
            return "Não avaliado";
        }
        Triagem ultimaTriagem = triagens.get(triagens.size() - 1);
        int sintomas = 0;
        for (Resposta resposta : ultimaTriagem.getRespostas()) {
            if (resposta.isResposta()) {
                sintomas++;
            }
        }
        return sintomas > 6 ? "ALTO" : "BAIXO";
    }
    
    public String pegarUltimaConsulta() {
        var triagens = pegarPaciente().getPaciente().getTriagens();
        if (triagens == null || triagens.isEmpty()) {
            return "Nenhuma";
        }
        Triagem ultimaTriagem = triagens.stream()
                .max((t1, t2) -> t1.getCriado_em().compareTo(t2.getCriado_em()))
                .orElse(null);
        if (ultimaTriagem == null) {
            return "Nenhuma";
        }
        return ultimaTriagem.getCriado_em()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


}
