package com.Procesos_Vehiculos.API.service;

import com.Procesos_Vehiculos.API.models.Car;
import com.Procesos_Vehiculos.API.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServicesImp implements CarService {
    @Autowired
    private CarRepository carRepository;

    public Car getCar(Long id){return carRepository.findById(id).get();}
        @Override
        public Boolean createCar(Car car){
        try{
            carRepository.save(car);
            return true;
        }catch (Exception e){
            return false;
        }
        }
        @Override
    public List<Car>allCars(){return carRepository.findAll();}

    @Override
    public Boolean updateCar(Long id, Car car){
        try{
            Car carBD = carRepository.findById(id).get();

            carBD.setCarBrand(car.getCarBrand());
            carBD.setColor(car.getColor());
            carBD.setState(car.getState());
            carBD.setPrice(car.getPrice());
            carBD.setYear(car.getYear());
            carBD.setModel(car.getModel());
            Car carUp = carRepository.save(carBD);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

