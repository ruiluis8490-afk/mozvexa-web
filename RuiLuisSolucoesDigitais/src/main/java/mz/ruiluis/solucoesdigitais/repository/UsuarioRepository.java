package mz.ruiluis.solucoesdigitais.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mz.ruiluis.solucoesdigitais.model.Usuario;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
}