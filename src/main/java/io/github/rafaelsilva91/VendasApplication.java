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
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}