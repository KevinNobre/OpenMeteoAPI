package com.example.OpenMeteoAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Configura as rotas da OpenMeteoAPI (Admin e User)
                        .requestMatchers("/cidades/cadastro").hasRole("ADMIN")  // Acesso ao cadastro de cidades apenas para Admin
                        .requestMatchers("/cidades/{id}/clima").hasAnyRole("ADMIN", "USER")  // Acesso ao clima apenas para Admin e User
                        .requestMatchers("/cidades").hasAnyRole("ADMIN", "USER")  // Acesso à lista de cidades para Admin e User
                        // Configura outras rotas
                        .requestMatchers("/admin").hasRole("ADMIN")  // Somente Admin pode acessar a rota /admin
                        .requestMatchers("/user").hasAnyRole("ADMIN", "USER")
                        .anyRequest().permitAll()  // As demais rotas são acessíveis a todos
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin").password("{noop}admin").roles("ADMIN").build(),
                User.withUsername("user").password("{noop}user").roles("USER").build()
        );
    }
}
