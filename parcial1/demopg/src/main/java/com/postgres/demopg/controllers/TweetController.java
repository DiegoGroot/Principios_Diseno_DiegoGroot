package com.postgres.demopg.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.postgres.demopg.models.Tweet;
import com.postgres.demopg.dto.CreateTweetRequest;
import com.postgres.demopg.repository.TweetRepository;

/**
 * Controlador REST para gestionar tweets
 * Maneja todas las operaciones CRUD relacionadas con tweets
 */
@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

    @Autowired
    private TweetRepository tweetRepository;

    /**
     * Obtiene todos los tweets con paginación
     * @param pageable Parámetros de paginación (page, size, sort)
     * @return Página de tweets
     */
    @GetMapping
    public Page<Tweet> getTweets(Pageable pageable) {
        logger.info("Obteniendo tweets con paginación: page={}, size={}", 
                   pageable.getPageNumber(), pageable.getPageSize());
        return tweetRepository.findAll(pageable);
    }

    /**
     * Obtiene un tweet específico por ID
     * @param id ID del tweet
     * @return Tweet encontrado o error 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTweetById(@PathVariable Long id) {
        logger.info("Buscando tweet con ID: {}", id);
        return tweetRepository.findById(id)
                .<ResponseEntity<?>>map(tweet -> {
                    logger.debug("Tweet encontrado: {}", tweet);
                    return ResponseEntity.ok(tweet);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Tweet no encontrado con ID: " + id));
    }

    /**
     * Crea un nuevo tweet
     * @param request Datos del tweet a crear
     * @return Tweet creado con estado 201
     */
    @PostMapping
    public ResponseEntity<?> createTweet(@Valid @RequestBody CreateTweetRequest request) {
        logger.info("Creando nuevo tweet con contenido: {}", request.getContent());

        try {
            Tweet newTweet = new Tweet(request.getContent());
            Tweet savedTweet = tweetRepository.save(newTweet);
            logger.info("Tweet creado exitosamente con ID: {}", savedTweet.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTweet);
        } catch (Exception e) {
            logger.error("Error al crear tweet: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el tweet");
        }
    }

    /**
     * Actualiza un tweet existente
     * @param id ID del tweet a actualizar
     * @param request Nuevos datos del tweet
     * @return Tweet actualizado o error 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTweet(@PathVariable Long id, 
                                        @Valid @RequestBody CreateTweetRequest request) {
        logger.info("Actualizando tweet con ID: {}", id);

        return tweetRepository.findById(id)
                .<ResponseEntity<?>>map(tweet -> {
                    tweet.setContent(request.getContent());
                    Tweet updated = tweetRepository.save(tweet);
                    logger.info("Tweet actualizado exitosamente: {}", id);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Tweet no encontrado con ID: " + id));
    }

    /**
     * Elimina un tweet existente
     * @param id ID del tweet a eliminar
     * @return Respuesta de éxito 204 o error 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTweet(@PathVariable Long id) {
        logger.info("Eliminando tweet con ID: {}", id);

        if (tweetRepository.existsById(id)) {
            tweetRepository.deleteById(id);
            logger.info("Tweet eliminado exitosamente: {}", id);
            return ResponseEntity.noContent().build();
        }

        logger.warn("Intento de eliminar tweet no existente: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tweet no encontrado con ID: " + id);
    }
}
