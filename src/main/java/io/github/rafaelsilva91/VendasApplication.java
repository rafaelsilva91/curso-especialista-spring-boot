package io.github.rafaelsilva91;

import io.github.rafaelsilva91.domain.entities.Cliente;
import io.github.rafaelsilva91.domain.entities.Pedido;
import io.github.rafaelsilva91.domain.repositories.ClienteRepository;
import io.github.rafaelsilva91.domain.repositories.PedidoRepository;
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
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init(
            @Autowired ClienteRepository clienteRepository,
            @Autowired PedidoRepository pedidoRepository
    ) {
        return args -> {

            Cliente cliente = new Cliente();
            cliente.setNome("Rafael Rodrigues");
            clienteRepository.save(cliente);

            Cliente cliente2 = clienteRepository.save(new Cliente("Rafael Silva"));

            Cliente cliente3 = clienteRepository.save(new Cliente("José"));

            Cliente cliente4 = clienteRepository.save(new Cliente("Maria"));

            Pedido p = new Pedido();
            p.setCliente(cliente4);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));

            Pedido p2 = new Pedido();
            p2.setCliente(cliente4);
            p2.setDataPedido(LocalDate.now());
            p2.setTotal(BigDecimal.valueOf(100));

            pedidoRepository.save(p);
            pedidoRepository.save(p2);

//            System.out.println("PEDIDO: "+p.getId());

            System.out.println();

            Cliente cli = clienteRepository.findClienteFetchPedidos(cliente4.getId());
            System.out.println(cli);
            System.out.println(cli.getPedidos());
            System.out.println();

            List<Pedido> list =  pedidoRepository.findByCliente(cliente4);
            System.out.println(cli);
            System.out.println(list);
            System.out.println();

            // or
            pedidoRepository.findByCliente(cliente4).forEach(System.out::println);
            System.out.println();




            System.out.println("Consulta por nome ");
            boolean existe = clienteRepository.existsByNome("José");
            System.out.println("Existe cliente com nome José? "+existe);

            System.out.println("Listando Clientes");
            List<Cliente> todosClientes = clienteRepository.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizando Clientes");
            todosClientes.forEach(c->{
                c.setNome(c.getNome() + " atualizado.");
                clienteRepository.save(c);
            });
            todosClientes = clienteRepository.findAll();
            todosClientes.forEach(System.out::println);
            System.out.println();
            System.out.println("Buscando Cliente por nome - metodo encontrarPorNome");
            clienteRepository.encontrarPorNome("Jo").forEach(System.out::println);
            System.out.println("Buscando Cliente por nome - metodo findByNomeLike");
            clienteRepository.findByNomeLike("Jo").forEach(System.out::println);
            System.out.println();
//            System.out.println("Deletando Cliente");
//            clienteRepository.findAll().forEach(c->{
//                clienteRepository.delete(c);
//            });
//
//
//            todosClientes = clienteRepository.findAll();
//            if(todosClientes.isEmpty()){
//                System.out.println("Nenhum cliente encontrado!");
//            }else{
//                todosClientes.forEach(System.out::println);
//            }

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