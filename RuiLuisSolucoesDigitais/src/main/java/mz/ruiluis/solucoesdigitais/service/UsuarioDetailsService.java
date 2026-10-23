package mz.ruiluis.solucoesdigitais.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mz.ruiluis.solucoesdigitais.model.Usuario;
import mz.ruiluis.solucoesdigitais.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService
        implements UserDetailsService {

    private final UsuarioRepository repository;

    public UsuarioDetailsService(
            UsuarioRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username
    ) throws UsernameNotFoundException {

        Usuario usuario =
                repository.findByUsername(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Utilizador não encontrado."
                                )
                        );

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles("ADMIN")
                .disabled(!usuario.isActivo())
                .build();
    }
}