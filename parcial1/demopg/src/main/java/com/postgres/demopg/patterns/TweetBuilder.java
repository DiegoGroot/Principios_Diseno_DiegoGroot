package com.postgres.demopg.patterns;

import com.postgres.demopg.models.Tweet;
import java.time.LocalDateTime;

/**
 * Builder Pattern Implementation para Tweet.
 * Permite construcción fluida y validación de Tweet.
 *
 * @author Diego Groot
 * @version 1.0
 */
public class TweetBuilder {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TweetBuilder() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public TweetBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public TweetBuilder withContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido del tweet no puede estar vacío");
        }
        if (content.length() > 280) {
            throw new IllegalArgumentException("El tweet no puede exceder 280 caracteres");
        }
        this.content = content;
        return this;
    }

    public TweetBuilder withCreatedAt(LocalDateTime createdAt) {
        if (createdAt != null) {
            this.createdAt = createdAt;
        }
        return this;
    }

    public TweetBuilder withUpdatedAt(LocalDateTime updatedAt) {
        if (updatedAt != null) {
            this.updatedAt = updatedAt;
        }
        return this;
    }

    public Tweet build() {
        if (this.content == null || this.content.trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido del tweet no puede estar vacío");
        }

        Tweet tweet = new Tweet();
        tweet.setId(this.id);
        tweet.setContent(this.content);
        tweet.setCreatedAt(this.createdAt);
        tweet.setUpdatedAt(this.updatedAt);

        return tweet;
    }

    public static TweetBuilder builder() {
        return new TweetBuilder();
    }
}
