package com.dimas.invetoryManager.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        // Definimos dos usuarios con roles específicos
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123")) // Contraseña codificada
                .roles("ADMIN")
                .build();

        UserDetails employee = User.withUsername("employee")
                .password(passwordEncoder().encode("employee123"))
                .roles("EMPLOYEE")
                .build();

        return new InMemoryUserDetailsManager(admin, employee);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Swagger disponible para todos los roles autenticados
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()

                        // Endpoints GET son accesibles por ADMIN y EMPLOYEE
                        .requestMatchers(HttpMethod.GET, "/product/**", "/category/**").hasAnyRole("ADMIN", "EMPLOYEE")

                        // Otros métodos (POST, PUT, DELETE) solo accesibles por ADMIN
                        .requestMatchers(HttpMethod.POST, "/product/**", "/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/product/**", "/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/product/**", "/category/**").hasRole("ADMIN")

                        // Cualquier otro endpoint requiere autenticación
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF (solo para pruebas, no en producción)
                .httpBasic(Customizer.withDefaults()) // Autenticación básica
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }


}
