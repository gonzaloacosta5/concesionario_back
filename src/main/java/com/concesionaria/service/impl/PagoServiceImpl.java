package com.concesionaria.service.impl;

import com.concesionaria.exception.ResourceNotFoundException;
import com.concesionaria.model.Pago;
import com.concesionaria.model.PagoContado;
import com.concesionaria.model.PagoTransferencia;
import com.concesionaria.model.PagoTarjeta;
import com.concesionaria.model.Pedido;
import com.concesionaria.repository.PagoContadoRepository;
import com.concesionaria.repository.PagoRepository;
import com.concesionaria.repository.PagoTarjetaRepository;
import com.concesionaria.repository.PagoTransferenciaRepository;
import com.concesionaria.repository.PedidoRepository;
import com.concesionaria.service.PagoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PagoServiceImpl implements PagoService {

    private final PedidoRepository pedidoRepo;
    private final PagoRepository pagoRepo;
    private final PagoContadoRepository contadoRepo;
    private final PagoTransferenciaRepository transfRepo;
    private final PagoTarjetaRepository tarjRepo;

    public PagoServiceImpl(PedidoRepository pedidoRepo,
                          PagoRepository pagoRepo,
                          PagoContadoRepository contadoRepo,
                          PagoTransferenciaRepository transfRepo,
                          PagoTarjetaRepository tarjRepo) {
        this.pedidoRepo  = pedidoRepo;
        this.pagoRepo    = pagoRepo;
        this.contadoRepo = contadoRepo;
        this.transfRepo  = transfRepo;
        this.tarjRepo    = tarjRepo;
    }

    @Override
    public Pago registrarPagoContado(Long pedidoId, PagoContado dto) {
        Pedido ped = pedidoRepo.findById(pedidoId)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido no existe"));
        dto.setPedido(ped);
        return contadoRepo.save(dto);
    }

    @Override
    public Pago registrarPagoTransferencia(Long pedidoId, PagoTransferencia dto) {
        Pedido ped = pedidoRepo.findById(pedidoId)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido no existe"));
        dto.setPedido(ped);
        return transfRepo.save(dto);
    }

    @Override
    public Pago registrarPagoTarjeta(Long pedidoId, PagoTarjeta dto) {
        Pedido ped = pedidoRepo.findById(pedidoId)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido no existe"));
        dto.setPedido(ped);
        return tarjRepo.save(dto);
    }

    @Override
    public List<Pago> listarPagosPorPedido(Long pedidoId) {
        return pagoRepo.findByPedidoId(pedidoId);
    }
}
