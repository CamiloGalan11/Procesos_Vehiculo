package com.Procesos_Vehiculos.API.controller;

import com.Procesos_Vehiculos.API.models.Car;
import com.Procesos_Vehiculos.API.models.CarObject;
import com.Procesos_Vehiculos.API.models.User;
import com.Procesos_Vehiculos.API.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@SecurityRequirement(name = "jwt")
public class CarController {
    @Autowired
    private CarService carService;
    @Operation(summary = "Get a car",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Car returned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "Car not found with provider id",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
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
    @Operation(summary = "Add a new car",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Car created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Car already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping(value = "/car/{id}")
    public ResponseEntity saveCar(@PathVariable Long id, @RequestParam Long userId){
        Map response = new HashMap();
        Car carResp = carService.createCar(id, userId);
        if (carResp != null){
            response.put("Status","201");
            response.put("Message","SE HA INGRESADO EL VEHICULO");
            return new ResponseEntity(response,HttpStatus.CREATED);
        }
        response.put("Status","400");
        response.put("Message","ERROR AL INGRESAR EL VEHICULO");
        return  new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }
    @Operation(summary = "Get all cars",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All cars returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
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
    @Operation(summary = "Update a car",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Car update",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
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
}
