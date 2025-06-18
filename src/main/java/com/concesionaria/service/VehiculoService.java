// File: src/main/java/com/concesionaria/service/VehiculoService.java
package com.concesionaria.service;

import com.concesionaria.model.Vehiculo;
import java.util.List;

public interface VehiculoService {
    Vehiculo crearVehiculo(Vehiculo vehiculo);
    List<Vehiculo> listarTodos();    
    List<Vehiculo> listarDisponibles();
    Vehiculo buscarPorId(Long id);
}
