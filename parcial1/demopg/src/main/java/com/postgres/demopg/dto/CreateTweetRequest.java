package com.postgres.demopg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para crear o actualizar tweets
 */
public class CreateTweetRequest {

    @NotBlank(message = "El contenido del tweet no puede estar vacío")
    @Size(max = 280, message = "El tweet no puede exceder 280 caracteres")
    private String content;

    /**
     * Constructor por defecto
     */
    public CreateTweetRequest() {}

    /**
     * Constructor con contenido
     * @param content Contenido del tweet
     */
    public CreateTweetRequest(String content) {
        this.content = content;
    }

    /**
     * Obtiene el contenido del tweet
     * @return Contenido
     */
    public String getContent() {
        return content;
    }

    /**
     * Establece el contenido del tweet
     * @param content Nuevo contenido
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CreateTweetRequest{" +
                "content='" + content + '\'' +
                '}';
    }
}
