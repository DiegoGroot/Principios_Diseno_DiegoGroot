package com.postgres.android_proyect.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.postgres.android_proyect.models.User;
import com.postgres.android_proyect.repository.UserRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET todos los usuarios
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // GET usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // POST crear usuario (registro)
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        if (userRepository.existsByCorreo(user.getCorreo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El correo ya está registrado");
        }
        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // POST login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> found = userRepository.findByCorreo(loginRequest.getCorreo());
        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario no encontrado");
        }
        User user = found.get();
        if (!user.getContrasena().equals(loginRequest.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Contraseña incorrecta");
        }
        return ResponseEntity.ok(user);
    }

    // PUT actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody User updated) {
        Optional<User> existing = userRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = existing.get();
        user.setNombre(updated.getNombre());
        user.setCorreo(updated.getCorreo());
        user.setContrasena(updated.getContrasena());
        return ResponseEntity.ok(userRepository.save(user));
    }

    // DELETE eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}