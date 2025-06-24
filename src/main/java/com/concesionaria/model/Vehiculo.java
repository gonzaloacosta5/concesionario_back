package com.concesionaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "marca", nullable = false)
    private String marca;

    @NotBlank
    @Column(name = "modelo", nullable = false)
    private String modelo;

    @NotBlank
    @Column(name = "color", nullable = false)
    private String color;

    @NotBlank
    @Column(name = "chasis", nullable = false, unique = true)
    private String chasis;

    @NotBlank
    @Column(name = "motor", nullable = false, unique = true)
    private String motor;

    @DecimalMin("0.0")
    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoVehiculo tipo;

    public Vehiculo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getChasis() { return chasis; }
    public void setChasis(String chasis) { this.chasis = chasis; }

    public String getMotor() { return motor; }
    public void setMotor(String motor) { this.motor = motor; }

    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }

    public TipoVehiculo getTipo() { return tipo; }
    public void setTipo(TipoVehiculo tipo) { this.tipo = tipo; }
}
