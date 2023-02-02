package io.github.rafaelsilva91.domain.repositories;

import io.github.rafaelsilva91.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNomeLike(String nome);

    List<Cliente> findByNomeLikeOrIdOrderById(String nome, Integer id);

    //Exemplos
    Cliente findOneByNome(String nome);

    boolean existsByNome(String nome);
}
