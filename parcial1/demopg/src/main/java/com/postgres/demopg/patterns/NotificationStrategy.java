package com.postgres.demopg.patterns;

/**
 * Strategy Pattern Interface para notificaciones.
 * Define el contrato para diferentes estrategias de notificación.
 *
 * @author Diego Groot
 * @version 1.0
 */
public interface NotificationStrategy {
    /**
     * Envía una notificación usando la estrategia específica.
     *
     * @param recipient Destinatario de la notificación
     * @param message Mensaje a enviar
     * @return true si se envió exitosamente, false en caso contrario
     */
    boolean send(String recipient, String message);

    /**
     * Obtiene el nombre de la estrategia.
     *
     * @return nombre de la estrategia
     */
    String getStrategyName();
}
