package io.github.rafaelsilva91.rest.controller;

import io.github.rafaelsilva91.domain.entities.Cliente;
import io.github.rafaelsilva91.domain.repositories.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/clientes")
@Api("Api Clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 400, message = "Cliente não encontrado para o ID informado")
    })
    public Cliente findById(@PathVariable("id") Integer id){
        return clienteRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não Encontrado ID: ["+id+"]"));
    }

    @GetMapping
    public List<Cliente> findAll(){
        List<Cliente> list = clienteRepository.findAll();
        if(!list.isEmpty()){
            return list;
        }
        return list;
    }


    @GetMapping("/filtro")
    public List<Cliente> findFilter( Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return clienteRepository.findAll(example);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Cliente insert(@RequestBody @Valid Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){
       clienteRepository.findById(id)
              .map( cliente -> {
                  clienteRepository.delete(cliente);
                  return cliente;
              })
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não Encontrado ID: ["+id+"]"));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity update(@PathVariable("id")  Integer id,
                                 @RequestBody @Valid Cliente cliente){

        return clienteRepository
                .findById(id)
                .map(c ->{
                    cliente.setId(c.getId());
                    clienteRepository.save(cliente);
                    return ResponseEntity.ok().body(cliente);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não Encontrado ID: ["+id+"]"));

    }


}
