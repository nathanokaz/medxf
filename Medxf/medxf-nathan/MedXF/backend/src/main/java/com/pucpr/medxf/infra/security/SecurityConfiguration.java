package com.pucpr.medxf.infra.security;

import com.pucpr.medxf.domain.user.User;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity.authorizeHttpRequests(http -> http
                        .requestMatchers("/css/**", "/assets/**").permitAll()
                        .requestMatchers("/inicio/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/inicio/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) ->
                                response.sendRedirect(pegarRole(authentication))))
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/inicio?logout").permitAll())
                .rememberMe(remember -> remember.key("867692"))
                .build();
    }

    private String pegarRole(Authentication authentication) {
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        if (role.equals("ROLE_ADMIN")) {
            return "/admin/home";
        } else {
            return "/medico/home";
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
