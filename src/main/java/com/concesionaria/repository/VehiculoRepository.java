// File: src/main/java/com/concesionaria/repository/VehiculoRepository.java
package com.concesionaria.repository;

import com.concesionaria.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    boolean existsByChasis(String chasis);
    boolean existsByMotor(String motor);
}
