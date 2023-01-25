package io.github.rafaelsilva91;

import io.github.rafaelsilva91.domain.entities.Cliente;
import io.github.rafaelsilva91.domain.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClienteRepository clienteRepository) {
        return args -> {
            Cliente cliente = new Cliente();
            cliente.setNome("Rafael Rodrigues");
            clienteRepository.salvar(cliente);

            Cliente cliente2 = clienteRepository.salvar(new Cliente("Rafael Silva"));

            List<Cliente> todosClientes = clienteRepository.obterTodos();
            todosClientes.forEach(System.out::println);
        };
    }

    @Value("${application.name}")
    private String s;

    @GetMapping("/hello")
    public String helloWorld(){
        return this.s;
    }

    public static void main(String[] args) {

        SpringApplication.run(VendasApplication.class, args);

    }
}