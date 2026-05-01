package com.postgres.demopg.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entidad que representa un Tweet en el sistema
 * Contiene el contenido del tweet y metadata asociada
 */
@Entity
@Table(name = "tweets")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El contenido del tweet no puede estar vacío")
    @Size(max = 280, message = "El tweet no puede exceder 280 caracteres")
    @Column(length = 280)
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Constructor por defecto para JPA
     */
    public Tweet() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Constructor con contenido del tweet
     * @param content Contenido del tweet
     */
    public Tweet(String content) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters y Setters

    /**
     * Obtiene el ID del tweet
     * @return ID único del tweet
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del tweet
     * @param id ID único del tweet
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el contenido del tweet
     * @return Contenido del tweet
     */
    public String getContent() {
        return content;
    }

    /**
     * Establece el contenido del tweet
     * @param content Nuevo contenido del tweet
     */
    public void setContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Obtiene la fecha de creación del tweet
     * @return Fecha de creación
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Establece la fecha de creación del tweet
     * @param createdAt Fecha de creación
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Obtiene la fecha de última actualización
     * @return Fecha de última actualización
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Establece la fecha de última actualización
     * @param updatedAt Fecha de última actualización
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
