package com.concesionaria.controller;

import com.concesionaria.model.Vehiculo;
import com.concesionaria.service.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final VehiculoService svc;

    public VehiculoController(VehiculoService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo v) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(svc.crearVehiculo(v));
    }

    @GetMapping
    public List<Vehiculo> listarTodos() {
        return svc.listarTodos();
    }

    @GetMapping("/disponibles")
    public List<Vehiculo> listarDisponibles() {
        return svc.listarDisponibles();
    }

    @GetMapping("/{id}")
    public Vehiculo obtener(@PathVariable Long id) {
        return svc.buscarPorId(id);
    }
}
