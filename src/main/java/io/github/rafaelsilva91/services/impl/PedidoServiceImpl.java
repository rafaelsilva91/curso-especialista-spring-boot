package io.github.rafaelsilva91.services.impl;

import io.github.rafaelsilva91.domain.entities.Cliente;
import io.github.rafaelsilva91.domain.entities.ItemPedido;
import io.github.rafaelsilva91.domain.entities.Pedido;
import io.github.rafaelsilva91.domain.entities.Produto;
import io.github.rafaelsilva91.domain.enums.StatusPedido;
import io.github.rafaelsilva91.domain.repositories.ClienteRepository;
import io.github.rafaelsilva91.domain.repositories.ItemPedidoRepository;
import io.github.rafaelsilva91.domain.repositories.PedidoRepository;
import io.github.rafaelsilva91.domain.repositories.ProdutoRepository;
import io.github.rafaelsilva91.exceptions.PedidoNaoEncontradoExceptions;
import io.github.rafaelsilva91.exceptions.RegraNegocioExceptions;
import io.github.rafaelsilva91.rest.dto.ItemPedidoDto;
import io.github.rafaelsilva91.rest.dto.PedidoDto;
import io.github.rafaelsilva91.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido insert(PedidoDto dto) {
        Integer clienteId = dto.getCliente();

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(()-> new RegraNegocioExceptions("Código de Cliente Inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedidos =  converterItems(pedido, dto.getItems());

        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemsPedidos);
        pedido.setItens(itemsPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository.findById(id)
                .map(pedido ->{
                    pedido.setStatus(statusPedido);
                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoExceptions());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDto> items){
        if(items.isEmpty()){
            throw new RegraNegocioExceptions("Não é possível realizar um pedido sem itens.");
        }

        return items.stream()
                .map(dto ->{

                    Integer produtoId = dto.getProduto();
                    Produto produto = produtoRepository.findById(produtoId)
                            .orElseThrow(
                                    ()-> new RegraNegocioExceptions("Código de Produto  Inválido: "
                                    +produtoId
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;

                }).collect(Collectors.toList());
    }

}
