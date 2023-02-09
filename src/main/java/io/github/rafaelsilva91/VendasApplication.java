package io.github.rafaelsilva91;

import io.github.rafaelsilva91.domain.entities.Cliente;
import io.github.rafaelsilva91.domain.entities.Pedido;
import io.github.rafaelsilva91.domain.entities.Produto;
import io.github.rafaelsilva91.domain.repositories.ClienteRepository;
import io.github.rafaelsilva91.domain.repositories.PedidoRepository;
import io.github.rafaelsilva91.domain.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner commandLineRunner(
            @Autowired ClienteRepository clienteRepository,
            @Autowired ProdutoRepository produtoRepository){
        return args -> {
//
//            Cliente c1 = new Cliente(null, "José Aldo", "00000000000");
//            Cliente c2 = new Cliente(null, "Maria Aldo", "00000000011");
//
//            clienteRepository.save(c1);
//            clienteRepository.save(c2);
//
//            Produto p1 = new Produto(null, "Mouse Gamer", new BigDecimal(79.99));
//            Produto p2 = new Produto(null, "Impressora", new BigDecimal(299.99));
//
//            produtoRepository.save(p1);
//            produtoRepository.save(p2);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);



    }
}