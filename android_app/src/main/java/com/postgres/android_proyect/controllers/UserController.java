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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody User user) {
        System.out.println(">>> Recibida petición PUT para actualizar usuario ID: " + id);
        if (userRepository.existsById(id)) {
            User existingUser = userRepository.findById(id).get();
            existingUser.setNombre(user.getNombre());
            existingUser.setCorreo(user.getCorreo());
            existingUser.setContrasena(user.getContrasena());
            User updated = userRepository.save(existingUser);
            System.out.println(">>> Usuario actualizado correctamente: " + updated.getCorreo());
            return ResponseEntity.ok(updated);
        } else {
            System.out.println(">>> Usuario no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        System.out.println(">>> Recibida petición DELETE para eliminar usuario ID: " + id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            System.out.println(">>> Usuario eliminado correctamente: " + id);
            return ResponseEntity.noContent().build();
        } else {
            System.out.println(">>> Usuario no encontrado para eliminar: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}