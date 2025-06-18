// File: src/main/java/com/concesionaria/service/strategy/ImpuestoStrategy.java
package com.concesionaria.service.strategy;

import com.concesionaria.model.Pedido;

public interface ImpuestoStrategy {
    double calcular(Pedido pedido);
}
