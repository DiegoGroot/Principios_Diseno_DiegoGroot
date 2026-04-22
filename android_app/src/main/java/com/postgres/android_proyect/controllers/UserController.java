package com.postgres.android_proyect.controllers;

import com.postgres.android_proyect.models.User;
import com.postgres.android_proyect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") 
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Esto hará que el error 405 desaparezca en el navegador
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Este es el que usa tu botón de "Registrarse" en Flutter
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        // Log para ver en la terminal de VS Code si llega la petición
        System.out.println(">>> Recibida petición de registro para: " + user.getCorreo());
        
        if (userRepository.existsByCorreo(user.getCorreo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El correo ya está registrado");
        }
        
        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        return userRepository.findByCorreo(loginRequest.getCorreo())
                .filter(user -> user.getContrasena().equals(loginRequest.getContrasena()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}