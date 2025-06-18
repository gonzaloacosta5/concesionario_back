package com.concesionaria.service;

import com.concesionaria.model.Pedido;
import java.time.LocalDate;
import java.util.List;

public interface PedidoService {
    Pedido crearPedido(Pedido pedido);
    Pedido avanzarEstado(Long pedidoId, String nuevoEstado);
    List<Pedido> listarTodos();
    Pedido buscarPorId(Long id);
    List<Pedido> listarPorFechaYEstado(LocalDate desde, String estado);
}
