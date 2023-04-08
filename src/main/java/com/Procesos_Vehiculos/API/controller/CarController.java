package com.Procesos_Vehiculos.API.controller;

import com.Procesos_Vehiculos.API.models.Car;
import com.Procesos_Vehiculos.API.models.CarObject;
import com.Procesos_Vehiculos.API.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/car/{id}")
    public ResponseEntity findCarById(@PathVariable Long id){
        Map response = new HashMap();
        try{
            return new ResponseEntity(carService.getCar(id), HttpStatus.OK);
        }catch (Exception e){
            response.put("Status","404");
            response.put("Message","NO SE ENCONTRO EL VEHICULO");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(value = "/car/{id}")
    public ResponseEntity saveCar(@PathVariable Long id){
        Map response = new HashMap();
        Car carResp = carService.createCar(id);
        if (carResp != null){
            response.put("Status","201");
            response.put("Message","SE HA INGRESADO EL VEHICULO");
            return new ResponseEntity(response,HttpStatus.CREATED);
        }
        response.put("Status","400");
        response.put("Message","ERROR AL INGRESAR EL VEHICULO");
        return  new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "/cars")
    public ResponseEntity findCarsByAll(){
        Map response = new HashMap();
        try{
            return new ResponseEntity(carService.allCars(),HttpStatus.OK);
        }catch (Exception e){
            response.put("Status","404");
            response.put("Message","TODOS LOS VEHICULOS REGISTRADOS");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping(value = "/car/{id}")
    public ResponseEntity updateCar(@PathVariable Long id, @RequestBody Car car){
        Map<String, String> response = new HashMap<>();
        if (!carService.updateCar(id, car)){
            response.put("Status","404");
            response.put("Message","ERROR EN LOS DATOS");
            return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
        }
        response.put("Status","201");
        response.put("Message","VEHICULO ACTUALIZADO");
        return new ResponseEntity(response, HttpStatus.OK);
    }
    @GetMapping("/test/{id}")
    public Car getCar(@PathVariable Long id){
        String url = "https://myfakeapi.com/api/cars/"+id;
        CarObject[] carObject = restTemplate.getForObject(url, CarObject[].class);
        Car car = new Car();
        car.setId(carObject[0].getCar().getId());
        car.setCar_color(carObject[0].getCar().getCar_color());
        car.setCar_model(carObject[0].getCar().getCar_model());
        car.setCar_vin(carObject[0].getCar().getCar_vin());
        car.setCar(carObject[0].getCar().getCar());
        return car;
//        return carRepository.findById(id).get();
    }
}
