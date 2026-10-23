package mz.ruiluis.solucoesdigitais.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

            // =========================================================
            // CSRF
            // =========================================================
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
            )

            // =========================================================
            // HEADERS — PERMITIR IFRAME DO MESMO DOMÍNIO
            // (necessário para a pré-visualização no admin)
            // =========================================================
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )

            // =========================================================
            // AUTORIZAÇÃO
            // =========================================================
            .authorizeHttpRequests(auth -> auth

                // -----------------------------------------------------
                // WEBSITE PÚBLICO + RECURSOS ESTÁTICOS
                // -----------------------------------------------------
                .requestMatchers(
                    "/",
                    "/solicitar",
                    "/css/**",
                    "/js/**",
                    "/imagens/**",
                    "/images/**",
                    "/fonts/**",
                    "/favicon.ico",
                    "/login"
                ).permitAll()

                // -----------------------------------------------------
                // API PÚBLICA — CLIENTE ENVIA PEDIDO
                // -----------------------------------------------------
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/pedidos"
                ).permitAll()

                // -----------------------------------------------------
                // ÁREA ADMINISTRATIVA
                // -----------------------------------------------------
                .requestMatchers(
                    "/admin/**"
                ).hasRole("ADMIN")

                // -----------------------------------------------------
                // API DE ADMINISTRAÇÃO DE PEDIDOS
                // -----------------------------------------------------
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/pedidos"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/pedidos/*"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/pedidos/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/pedidos/**"
                ).hasRole("ADMIN")

                // -----------------------------------------------------
                // API DE ORÇAMENTOS
                // -----------------------------------------------------
                .requestMatchers(
                    "/api/orcamentos/**"
                ).hasRole("ADMIN")

                // -----------------------------------------------------
                // QUALQUER OUTRO ENDPOINT
                // -----------------------------------------------------
                .anyRequest().authenticated()
            )

            // =========================================================
            // LOGIN
            // =========================================================
            .formLogin(form -> form

                .loginPage("/login")

                .defaultSuccessUrl(
                    "/admin",
                    true
                )

                .permitAll()
            )

            // =========================================================
            // LOGOUT
            // =========================================================
            .logout(logout -> logout

                .logoutUrl("/logout")

                .logoutSuccessUrl(
                    "/login?logout"
                )

                .permitAll()
            );

        return http.build();
    }
}