package com.Procesos_Vehiculos.API.repository;

import com.Procesos_Vehiculos.API.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long>{
    Optional<User> findByEmail(String email);
}
