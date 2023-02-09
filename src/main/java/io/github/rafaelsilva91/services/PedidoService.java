package io.github.rafaelsilva91.services;

import io.github.rafaelsilva91.domain.entities.Pedido;
import io.github.rafaelsilva91.domain.enums.StatusPedido;
import io.github.rafaelsilva91.rest.dto.PedidoDto;

import java.util.Optional;

public interface PedidoService {

    Pedido insert(PedidoDto dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
