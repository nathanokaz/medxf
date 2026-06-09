package com.pucpr.medxf.domain.medico.service;

import com.pucpr.medxf.domain.Socioeconomico.Socioeconomico;
import com.pucpr.medxf.domain.Socioeconomico.repository.SocioeconomicoRepository;
import com.pucpr.medxf.domain.medico.Medico;
import com.pucpr.medxf.domain.medico.dto.*;
import com.pucpr.medxf.domain.medico.repository.MedicoRepository;
import com.pucpr.medxf.domain.paciente.Paciente;
import com.pucpr.medxf.domain.paciente.repository.PacienteRepository;
import com.pucpr.medxf.domain.pergunta.Pergunta;
import com.pucpr.medxf.domain.pergunta.repository.PerguntaRepository;
import com.pucpr.medxf.domain.respostas.Resposta;
import com.pucpr.medxf.domain.respostas.repository.RespostaRepository;
import com.pucpr.medxf.domain.triagem.Triagem;
import com.pucpr.medxf.domain.triagem.repository.TriagemReposiotry;
import com.pucpr.medxf.domain.user.User;
import com.pucpr.medxf.domain.user.dto.UserRoles;
import com.pucpr.medxf.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final RespostaRepository respostaRepository;
    private final TriagemReposiotry triagemReposiotry;
    private final UserRepository userRepository;
    private final SocioeconomicoRepository socioeconomicoRepository;
    private final PerguntaRepository perguntaRepository;

    @Transactional
    public Paciente cadastrarPaciente(CadastroPaciente cadastroPaciente) {
        if (pacienteRepository.existsByEmail(cadastroPaciente.email())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        if (pacienteRepository.existsByTelefone(cadastroPaciente.telefone())) {
            throw new IllegalArgumentException("Telefone já cadastrado.");
        }
        if (pacienteRepository.existsByCpf(cadastroPaciente.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        var senhaCriptografada = new BCryptPasswordEncoder().encode(cadastroPaciente.senha());
        User user = User.builder()
                .email(cadastroPaciente.email())
                .role(UserRoles.PACIENTE)
                .senha(senhaCriptografada)
                .criadoEm(LocalDateTime.now())
                .build();
        Paciente paciente = Paciente.builder()
                .nome(cadastroPaciente.nome().trim())
                .nascimento(cadastroPaciente.nascimento())
                .sexo(cadastroPaciente.sexo())
                .email(cadastroPaciente.email().trim().toLowerCase())
                .telefone(cadastroPaciente.telefone().trim())
                .historico1(cadastroPaciente.historico1())
                .historico2(cadastroPaciente.historico2())
                .cpf(cadastroPaciente.cpf())
                .medico(pegarMedico())
                .criado_em(LocalDateTime.now())
                .user(user)
                .build();
        userRepository.save(user);
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public void cadastrarAvaliacao(AvaliacaoMedico avaliacaoMedico) {
        Paciente paciente = pacienteRepository.findById(avaliacaoMedico.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        Triagem triagem = Triagem.builder()
                .criado_em(LocalDateTime.now())
                .medico(pegarMedico())
                .paciente(paciente)
                .build();
        triagemReposiotry.save(triagem);
        for (Respostas dto : avaliacaoMedico.respostas()) {
            Pergunta pergunta = perguntaRepository.findById(dto.pergunta())
                    .orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));
            Resposta resposta = Resposta.builder()
                    .resposta(Boolean.TRUE.equals(dto.resposta()))
                    .observacao(dto.observacao())
                    .triagem(triagem)
                    .pergunta(pergunta)
                    .build();
            respostaRepository.save(resposta);
        }
    }

    @Transactional
    public void salvarSocioeconomica(CadastroSocioeconomico dados) {
        Paciente paciente = pacienteRepository.findById(dados.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        Socioeconomico socio = new Socioeconomico();
        socio.setPaciente(paciente);
        socio.setNomeResponsavel(dados.nomeResponsavel());
        socio.setQuantidadeMoradores(dados.quantidadeMoradores());
        socio.setRenda(dados.renda());
        socio.setInternet(dados.internet());
        socio.setBeneficio(dados.beneficio());
        socio.setPlanoSaude(dados.planoSaude());
        socio.setMoradia(dados.moradia());
        socioeconomicoRepository.save(socio);
    }

    public Socioeconomico buscarSocioeconomico(Integer pacienteId) {
        return socioeconomicoRepository.findAll()
                .stream()
                .filter(s -> s.getPaciente().getId().equals(pacienteId))
                .findFirst()
                .orElse(null);
    }

    public List<Triagem> buscarTriagensPorPaciente(Integer pacienteId) {
        return triagemReposiotry.findAllByPacienteId(pacienteId);
    }

    public List<Resposta> buscarRespostasPorTriagens(List<Triagem> triagens) {
        if (triagens == null || triagens.isEmpty()) {
            return new ArrayList<>();
        }
        return respostaRepository.findAllByTriagemIn(triagens);
    }

    public List<ListaPaciente> listarPacientes() {
        var pacientes = pacienteRepository.findAll();
        return pacientes.stream().map(ListaPaciente::new).toList();
    }

    public List<ListaPacientesHome> listarPacientesHome() {
        var medico = pegarMedico().getId();
        var pacientes = pacienteRepository.findAllByMedicoId(medico);
        List<ListaPacientesHome> lista = new ArrayList<>();
        for (Paciente p : pacientes) {
            int idade = Period.between(p.getNascimento(), LocalDate.now()).getYears();
            int sintomas = pegarSintomas(p);
            Risco risco = verificarRisco(sintomas);
            LocalDate data = pegarDataTriagem(p);
            lista.add(new ListaPacientesHome(
                    p.getId(),
                    p.getNome(),
                    p.getEmail(),
                    idade,
                    sintomas,
                    risco,
                    data,
                    Status.CONCLUIDO
            ));
        }
        return lista;
    }

    private LocalDate pegarDataTriagem(Paciente paciente) {
        LocalDate data = null;
        var triagens = triagemReposiotry.findAllByPacienteId(paciente.getId());
        for (Triagem t : triagens) {
            data = t.getCriado_em().toLocalDate();
        }
        return data;
    }

    private int pegarSintomas(Paciente paciente) {
        int total = 0;
        var triagens = triagemReposiotry.findAllByPacienteId(paciente.getId());
        for (Triagem t : triagens) {
            var respostas = t.getRespostas();
            for (Resposta r : respostas) {
                if (r.isResposta()) {
                    total++;
                }
            }
        }
        return total;
    }

    private Risco verificarRisco(int sintomas) {
        return sintomas > 6 ? Risco.ALTO : Risco.BAIXO;
    }

    public List<String> informacoesMedico() {
        List<String> infoMedico = new ArrayList<>();
        var medico = medicoRepository.findById(pegarMedico().getId());
        infoMedico.add(medico.get().getNome());
        infoMedico.add(pegarUser().getUsername());
        infoMedico.add(medico.get().getCrm());
        infoMedico.add(medico.get().getHospital());
        infoMedico.add(medico.get().getCidade());
        infoMedico.add(medico.get().getEstado());
        return infoMedico;
    }

    @Transactional
    public void editarInformacoesMedico(InformacoesPerfil informacoesPerfil, MultipartFile foto) throws IOException {
        var medico = pegarMedico();
        var user = pegarUser();
        if (informacoesPerfil.nome() != null && !informacoesPerfil.nome().isBlank()) {
            medico.setNome(informacoesPerfil.nome());
        }
        if (informacoesPerfil.email() != null && !informacoesPerfil.email().isBlank()) {
            if (!informacoesPerfil.email().equals(user.getEmail())
                    && userRepository.existsByEmail(informacoesPerfil.email())) {
                throw new RuntimeException("Esse email já existe.");
            }
            user.setEmail(informacoesPerfil.email());
        }
        if (informacoesPerfil.senha() != null && !informacoesPerfil.senha().isBlank()) {
            var senhaCriptografada = new BCryptPasswordEncoder().encode(informacoesPerfil.senha());
            user.setSenha(senhaCriptografada);
        }
        if (informacoesPerfil.crm() != null && !informacoesPerfil.crm().isBlank()) {
            if (!informacoesPerfil.crm().equals(medico.getCrm())
                    && medicoRepository.existsByCrm(informacoesPerfil.crm())) {
                throw new RuntimeException("CRM já cadastrado.");
            }
            medico.setCrm(informacoesPerfil.crm());
        }
        if (informacoesPerfil.especialidade() != null) {
            medico.setEspecialidade(informacoesPerfil.especialidade());
        }
        if (informacoesPerfil.hospital() != null && !informacoesPerfil.hospital().isBlank()) {
            medico.setHospital(informacoesPerfil.hospital());
        }
        if (informacoesPerfil.cidade() != null && !informacoesPerfil.cidade().isBlank()) {
            medico.setCidade(informacoesPerfil.cidade());
        }
        if (informacoesPerfil.estado() != null && !informacoesPerfil.estado().isBlank()) {
            medico.setEstado(informacoesPerfil.estado());
        }
        if (!foto.isEmpty()) {
            String nomeArquivo = UUID.randomUUID() + "_" + foto.getOriginalFilename();
            Path caminho = Paths.get("uploads/" + nomeArquivo);
            Files.createDirectories(caminho.getParent());
            Files.write(caminho, foto.getBytes());
            medico.setFotoPerfil(nomeArquivo);
        }
    }

    public List<Integer> informacoesNumericasHome() {
        int contadorTriagens = 0;
        int contadorPacientes = 0;
        List<Integer> informacoes = new ArrayList<>();
        var medico = pegarMedico().getId();
        var triagens = triagemReposiotry.findAllByMedicoId(medico);
        contadorTriagens = triagens.size();
        var pacientes = pacienteRepository.findAllByMedicoId(medico);
        contadorPacientes = pacientes.size();
        informacoes.add(contadorTriagens);
        informacoes.add(contadorPacientes);
        informacoes.add(pegarAltoRisco());
        return informacoes;
    }

    private int pegarAltoRisco() {
        int casosGraves = 0;
        var medico = pegarMedico().getId();
        var triagens = triagemReposiotry.findAllByMedicoId(medico);
        var respostas = respostaRepository.findAllByTriagemIn(triagens);
        for (Triagem t : triagens) {
            int marcadasSim = 0;
            for (Resposta r : respostas) {
                if (r.getTriagem().equals(t) && r.isResposta()) {
                    marcadasSim++;
                }
            }
            if (marcadasSim >= 6) {
                casosGraves++;
            }
        }
        return casosGraves;
    }

    public List<String> informacoesPaciente(Integer id) {
        List<String> infoPaciente = new ArrayList<>();
        var paciente = pacienteRepository.findById(id);
        infoPaciente.add(paciente.get().getNome());
        infoPaciente.add(String.valueOf(id));
        infoPaciente.add(paciente.get().getCpf());
        infoPaciente.add(String.valueOf(paciente.get().getNascimento()));
        infoPaciente.add(String.valueOf(paciente.get().getSexo()));
        infoPaciente.add(paciente.get().getTelefone());
        infoPaciente.add(String.valueOf(paciente.get().getHistorico1()));
        infoPaciente.add(String.valueOf(paciente.get().getHistorico2()));
        return infoPaciente;
    }

    @Transactional
    public void editarInformacoesPaciente(Integer id, InformacoesPaciente informacoesPaciente) {
        var paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        if (informacoesPaciente.nome() != null && !informacoesPaciente.nome().isBlank()) {
            paciente.setNome(informacoesPaciente.nome());
        }
        if (informacoesPaciente.cpf() != null && !informacoesPaciente.cpf().isBlank()) {
            if (pacienteRepository.existsByCpf(informacoesPaciente.cpf())
                    && !informacoesPaciente.cpf().equals(paciente.getCpf())) {
                throw new RuntimeException("CPF já cadastrado");
            }
            paciente.setCpf(informacoesPaciente.cpf());
        }
        if (informacoesPaciente.nascimento() != null) {
            paciente.setNascimento(informacoesPaciente.nascimento());
        }
        if (informacoesPaciente.sexo() != null) {
            paciente.setSexo(informacoesPaciente.sexo());
        }
        if (informacoesPaciente.historico1() != null) {
            paciente.setHistorico1(informacoesPaciente.historico1());
        }
        if (informacoesPaciente.historico2() != null) {
            paciente.setHistorico2(informacoesPaciente.historico2());
        }
    }

    public String pegarFotoMedico() {
        var medico = pegarMedico();
        return medico.getFotoPerfil();
    }

    private Medico pegarMedico() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return medicoRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
    }

    private User pegarUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));
    }
}