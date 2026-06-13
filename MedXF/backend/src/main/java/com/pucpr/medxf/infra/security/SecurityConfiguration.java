package com.pucpr.medxf.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(http -> http
                        .requestMatchers("/css/**", "/assets/**").permitAll()
                        .requestMatchers("/inicio/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/medico/**").hasRole("USER")
                        .requestMatchers("/paciente/**").hasRole("PACIENTE")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/inicio")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) ->
                                response.sendRedirect(pegarRole(authentication))))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/inicio?logout")
                        .permitAll())
                .build();
    }

    private String pegarRole(Authentication authentication) {
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        if (role.equals("ROLE_ADMIN")) {
            return "/admin/home";
        } else if (role.equals("ROLE_USER")) {
            return "/medico/home";
        } else {
            return "/paciente/home";
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
