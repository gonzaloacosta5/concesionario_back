package com.concesionaria.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pago_tarjeta")
@DiscriminatorValue("TARJETA")
@PrimaryKeyJoinColumn(name = "id")
public class PagoTarjeta extends Pago {

    @Column(name = "numero_tarjeta", nullable = false)
    private String numeroTarjeta;

    @Column(name = "titular", nullable = false)
    private String titular;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDate fechaExpiracion;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    public PagoTarjeta() {
        super();
    }

    public PagoTarjeta(Pedido pedido,
                       String numeroTarjeta,
                       String titular,
                       LocalDate fechaExpiracion,
                       String cvv) {
        super(pedido);
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
