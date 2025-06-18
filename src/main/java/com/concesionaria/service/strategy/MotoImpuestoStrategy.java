// File: src/main/java/com/concesionaria/service/strategy/MotoImpuestoStrategy.java
package com.concesionaria.service.strategy;

import com.concesionaria.model.Pedido;
import org.springframework.stereotype.Component;

@Component("MOTO")
public class MotoImpuestoStrategy implements ImpuestoStrategy {
    @Override
    public double calcular(Pedido pedido) {
        double base = pedido.getVehiculo().getPrecioBase();
        double impProvGral = base * 0.05;
        return impProvGral;
    }
}
