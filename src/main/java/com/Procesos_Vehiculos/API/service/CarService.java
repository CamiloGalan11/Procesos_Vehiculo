package com.Procesos_Vehiculos.API.service;

import com.Procesos_Vehiculos.API.models.Car;

import java.util.List;

public interface CarService {
    Car getCar(Long id);
    Car createCar(Long id, Long userId);
    List<Car>allCars();
    Boolean updateCar(Long id, Car car);
}
