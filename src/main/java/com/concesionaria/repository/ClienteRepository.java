// File: src/main/java/com/concesionaria/repository/ClienteRepository.java
package com.concesionaria.repository;
import java.util.List;
import java.util.Optional;
import com.concesionaria.model.Cliente;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDocumento(String documento);
    boolean existsByEmail(String email);
    @Override
    @EntityGraph(attributePaths = "pedidos")
    List<Cliente> findAll();

    @Override
    @EntityGraph(attributePaths = "pedidos")
    Optional<Cliente> findById(Long id);
}
