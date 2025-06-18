package com.concesionaria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pago_transferencia")
@DiscriminatorValue("TRANSFERENCIA")
@PrimaryKeyJoinColumn(name = "id")
public class PagoTransferencia extends Pago {

    @Column(name = "banco", nullable = false)
    private String banco;

    @Column(name = "cbu", nullable = false)
    private String cbu;

    public PagoTransferencia() {
        super();
    }

    public PagoTransferencia(Pedido pedido, String banco, String cbu) {
        super(pedido);
        this.banco = banco;
        this.cbu = cbu;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }
}
