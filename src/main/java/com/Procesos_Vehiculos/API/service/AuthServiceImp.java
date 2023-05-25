package com.Procesos_Vehiculos.API.service;


import com.Procesos_Vehiculos.API.models.User;
import com.Procesos_Vehiculos.API.repository.UserRepository;
import com.Procesos_Vehiculos.API.utils.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor

public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;

    @Override
    public String login(User user) {

        User userDb = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        if (userDb == null){
            throw new RuntimeException("Las credenciales son erroneas");
        }

        return JwtToken.generateToken(userDb);

    }
}
