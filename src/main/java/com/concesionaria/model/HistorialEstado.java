package com.concesionaria.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_estado")
public class HistorialEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaCambio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonBackReference("pedido-historial")
    private Pedido pedido;

    public HistorialEstado() {
    }

    public HistorialEstado(LocalDateTime fechaCambio, EstadoPedido estado, Pedido pedido) {
        this.fechaCambio = fechaCambio;
        this.estado = estado;
        this.pedido = pedido;
    }

    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public LocalDateTime getFechaCambio() { 
        return fechaCambio; 
    }
    
    public void setFechaCambio(LocalDateTime fechaCambio) { 
        this.fechaCambio = fechaCambio; 
    }

    public EstadoPedido getEstado() { 
        return estado; 
    }
    
    public void setEstado(EstadoPedido estado) { 
        this.estado = estado; 
    }

    public Pedido getPedido() { 
        return pedido; 
    }
    
    public void setPedido(Pedido pedido) { 
        this.pedido = pedido; 
    }
}