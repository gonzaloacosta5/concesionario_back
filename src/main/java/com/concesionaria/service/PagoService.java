package com.concesionaria.service;

import com.concesionaria.model.Pago;
import com.concesionaria.model.PagoContado;
import com.concesionaria.model.PagoTarjeta;
import com.concesionaria.model.PagoTransferencia;
import java.util.List;

public interface PagoService {
    Pago registrarPagoContado(Long pedidoId, PagoContado dto);
    Pago registrarPagoTransferencia(Long pedidoId, PagoTransferencia dto);
    Pago registrarPagoTarjeta(Long pedidoId, PagoTarjeta dto);
    List<Pago> listarPagosPorPedido(Long pedidoId);
}
