package com.postgres.android_proyect.controllers;

import com.postgres.android_proyect.facade.TweetFacade;
import com.postgres.android_proyect.models.Comentario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tweets")
public class ComentarioController {

    private final TweetFacade tweetFacade;

    public ComentarioController(TweetFacade tweetFacade) {
        this.tweetFacade = tweetFacade;
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<?> agregar(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Comentario c = tweetFacade.agregarComentario(id, body.get("texto"));
            return ResponseEntity.ok(c);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<Comentario>> listar(@PathVariable Long id) {
        return ResponseEntity.ok(tweetFacade.obtenerComentarios(id));
    }
}