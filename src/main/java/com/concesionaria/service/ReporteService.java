package com.concesionaria.service;

import com.concesionaria.model.Pedido;
import com.concesionaria.model.FormaPago;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReporteService {
    List<Pedido> generarReportePedidos(LocalDate desde, String estado);
    Map<FormaPago, Double> calcularTotales(LocalDate desde, LocalDate hasta, boolean incluirImpuestos);
    byte[] exportPedidosCsv(LocalDate desde, LocalDate hasta, String estado);
}
