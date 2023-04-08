package com.Procesos_Vehiculos.API.service;

import com.Procesos_Vehiculos.API.models.Car;
import com.Procesos_Vehiculos.API.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CarServicesImp implements CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Car getCar(Long id){
        String url = "https://myfakeapi.com/api/cars/"+id;
        Car car = restTemplate.getForObject(url, Car.class);
        return car;
//        return carRepository.findById(id).get();
    }
        @Override
        public Car createCar(Long id ){
            String url = "https://myfakeapi.com/api/cars/"+id;
            Car car = restTemplate.getForObject(url, Car.class);
                return carRepository.save(car);
        }
        @Override
    public List<Car>allCars(){return carRepository.findAll();}

    @Override
    public Boolean updateCar(Long id, Car car){
        try{
            Car carBD = carRepository.findById(id).get();

            carBD.setCar(car.getCar());
            carBD.setCar_color(car.getCar_color());
            carBD.setPrice(car.getPrice());
            carBD.setCar_model_year(car.getCar_model_year());
            carBD.setCar_model(car.getCar_model());
            Car carUp = carRepository.save(carBD);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

