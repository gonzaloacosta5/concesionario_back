// File: src/main/java/com/concesionaria/service/strategy/CamionetaImpuestoStrategy.java
package com.concesionaria.service.strategy;

import com.concesionaria.model.Pedido;
import org.springframework.stereotype.Component;

@Component("CAMIONETA")
public class CamionetaImpuestoStrategy implements ImpuestoStrategy {
    @Override
    public double calcular(Pedido pedido) {
        double base = pedido.getVehiculo().getPrecioBase();
        double impNacional = base * 0.10;
        double impProvGral = base * 0.05;
        double impProvAdd = base * 0.02;
        return impNacional + impProvGral + impProvAdd;
    }
}
