// File: src/main/java/com/concesionaria/repository/PedidoRepository.java
package com.concesionaria.repository;

import com.concesionaria.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    boolean existsByNumeroPedido(String numeroPedido);
    
}
