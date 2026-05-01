package com.postgres.android_proyect.controllers;

import com.postgres.android_proyect.models.User;
import com.postgres.android_proyect.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Obtiene todos los usuarios registrados
     * @return Lista de usuarios
     */
    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Obteniendo todos los usuarios");
        return userRepository.findAll();
    }

    /**
     * Registra un nuevo usuario
     * @param user Datos del usuario a registrar
     * @return Usuario creado o error de conflicto
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        logger.info("Nuevo registro solicitado para: {}", user.getCorreo());
        
        if (userRepository.existsByCorreo(user.getCorreo())) {
            logger.warn("Intento de registro con correo duplicado: {}", user.getCorreo());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El correo ya está registrado");
        }
        
        User saved = userRepository.save(user);
        logger.info("Usuario registrado exitosamente: {}", saved.getCorreo());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Autentica un usuario
     * @param loginRequest Credenciales de login
     * @return Usuario autenticado o error de autorización
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        logger.info("Intento de login para: {}", loginRequest.getCorreo());
        
        return userRepository.findByCorreo(loginRequest.getCorreo())
                .filter(user -> user.getContrasena().equals(loginRequest.getContrasena()))
                .map(user -> {
                    logger.info("Login exitoso para: {}", loginRequest.getCorreo());
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    /**
     * Actualiza un usuario existente
     * @param id ID del usuario a actualizar
     * @param user Nuevos datos del usuario
     * @return Usuario actualizado o error 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody User user) {
        logger.info("Actualizando usuario ID: {}", id);

        if (!userRepository.existsById(id)) {
            logger.warn("Usuario no encontrado para actualizar: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User existingUser = userRepository.findById(id).get();
        existingUser.setNombre(user.getNombre());
        existingUser.setCorreo(user.getCorreo());
        existingUser.setContrasena(user.getContrasena());

        User updated = userRepository.save(existingUser);
        logger.info("Usuario actualizado exitosamente: {}", updated.getCorreo());
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un usuario
     * @param id ID del usuario a eliminar
     * @return Respuesta de éxito o error 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Eliminando usuario ID: {}", id);

        if (!userRepository.existsById(id)) {
            logger.warn("Usuario no encontrado para eliminar: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        userRepository.deleteById(id);
        logger.info("Usuario eliminado exitosamente: {}", id);
        return ResponseEntity.noContent().build();
    }
}
