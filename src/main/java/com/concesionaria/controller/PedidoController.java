package com.concesionaria.controller;

import com.concesionaria.model.Pedido;
import com.concesionaria.model.HistorialEstado;
import com.concesionaria.service.PedidoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService svc;

    public PedidoController(PedidoService svc) {
        this.svc = svc;
    }

    @PostMapping
    public Pedido crear(@RequestBody Pedido p) {
        return svc.crearPedido(p);
    }

    @GetMapping
    public List<Pedido> listar() {
        return svc.listarTodos();
    }

    @GetMapping("/{id}")
    public Pedido obtener(@PathVariable Long id) {
        return svc.buscarPorId(id);
    }

    @GetMapping("/{id}/historial")
    public List<HistorialEstado> historial(@PathVariable Long id) {
        return svc.buscarPorId(id).getHistorial();
    }

    @PutMapping("/{id}/estado")
    public Pedido avanzar(@PathVariable Long id,
                          @RequestParam String nuevoEstado) {
        return svc.avanzarEstado(id, nuevoEstado);
    }

    @GetMapping("/reportes")
    public List<Pedido> reporte(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
        @RequestParam(required = false) String estado) {
        return svc.listarPorFechaYEstado(desde, estado);
    }
}
