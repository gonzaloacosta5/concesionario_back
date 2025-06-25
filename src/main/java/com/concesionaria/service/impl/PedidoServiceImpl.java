package com.concesionaria.service.impl;

import com.concesionaria.exception.DuplicateResourceException;
import com.concesionaria.exception.ResourceNotFoundException;
import com.concesionaria.model.HistorialEstado;
import com.concesionaria.model.Pedido;
import com.concesionaria.model.Cliente;
import com.concesionaria.model.Vehiculo;
import com.concesionaria.model.EstadoPedido;
import com.concesionaria.repository.PedidoRepository;
import com.concesionaria.repository.ClienteRepository;
import com.concesionaria.repository.VehiculoRepository;
import com.concesionaria.service.PedidoService;
import com.concesionaria.service.strategy.ImpuestoStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepo;
    private final ClienteRepository clienteRepo;
    private final VehiculoRepository vehRepo;
    private final Map<String, ImpuestoStrategy> estrategias = new HashMap<>();

    public PedidoServiceImpl(PedidoRepository pedidoRepo,
                             ClienteRepository clienteRepo,
                             VehiculoRepository vehRepo,
                             List<ImpuestoStrategy> listaEstrategias) {
        this.pedidoRepo  = pedidoRepo;
        this.clienteRepo = clienteRepo;
        this.vehRepo     = vehRepo;
        listaEstrategias.forEach(e -> {
            Component ann = e.getClass().getAnnotation(Component.class);
            if (ann != null && !ann.value().isEmpty()) {
                estrategias.put(ann.value(), e);
            }
        });
    }

@Override
public Pedido crearPedido(Pedido pedido) {
    if (pedidoRepo.existsByNumeroPedido(pedido.getNumeroPedido())) {
        throw new DuplicateResourceException("Pedido repetido");
    }

    Cliente  c = clienteRepo.findById(pedido.getCliente().getId())
                  .orElseThrow(() -> new ResourceNotFoundException("Cliente no existe"));
    Vehiculo v = vehRepo.findById(pedido.getVehiculo().getId())
                  .orElseThrow(() -> new ResourceNotFoundException("Vehículo no existe"));

    pedido.setCliente(c);
    pedido.setVehiculo(v);
    pedido.setFechaCreacion(LocalDateTime.now());

    String tipo = v.getTipo().name();              
    ImpuestoStrategy strat = estrategias.get(tipo);
    if (strat == null) {
        throw new IllegalStateException("No hay estrategia para tipo " + tipo);
    }
    double imp = strat.calcular(pedido);
    pedido.setTotal(v.getPrecioBase() + imp);

    Pedido guardado = pedidoRepo.save(pedido);

    return pedidoRepo.findById(guardado.getId())
                     .orElseThrow();
}


    @Override
    public Pedido avanzarEstado(Long pedidoId, String nuevoEstado) {
        Pedido p = pedidoRepo.findById(pedidoId)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido no existe"));

        HistorialEstado h = new HistorialEstado();
        h.setFechaCambio(LocalDateTime.now());
        h.setEstado(EstadoPedido.valueOf(nuevoEstado));
        h.setPedido(p);
        p.getHistorial().add(h);

        return pedidoRepo.save(p);
    }

    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepo.findAll();
    }

    @Override
    public Pedido buscarPorId(Long id) {
        return pedidoRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
    }

    @Override
    public List<Pedido> listarPorFechaYEstado(LocalDate desde, String estado) {
        return pedidoRepo.findAll().stream()
            .filter(p -> !p.getFechaCreacion().toLocalDate().isBefore(desde))
            .filter(p -> estado == null ||
                         p.getHistorial().stream()
                          .anyMatch(h -> h.getEstado().name().equals(estado)))
            .collect(Collectors.toList());
    }
}
