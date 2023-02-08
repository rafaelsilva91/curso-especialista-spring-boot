package io.github.rafaelsilva91.rest.controller;

import io.github.rafaelsilva91.domain.entities.Cliente;
import io.github.rafaelsilva91.domain.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

//    private ClienteRepository clienteRepository;
//
//    public ClienteController(ClienteRepository clienteRepository){
//        this.clienteRepository = clienteRepository;
//    }

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable("id") Integer id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public ResponseEntity<List<Cliente>> findAll(){
        List<Cliente> list = clienteRepository.findAll();
        if(!list.isEmpty()){
            return ResponseEntity.ok().body(list);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity insert(@RequestBody Cliente cliente){
        Cliente c = clienteRepository.save(cliente);
        return ResponseEntity.ok(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(cliente.isPresent()){
            clienteRepository.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id,
                                 @RequestBody Cliente cliente){

        return clienteRepository
                .findById(id)
                .map(c ->{
                    cliente.setId(c.getId());
                    clienteRepository.save(cliente);
//                    return ResponseEntity.noContent().build();
                    return ResponseEntity.ok().body(cliente);
                }).orElseGet(()-> ResponseEntity.notFound().build());

    }

    @GetMapping("/filtro")
    public ResponseEntity find( Cliente filtro){

        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        List<Cliente> list = clienteRepository.findAll(example);
        return ResponseEntity.ok().body(list);
    }



//    @PutMapping("/{id}")
//    public ResponseEntity update(@PathVariable("id") Integer id,
//                                 @RequestBody Cliente clienteclienteRequest){
//        Optional<Cliente> cliente = clienteRepository.findById(id);
//        if(cliente.isPresent()){
//            Cliente c = new Cliente();
//            c.setId(cliente.get().getId());
//            c.setNome(clienteclienteRequest.getNome());
//            clienteRepository.save(c);
//            return ResponseEntity.ok().body(c);
//        }
//        return ResponseEntity.notFound().build();
//
//    }


}
