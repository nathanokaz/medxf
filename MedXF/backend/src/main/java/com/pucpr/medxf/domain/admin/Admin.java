package com.pucpr.medxf.domain.admin;

import com.pucpr.medxf.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admins")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
