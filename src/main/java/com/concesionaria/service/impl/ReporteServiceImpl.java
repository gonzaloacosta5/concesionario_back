package com.concesionaria.service.impl;

import com.concesionaria.model.FormaPago;
import com.concesionaria.model.Pedido;
import com.concesionaria.repository.PedidoRepository;
import com.concesionaria.service.ReporteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
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

    /* ---------- LISTA JSON ---------- */
    @Override
    public List<Pedido> generarReportePedidos(LocalDate desde, String estado) {
        return pedidoRepo.findAll().stream()
                .filter(p -> !p.getFechaCreacion().toLocalDate().isBefore(desde))
                .filter(p -> estado == null ||
                             p.getHistorial().stream()
                               .anyMatch(h -> h.getEstado().name().equalsIgnoreCase(estado)))
                .collect(Collectors.toList());
    }

    @Override
    public byte[] exportPedidosCsv(LocalDate desde, LocalDate hasta, String estado) {

        List<Pedido> pedidos = pedidoRepo.findAll().stream()
                .filter(p -> {
                    LocalDate f = p.getFechaCreacion().toLocalDate();
                    return !f.isBefore(desde) && !f.isAfter(hasta) &&
                           (estado == null ||
                            p.getHistorial().stream()
                              .anyMatch(h -> h.getEstado().name().equalsIgnoreCase(estado)));
                })
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        sb.append("numeroPedido,fecha,estado,cliente,vehiculo,total,formaPago\n");

        for (Pedido p : pedidos) {
            String estadoActual = p.getHistorial()
                                   .get(p.getHistorial().size() - 1)
                                   .getEstado().name();

            sb.append(p.getId()).append(',')
              .append(p.getFechaCreacion()).append(',')
              .append(estadoActual).append(',')
              .append(p.getCliente().getNombre())
              .append(' ').append(p.getCliente().getApellido()).append(',')
              .append(p.getVehiculo().getMarca())
              .append(' ').append(p.getVehiculo().getModelo()).append(',')
              .append(p.getTotal()).append(',')
              .append(p.getFormaPago().name()).append('\n');
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Map<FormaPago, Double> calcularTotales(LocalDate desde, LocalDate hasta, boolean incImp) {

        List<Pedido> filtrados = pedidoRepo.findAll().stream()
                .filter(p -> {
                    LocalDate f = p.getFechaCreacion().toLocalDate();
                    return !f.isBefore(desde) && !f.isAfter(hasta);
                })
                .collect(Collectors.toList());

        return filtrados.stream()
                .collect(Collectors.groupingBy(
                        Pedido::getFormaPago,
                        Collectors.summingDouble(Pedido::getTotal)
                ));
    }
}
