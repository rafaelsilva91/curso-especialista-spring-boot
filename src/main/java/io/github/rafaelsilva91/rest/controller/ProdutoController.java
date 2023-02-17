package io.github.rafaelsilva91.rest.controller;

import io.github.rafaelsilva91.domain.entities.Produto;
import io.github.rafaelsilva91.domain.repositories.ProdutoRepository;
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
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/{id}")
    public Produto findById(@PathVariable("id") Integer id){
        return produtoRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não Encontrado ID: ["+id+"]"));
    }

    @GetMapping
    public List<Produto> findAll(){
        List<Produto> list = produtoRepository.findAll();
        if(!list.isEmpty()){
            return list;
        }
        return list;
    }

    @GetMapping("/filtro")
    public List<Produto> findFilter( Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtoRepository.findAll(example);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto insert(@RequestBody @Valid Produto produto){
        return produtoRepository.save(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){
        produtoRepository.findById(id)
                .map( produto -> {
                    produtoRepository.delete(produto);
                    return produto;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não Encontrado ID: ["+id+"]"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity update(@PathVariable("id") Integer id,
                                 @RequestBody @Valid Produto produto){
        return produtoRepository
                .findById(id)
                .map(p ->{
                    produto.setId(p.getId());
                    produtoRepository.save(produto);
                    return ResponseEntity.ok().body(produto);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não Encontrado ID: ["+id+"]"));

    }
}
