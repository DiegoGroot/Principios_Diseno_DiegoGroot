package com.postgres.demopg.patterns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Email Notification Strategy Implementation.
 * Implementa envío de notificaciones por email.
 *
 * @author Diego Groot
 * @version 1.0
 */
public class EmailNotificationStrategy implements NotificationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationStrategy.class);

    @Override
    public boolean send(String recipient, String message) {
        try {
            if (!isValidEmail(recipient)) {
                logger.warn("Email inválido: {}", recipient);
                return false;
            }

            logger.info("Enviando email a: {} - Mensaje: {}", recipient, message);
            // Simulación de envío de email
            Thread.sleep(100);
            logger.debug("Email enviado exitosamente a: {}", recipient);
            return true;
        } catch (InterruptedException e) {
            logger.error("Error al enviar email", e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public String getStrategyName() {
        return "EMAIL";
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
