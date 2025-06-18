// File: src/main/java/com/concesionaria/service/impl/ClienteServiceImpl.java
package com.concesionaria.service.impl;

import com.concesionaria.exception.DuplicateResourceException;
import com.concesionaria.exception.ResourceNotFoundException;
import com.concesionaria.model.Cliente;
import com.concesionaria.repository.ClienteRepository;
import com.concesionaria.service.ClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository repo;

    public ClienteServiceImpl(ClienteRepository repo) {
        this.repo = repo;
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        if (repo.existsByDocumento(cliente.getDocumento()))
            throw new DuplicateResourceException("Documento ya registrado");
        if (repo.existsByEmail(cliente.getEmail()))
            throw new DuplicateResourceException("Email ya registrado");
        return repo.save(cliente);
    }

    @Override
    public List<Cliente> listarTodos() {
        return repo.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
    }
}
