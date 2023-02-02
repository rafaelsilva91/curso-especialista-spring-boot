package io.github.rafaelsilva91;

import io.github.rafaelsilva91.domain.entities.Cliente;
import io.github.rafaelsilva91.domain.repositories.ClienteRepositoryUsingOperationsJPAEntityManger;
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
    public CommandLineRunner init(@Autowired ClienteRepositoryUsingOperationsJPAEntityManger clienteRepository) {
        return args -> {

            Cliente cliente = new Cliente();
            cliente.setNome("Rafael Rodrigues");
            clienteRepository.salvar(cliente);

            Cliente cliente2 = clienteRepository.salvar(new Cliente("Rafael Silva"));

            Cliente cliente3 = clienteRepository.salvar(new Cliente("José"));

            Cliente cliente4 = clienteRepository.salvar(new Cliente("Maria"));

            System.out.println("Listando Clientes");
            List<Cliente> todosClientes = clienteRepository.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizando Clientes");
            todosClientes.forEach(c->{
                c.setNome(c.getNome() + " atualizado.");
                clienteRepository.atualizar(c);
            });
            todosClientes = clienteRepository.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Buscando Cliente por nome");
            clienteRepository.buscarPorNome("Jo").forEach(System.out::println);

            System.out.println("Deletando Cliente");
            clienteRepository.obterTodos().forEach(c->{
                clienteRepository.deletar(c);
            });


            todosClientes = clienteRepository.obterTodos();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado!");
            }else{
                todosClientes.forEach(System.out::println);
            }

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