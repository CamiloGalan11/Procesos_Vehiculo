package com.Procesos_Vehiculos.API.controller;

import com.Procesos_Vehiculos.API.models.Car;
import com.Procesos_Vehiculos.API.service.CarServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CarController {
    @Autowired
    private CarServicesImp carServicesImp;

    @GetMapping(value = "/car/{id}")
    public ResponseEntity findCarById(@PathVariable Long id){
        Map response = new HashMap();
        try{
            return new ResponseEntity(carServicesImp.getCar(id), HttpStatus.OK);
        }catch (Exception e){
            response.put("Status","400");
            response.put("Message","NO SE ENCONTRO EL VEHICULO");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(value = "car")
    public ResponseEntity saveCar(@RequestBody Car car){
        Map response = new HashMap();
        Boolean carResp = carServicesImp.createCar(car);
        if (carResp == true){
            response.put("Status","300");
            response.put("Message","SE HA INGRESADO EL VEHICULO");
            return new ResponseEntity(response,HttpStatus.CREATED);
        }
        response.put("Status","200");
        response.put("Message","ERROR AL INGRESAR EL VEHICULO");
        return  new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "/cars")
    public ResponseEntity findCarsByAll(){
        Map response = new HashMap();
        try{
            return new ResponseEntity(carServicesImp.allCars(),HttpStatus.OK);
        }catch (Exception e){
            response.put("Status","500");
            response.put("Message","TODOS LOS VEHICULOS REGISTRADOS");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity updateCar(@PathVariable Long id, @RequestBody Car car){
        Map<String, String> response = new HashMap<>();
        if (!carServicesImp.updateCar(id, car)){
            response.put("Status","100");
            response.put("Message","ERROR EN LOS DATOS");
            return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
        }
        response.put("Status","600");
        response.put("Message","VEHICULO ACTUALIZADO");
        return new ResponseEntity(response, HttpStatus.OK);
    }
}