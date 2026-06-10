package com.pucpr.medxf.domain.medico;

import com.pucpr.medxf.domain.admin.Admin;
import com.pucpr.medxf.domain.medico.dto.Especialidade;
import com.pucpr.medxf.domain.paciente.Paciente;
import com.pucpr.medxf.domain.triagem.Triagem;
import com.pucpr.medxf.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "medicos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Column(nullable = false)
    private String hospital;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "medico")
    private List<Paciente> pacientes;

    @OneToMany(mappedBy = "medico")
    private List<Triagem> triagens;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;


}
