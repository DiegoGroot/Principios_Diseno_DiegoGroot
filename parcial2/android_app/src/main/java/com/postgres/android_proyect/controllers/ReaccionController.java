package com.postgres.android_proyect.controllers;

import com.postgres.android_proyect.facade.TweetFacade;
import com.postgres.android_proyect.models.Reaccion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tweets")
public class ReaccionController {

    private final TweetFacade tweetFacade;

    public ReaccionController(TweetFacade tweetFacade) {
        this.tweetFacade = tweetFacade;
    }

    @PostMapping("/{id}/reacciones")
    public ResponseEntity<?> reaccionar(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Reaccion r = tweetFacade.agregarReaccion(id, body.get("tipo"));
            return ResponseEntity.ok(r);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/reacciones")
    public ResponseEntity<List<Reaccion>> listar(@PathVariable Long id) {
        return ResponseEntity.ok(tweetFacade.obtenerReacciones(id));
    }

    @GetMapping("/{id}/reacciones/conteo")
    public ResponseEntity<Map<String, Long>> conteo(@PathVariable Long id) {
        return ResponseEntity.ok(tweetFacade.contarReacciones(id));
    }
}