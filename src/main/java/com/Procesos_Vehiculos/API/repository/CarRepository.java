package com.Procesos_Vehiculos.API.repository;

import com.Procesos_Vehiculos.API.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
