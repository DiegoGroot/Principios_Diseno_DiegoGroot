package com.postgres.demopg.patterns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SMS Notification Strategy Implementation.
 * Implementa envío de notificaciones por SMS.
 *
 * @author Diego Groot
 * @version 1.0
 */
public class SMSNotificationStrategy implements NotificationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SMSNotificationStrategy.class);

    @Override
    public boolean send(String recipient, String message) {
        try {
            if (!isValidPhoneNumber(recipient)) {
                logger.warn("Número de teléfono inválido: {}", recipient);
                return false;
            }

            // Limitar mensajes SMS a 160 caracteres
            String limitedMessage = message.length() > 160 
                ? message.substring(0, 157) + "..." 
                : message;

            logger.info("Enviando SMS a: {} - Mensaje: {}", recipient, limitedMessage);
            // Simulación de envío de SMS
            Thread.sleep(50);
            logger.debug("SMS enviado exitosamente a: {}", recipient);
            return true;
        } catch (InterruptedException e) {
            logger.error("Error al enviar SMS", e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public String getStrategyName() {
        return "SMS";
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{7,15}");
    }
}
