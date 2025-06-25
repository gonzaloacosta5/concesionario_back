// File: src/main/java/com/concesionaria/repository/PedidoRepository.java
package com.concesionaria.repository;
import java.util.List;
import java.util.Optional;
import com.concesionaria.model.Pedido;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    boolean existsByNumeroPedido(String numeroPedido);

    @Override
    @EntityGraph(attributePaths = { "cliente", "vehiculo" })
    List<Pedido> findAll();

    @Override
    @EntityGraph(attributePaths = { "cliente", "vehiculo" })
    Optional<Pedido> findById(Long id);
}
