package com.postgres.android_proyect.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reacciones")
public class Reaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id")
    @JsonIgnore
    private android_tweet tweet;

    public Reaccion() {}

    public Reaccion(String tipo, android_tweet tweet) {
        this.tipo = tipo;
        this.tweet = tweet;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public android_tweet getTweet() { return tweet; }
    public void setTweet(android_tweet tweet) { this.tweet = tweet; }
}
