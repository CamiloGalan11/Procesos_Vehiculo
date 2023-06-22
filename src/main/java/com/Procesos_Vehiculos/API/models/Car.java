package com.Procesos_Vehiculos.API.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CAR")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String model;
    private String color;
    private String price;
    private int year;
    private String reference;
    private boolean availability;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
}
