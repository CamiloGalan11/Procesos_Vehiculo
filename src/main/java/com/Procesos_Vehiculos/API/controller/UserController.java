package com.Procesos_Vehiculos.API.controller;

import com.Procesos_Vehiculos.API.models.User;
import com.Procesos_Vehiculos.API.service.UserService;
import com.Procesos_Vehiculos.API.utils.ApiResponse;
import com.Procesos_Vehiculos.API.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

public class UserController {

    @Autowired
    private UserService userService;
    private ApiResponse apiResponse;
    Map data = new HashMap<>();

    @GetMapping(value = "/{id}")
    public ResponseEntity findUserById(@PathVariable Long id){
        try {
            apiResponse = new ApiResponse(Constants.REGISTER_FOUND, userService.getUser(id));
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        }catch(Exception e){
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND,"");
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(value = "")
    public ResponseEntity saveUser(@RequestBody User user){
        Boolean userResp = userService.createUser(user);

        if(userResp == true){
            apiResponse = new ApiResponse(Constants.REGISTER_CREATED,"");
            return new ResponseEntity(apiResponse, HttpStatus.CREATED);
        }
        apiResponse = new ApiResponse(Constants.REGISTER_BAD, user);
        return new ResponseEntity(apiResponse,HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "")
    public ResponseEntity findAllUser(){
        try {
            apiResponse = new ApiResponse(Constants.REGISTER_LIST,userService.allUsers());
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        }catch(Exception e){
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND,"");
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
        }
    }
}