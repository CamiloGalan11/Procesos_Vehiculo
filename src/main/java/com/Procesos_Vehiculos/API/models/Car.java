package com.Procesos_Vehiculos.API.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String car;
    private String car_model;
    private String car_color;
    private Double price;
    private int car_model_year;
    private String car_vin;
    private String availability;
}
