package com.Procesos_Vehiculos.API.controller;

import com.Procesos_Vehiculos.API.models.User;
import com.Procesos_Vehiculos.API.service.AuthService;
import com.Procesos_Vehiculos.API.utils.ResponseApi;
import com.Procesos_Vehiculos.API.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;
    private ResponseApi responseApi;
    Map data = new HashMap<>();

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody User user){

        try{
            data.put("token",authService.login(user));
            responseApi = new ResponseApi(Constants.USER_LOGIN, data);
            return new ResponseEntity(responseApi, HttpStatus.OK);
        }catch (Exception e){
            responseApi = new ResponseApi(e.getMessage(), "");
            return new ResponseEntity<>(responseApi,HttpStatus.NOT_FOUND);
        }
    }
}
