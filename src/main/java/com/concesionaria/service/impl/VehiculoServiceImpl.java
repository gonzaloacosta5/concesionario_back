package com.concesionaria.service.impl;

import com.concesionaria.exception.DuplicateResourceException;
import com.concesionaria.exception.ResourceNotFoundException;
import com.concesionaria.model.Vehiculo;
import com.concesionaria.repository.VehiculoRepository;
import com.concesionaria.service.VehiculoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehiculoServiceImpl implements VehiculoService {
    private final VehiculoRepository repo;

    public VehiculoServiceImpl(VehiculoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        if (repo.existsByChasis(vehiculo.getChasis()))
            throw new DuplicateResourceException("Chasis ya registrado");

        if (repo.existsByMotor(vehiculo.getMotor()))
            throw new DuplicateResourceException("Motor ya registrado");
        return repo.save(vehiculo);
    }

    @Override
    public List<Vehiculo> listarTodos() {
        return repo.findAll();
    }

    @Override
    public List<Vehiculo> listarDisponibles() {
        // Aquí podrías filtrar por los que no estén en pedido
        return repo.findAll();
    }

    @Override
    public Vehiculo buscarPorId(Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));
    }
}
