package com.postgres.android_proyect.facade;

import com.postgres.android_proyect.models.Comentario;
import com.postgres.android_proyect.models.Reaccion;
import com.postgres.android_proyect.models.android_tweet;
import com.postgres.android_proyect.repository.AndroidRepository;
import com.postgres.android_proyect.repository.ComentarioRepository;
import com.postgres.android_proyect.repository.ReaccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TweetFacade {

    private final AndroidRepository tweetRepository;
    private final ComentarioRepository comentarioRepository;
    private final ReaccionRepository reaccionRepository;

    public TweetFacade(AndroidRepository tweetRepository,
                       ComentarioRepository comentarioRepository,
                       ReaccionRepository reaccionRepository) {
        this.tweetRepository = tweetRepository;
        this.comentarioRepository = comentarioRepository;
        this.reaccionRepository = reaccionRepository;
    }

    public Comentario agregarComentario(Long tweetId, String texto) {
        android_tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Error: El Tweet con ID " + tweetId + " no existe en la DB."));
        Comentario comentario = new Comentario(texto, tweet);
        return comentarioRepository.save(comentario);
    }

    public List<Comentario> obtenerComentarios(Long tweetId) {
        return comentarioRepository.findByTweetId(tweetId);
    }

    public Reaccion agregarReaccion(Long tweetId, String tipo) {
        android_tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet no encontrado"));
        Reaccion reaccion = new Reaccion(tipo, tweet);
        return reaccionRepository.save(reaccion);
    }

    public List<Reaccion> obtenerReacciones(Long tweetId) {
        return reaccionRepository.findByTweetId(tweetId);
    }

    public Map<String, Long> contarReacciones(Long tweetId) {
        List<Reaccion> reacciones = reaccionRepository.findByTweetId(tweetId);
        Map<String, Long> conteo = new java.util.HashMap<>();
        for (Reaccion r : reacciones) {
            conteo.merge(r.getTipo(), 1L, Long::sum);
        }
        return conteo;
    }
}