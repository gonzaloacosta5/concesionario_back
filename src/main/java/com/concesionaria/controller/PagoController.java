package com.concesionaria.controller;

import com.concesionaria.model.Pago;
import com.concesionaria.model.PagoContado;
import com.concesionaria.model.PagoTransferencia;
import com.concesionaria.model.PagoTarjeta;
import com.concesionaria.service.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos/{pedidoId}/pagos")
public class PagoController {
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("/contado")
    public ResponseEntity<Pago> registrarPagoContado(
            @PathVariable Long pedidoId,
            @RequestBody PagoContado dto) {
        Pago pago = pagoService.registrarPagoContado(pedidoId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pago);
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Pago> registrarPagoTransferencia(
            @PathVariable Long pedidoId,
            @RequestBody PagoTransferencia dto) {
        Pago pago = pagoService.registrarPagoTransferencia(pedidoId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pago);
    }

    @PostMapping("/tarjeta")
    public ResponseEntity<Pago> registrarPagoTarjeta(
            @PathVariable Long pedidoId,
            @RequestBody PagoTarjeta dto) {
        Pago pago = pagoService.registrarPagoTarjeta(pedidoId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pago);
    }

    @GetMapping
    public ResponseEntity<List<Pago>> listarPagos(@PathVariable Long pedidoId) {
        List<Pago> pagos = pagoService.listarPagosPorPedido(pedidoId);
        return ResponseEntity.ok(pagos);
    }
}
