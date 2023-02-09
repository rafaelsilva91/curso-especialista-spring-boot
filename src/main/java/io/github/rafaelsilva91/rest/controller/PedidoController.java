package io.github.rafaelsilva91.rest.controller;

import io.github.rafaelsilva91.domain.entities.ItemPedido;
import io.github.rafaelsilva91.domain.entities.Pedido;
import io.github.rafaelsilva91.domain.entities.Produto;
import io.github.rafaelsilva91.domain.enums.StatusPedido;
import io.github.rafaelsilva91.domain.repositories.PedidoRepository;
import io.github.rafaelsilva91.domain.repositories.ProdutoRepository;
import io.github.rafaelsilva91.rest.dto.*;
import io.github.rafaelsilva91.services.PedidoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido insert(@RequestBody PedidoDto dto){
        Pedido pedido = service.insert(dto);
        return pedido;
    }

    @GetMapping("{id}")
    public InformacoesPedidoDto findById(@PathVariable Integer id){
        return service
                .obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(
            @PathVariable Integer id,
            @RequestBody AtualizacaoStatusPedidoDto dto){

        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDto converter(Pedido pedido){
        return InformacoesPedidoDto.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf_cnpj(pedido.getCliente().getCpf_cnpj())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDto> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream().map(
                item -> InformacaoItemPedidoDto.builder()
                .descricaoProduto(item.getProduto().getDescricao())
                .precoUnitario(item.getProduto().getPreco())
                .quantidade(item.getQuantidade())
                .build()
        ).collect(Collectors.toList());
    }


}
