package com.concesionaria.service.impl;

import com.concesionaria.model.FormaPago;
import com.concesionaria.model.Pedido;
import com.concesionaria.repository.PedidoRepository;
import com.concesionaria.service.ReporteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReporteServiceImpl implements ReporteService {
    private final PedidoRepository pedidoRepo;

    public ReporteServiceImpl(PedidoRepository pedidoRepo) {
        this.pedidoRepo = pedidoRepo;
    }

    @Override
    public List<Pedido> generarReportePedidos(LocalDate desde, String estado) {
        return pedidoRepo.findAll().stream()
            .filter(p -> !p.getFechaCreacion().toLocalDate().isBefore(desde))
            .filter(p -> estado == null ||
                         p.getHistorial().stream()
                           .anyMatch(h -> h.getEstado().name().equals(estado)))
            .collect(Collectors.toList());
    }

    @Override
    public Map<FormaPago, Double> calcularTotales(LocalDate desde, LocalDate hasta, boolean incImp) {
        List<Pedido> filtrados = pedidoRepo.findAll().stream()
            .filter(p -> {
                var f = p.getFechaCreacion().toLocalDate();
                return !f.isBefore(desde) && !f.isAfter(hasta);
            })
            .collect(Collectors.toList());

        // Si no incluye impuestos, sumamos cero donde corresponda
        double totalSin = incImp
            ? filtrados.stream().mapToDouble(Pedido::getTotal).sum()
            : 0.0;

        // Total global
        double totalCon = filtrados.stream().mapToDouble(Pedido::getTotal).sum();

        // Agrupado por forma de pago
        Map<FormaPago, Double> porForma = filtrados.stream()
            .collect(Collectors.groupingBy(
                Pedido::getFormaPago,
                Collectors.summingDouble(Pedido::getTotal)
            ));

        // Inserto la clave TOTAL_SIN_IMPUESTOS y TOTAL_CON_IMPUESTOS si quieres,
        // o devuelve sólo `porForma`.  
        porForma.put(FormaPago.CONTADO, totalSin);   // opcional  
        porForma.put(FormaPago.TRANSFERENCIA, totalCon); // opcional  

        return porForma;
    }
}
