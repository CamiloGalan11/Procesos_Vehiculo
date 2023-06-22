package com.Procesos_Vehiculos.API.controller;

import com.Procesos_Vehiculos.API.models.User;
import com.Procesos_Vehiculos.API.service.UserService;
import com.Procesos_Vehiculos.API.utils.ResponseApi;
import com.Procesos_Vehiculos.API.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class UserController {


    private final UserService userService;
    private ResponseApi responseApi;
    Map data = new HashMap<>();
    @Operation(summary = "Get a user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User returned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "User not found with provider id",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping(value = "/{id}")
    public ResponseEntity findUserById(@PathVariable Long id){
        try {
            responseApi = new ResponseApi(Constants.REGISTER_FOUND, userService.getUser(id));
            return new ResponseEntity(responseApi, HttpStatus.OK);
        }catch(Exception e){
            responseApi = new ResponseApi(Constants.REGISTER_NOT_FOUND,"");
            return new ResponseEntity<>(responseApi,HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Add a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "User already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping(value = "/")
    public ResponseEntity saveUser(@RequestBody User user){
        Boolean userResp = userService.createUser(user);

        if(userResp == true){
            responseApi = new ResponseApi(Constants.REGISTER_CREATED,"");
            return new ResponseEntity(responseApi, HttpStatus.CREATED);
        }
        responseApi = new ResponseApi(Constants.REGISTER_BAD, user);
        return new ResponseEntity(responseApi,HttpStatus.BAD_REQUEST);
    }
    @Operation(summary = "Get all users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All users returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping(value = "/alls")
    public ResponseEntity findAllUser(){
        try {
            responseApi = new ResponseApi(Constants.REGISTER_LIST,userService.allUsers());
            return new ResponseEntity(responseApi, HttpStatus.OK);
        }catch(Exception e){
            responseApi = new ResponseApi(Constants.REGISTER_NOT_FOUND,"");
            return new ResponseEntity<>(responseApi,HttpStatus.NOT_FOUND);
        }
    }
}
