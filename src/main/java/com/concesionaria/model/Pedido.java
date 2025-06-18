package com.concesionaria.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_pedido", unique = true, nullable = false)
    private String numeroPedido;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "configuracion_extra")
    private String configuracionExtra;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pago", nullable = false)
    private FormaPago formaPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialEstado> historial = new ArrayList<>();

    public Pedido() {}

    // Getters & setters…

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getConfiguracionExtra() { return configuracionExtra; }
    public void setConfiguracionExtra(String configuracionExtra) { this.configuracionExtra = configuracionExtra; }

    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public List<HistorialEstado> getHistorial() { return historial; }
    public void setHistorial(List<HistorialEstado> historial) { this.historial = historial; }

    public void addHistorial(HistorialEstado h) {
        historial.add(h);
        h.setPedido(this);
    }

    public void removeHistorial(HistorialEstado h) {
        historial.remove(h);
        h.setPedido(null);
    }
}
