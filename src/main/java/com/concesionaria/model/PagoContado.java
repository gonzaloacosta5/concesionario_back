package com.concesionaria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pago_contado")
@DiscriminatorValue("CONTADO")
@PrimaryKeyJoinColumn(name = "id")
public class PagoContado extends Pago {

    @Column(name = "descuento", nullable = false)
    private Double descuento;

    public PagoContado() {
        super();
    }

    public PagoContado(Pedido pedido, Double descuento) {
        super(pedido);
        this.descuento = descuento;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
}
