package com.concesionaria.controller;

import com.concesionaria.model.FormaPago;
import com.concesionaria.model.Pedido;
import com.concesionaria.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;        // ← IMPORT NUEVO
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/pedidos")
    public ResponseEntity<List<Pedido>> reportePedidos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) String estado) {
        return ResponseEntity.ok(
                reporteService.generarReportePedidos(desde, estado)
        );
    }

    @GetMapping("/totales")
    public ResponseEntity<Map<FormaPago, Double>> totales(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(defaultValue = "false") boolean incluirImpuestos) {
        return ResponseEntity.ok(
                reporteService.calcularTotales(desde, hasta, incluirImpuestos)
        );
    }

    @GetMapping(value = "/pedidos/csv", produces = "text/csv")
    public ResponseEntity<byte[]> exportarPedidosCsv(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) String estado) {

        byte[] csv = reporteService.exportPedidosCsv(desde, hasta, estado);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"reporte_pedidos.csv\"")
                .body(csv);
    }
}
