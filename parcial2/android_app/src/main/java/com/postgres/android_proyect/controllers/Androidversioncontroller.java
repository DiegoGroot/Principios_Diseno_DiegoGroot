package com.postgres.android_proyect.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.postgres.android_proyect.models.android_tweet;
import com.postgres.android_proyect.models.CreateAndroidVersionRequest;
import com.postgres.android_proyect.models.User;
import com.postgres.android_proyect.repository.AndroidRepository;
import com.postgres.android_proyect.repository.UserRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/android-versions")
public class Androidversioncontroller {

    @Autowired
    private AndroidRepository androidVersionRepository;

    @Autowired
    private UserRepository userRepository;

    // GET todas las versiones de un usuario
    @GetMapping
    public List<android_tweet> getAll(@RequestParam Long userId) {
        return androidVersionRepository.findByUserId(userId);
    }

    // POST crear versión para un usuario
    @PostMapping
    @Transactional
    public ResponseEntity<?> create(
            @RequestParam Long userId,
            @Valid @RequestBody CreateAndroidVersionRequest request) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        android_tweet version = new android_tweet(
            request.getNombre(),
            request.getFecha(),
            request.getDescripcion(),
            request.getCaracteristicas(),
            request.getUrlPhoto()
        );
        version.setUser(user.get());
        return ResponseEntity.ok(androidVersionRepository.save(version));
    }

    // DELETE versión por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!androidVersionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        androidVersionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}