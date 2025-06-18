// File: src/main/java/com/concesionaria/repository/ClienteRepository.java
package com.concesionaria.repository;

import com.concesionaria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDocumento(String documento);
    boolean existsByEmail(String email);
}
