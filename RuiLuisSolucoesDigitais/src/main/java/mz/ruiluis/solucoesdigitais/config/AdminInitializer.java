package mz.ruiluis.solucoesdigitais.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import mz.ruiluis.solucoesdigitais.model.Usuario;
import mz.ruiluis.solucoesdigitais.repository.UsuarioRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String username;

    @Value("${app.admin.password}")
    private String password;

    public AdminInitializer(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (usuarioRepository.findByUsername(username).isEmpty()) {

            Usuario admin = new Usuario();

            admin.setUsername(username);

            admin.setPassword(
                    passwordEncoder.encode(password)
            );

            admin.setActivo(true);

            usuarioRepository.save(admin);

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "ADMINISTRADOR CRIADO COM SUCESSO"
            );

            System.out.println(
                    "Utilizador: " + username
            );

            System.out.println(
                    "========================================"
            );

        } else {

            System.out.println(
                    "Administrador '" +
                    username +
                    "' já existe."
            );
        }
    }
}