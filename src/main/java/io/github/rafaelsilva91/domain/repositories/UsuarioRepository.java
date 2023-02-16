package io.github.rafaelsilva91.domain.repositories;

import io.github.rafaelsilva91.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query()
    Optional<Usuario> findByLogin(String login);
}
