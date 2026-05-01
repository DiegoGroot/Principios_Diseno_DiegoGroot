package com.postgres.android_proyect.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.postgres.android_proyect.models.AndroidVersion;
import com.postgres.android_proyect.models.User;
import com.postgres.android_proyect.dto.CreateAndroidVersionRequest;
import com.postgres.android_proyect.repository.AndroidRepository;
import com.postgres.android_proyect.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/android-versions")
public class AndroidVersionController {

    private static final Logger logger = LoggerFactory.getLogger(AndroidVersionController.class);

    @Autowired
    private AndroidRepository androidVersionRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Obtiene todas las versiones de Android de un usuario
     * @param userId ID del usuario
     * @return Lista de versiones del usuario
     */
    @GetMapping
    public List<AndroidVersion> getAll(@RequestParam Long userId) {
        logger.info("Obteniendo versiones de Android para usuario: {}", userId);
        return androidVersionRepository.findByUserId(userId);
    }

    /**
     * Crea una nueva versión de Android para un usuario
     * @param userId ID del usuario propietario
     * @param request Datos de la nueva versión
     * @return Versión creada o error
     */
    @PostMapping
    @Transactional
    public ResponseEntity<?> create(
            @RequestParam Long userId,
            @Valid @RequestBody CreateAndroidVersionRequest request) {

        logger.info("Creando nueva versión de Android para usuario: {}", userId);

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            logger.warn("Usuario no encontrado: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        AndroidVersion version = new AndroidVersion(
            request.getNombre(),
            request.getFecha(),
            request.getDescripcion(),
            request.getCaracteristicas(),
            request.getUrlPhoto()
        );

        version.setUser(user.get());
        AndroidVersion saved = androidVersionRepository.save(version);

        logger.info("Versión de Android creada exitosamente. ID: {}", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Obtiene una versión específica de Android
     * @param id ID de la versión
     * @return Versión encontrada o error 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        logger.info("Buscando versión de Android: {}", id);
        return androidVersionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Actualiza una versión existente
     * @param id ID de la versión
     * @param request Datos a actualizar
     * @return Versión actualizada o error
     */
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateAndroidVersionRequest request) {

        logger.info("Actualizando versión de Android: {}", id);

        return androidVersionRepository.findById(id)
                .map(version -> {
                    version.setNombre(request.getNombre());
                    version.setFecha(request.getFecha());
                    version.setDescripcion(request.getDescripcion());
                    version.setCaracteristicas(request.getCaracteristicas());
                    version.setUrlPhoto(request.getUrlPhoto());

                    AndroidVersion updated = androidVersionRepository.save(version);
                    logger.info("Versión actualizada exitosamente: {}", id);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Elimina una versión de Android
     * @param id ID de la versión a eliminar
     * @return Respuesta de éxito o error
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Eliminando versión de Android: {}", id);

        if (androidVersionRepository.existsById(id)) {
            androidVersionRepository.deleteById(id);
            logger.info("Versión eliminada exitosamente: {}", id);
            return ResponseEntity.noContent().build();
        }

        logger.warn("Versión no encontrada para eliminar: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
