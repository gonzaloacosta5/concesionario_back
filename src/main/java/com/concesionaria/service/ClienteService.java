// File: src/main/java/com/concesionaria/service/ClienteService.java
package com.concesionaria.service;

import com.concesionaria.model.Cliente;
import java.util.List;

public interface ClienteService {
    Cliente crearCliente(Cliente cliente);
    List<Cliente> listarTodos();
    Cliente buscarPorId(Long id);
}
