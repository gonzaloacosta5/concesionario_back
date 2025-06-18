// File: src/main/java/com/concesionaria/service/strategy/AutoImpuestoStrategy.java
package com.concesionaria.service.strategy;

import com.concesionaria.model.Pedido;
import org.springframework.stereotype.Component;

@Component("AUTO")
public class AutoImpuestoStrategy implements ImpuestoStrategy {
    @Override
    public double calcular(Pedido pedido) {
        double base = pedido.getVehiculo().getPrecioBase();
        double impNacional = base * 0.21;
        double impProvGral = base * 0.05;
        double impProvAdd = base * 0.01;
        return impNacional + impProvGral + impProvAdd;
    }
}
