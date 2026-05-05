package com.postgres.android_proyect.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id")
    @JsonIgnore
    private android_tweet tweet;

    public Comentario() {}

    public Comentario(String texto, android_tweet tweet) {
        this.texto = texto;
        this.tweet = tweet;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public android_tweet getTweet() { return tweet; }
    public void setTweet(android_tweet tweet) { this.tweet = tweet; }
}
