package com.postgres.android_proyect.controllers;

import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.postgres.android_proyect.models.android_tweet;
import com.postgres.android_proyect.repository.AndroidRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/android-versions")
public class Androidversioncontroller {

    @Autowired
    private AndroidRepository androidVersionRepository;

    @GetMapping("")
    public List<android_tweet> getAndroidVersions() {
        return androidVersionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<android_tweet> getAndroidVersionById(@PathVariable Long id) {
        return androidVersionRepository.findById(id);
    }

    @PostMapping("")
    public android_tweet createAndroidVersion(@Valid @RequestBody android_tweet androidVersion) {
        return androidVersionRepository.save(androidVersion);
    }

    @DeleteMapping("/{id}")
    public void deleteAndroidVersion(@PathVariable Long id) {
        androidVersionRepository.deleteById(id);
    }
}
