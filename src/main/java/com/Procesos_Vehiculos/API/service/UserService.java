package com.Procesos_Vehiculos.API.service;

import com.Procesos_Vehiculos.API.models.User;

import java.util.List;

public interface UserService {
    User getUser(Long id);
    Boolean createUser(User user);
    List<User> allUsers();
    Boolean updateUser(Long id, User user);
    String login(User user);
}
