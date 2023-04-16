package com.Procesos_Vehiculos.API.models;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Data;

@Data
@Entity
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CAR")
>>>>>>> develop/yindry/parcial1
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
<<<<<<< HEAD
    @Column(name = "car_brand")
    private String carBrand;
    private String model;
    private String color;
    private Double price;
    private int year;
    private String state;
=======
    private String type;
    private String model;
    private String color;
    private String price;
    private int year;
    private String reference;
    private boolean availability;
>>>>>>> develop/yindry/parcial1
}
