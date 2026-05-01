package com.postgres.demopg.patterns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Push Notification Strategy Implementation.
 * Implementa envío de notificaciones push a aplicaciones móviles.
 *
 * @author Diego Groot
 * @version 1.0
 */
public class PushNotificationStrategy implements NotificationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(PushNotificationStrategy.class);

    @Override
    public boolean send(String recipient, String message) {
        try {
            if (!isValidDeviceToken(recipient)) {
                logger.warn("Token de dispositivo inválido: {}", recipient);
                return false;
            }

            logger.info("Enviando Push Notification a dispositivo: {} - Mensaje: {}", recipient, message);
            // Simulación de envío de push notification
            Thread.sleep(75);
            logger.debug("Push notification enviada exitosamente a: {}", recipient);
            return true;
        } catch (InterruptedException e) {
            logger.error("Error al enviar push notification", e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public String getStrategyName() {
        return "PUSH";
    }

    private boolean isValidDeviceToken(String token) {
        return token != null && token.length() >= 20;
    }
}
