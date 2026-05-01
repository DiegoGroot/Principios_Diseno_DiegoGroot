package com.postgres.demopg.patterns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Notification Service usando Strategy Pattern.
 * Permite cambiar dinámicamente la estrategia de notificación.
 *
 * @author Diego Groot
 * @version 1.0
 */
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final Map<String, NotificationStrategy> strategies;
    private NotificationStrategy currentStrategy;

    public NotificationService() {
        this.strategies = new HashMap<>();
        this.strategies.put("EMAIL", new EmailNotificationStrategy());
        this.strategies.put("SMS", new SMSNotificationStrategy());
        this.strategies.put("PUSH", new PushNotificationStrategy());
        this.currentStrategy = strategies.get("EMAIL"); // Default
        logger.info("NotificationService inicializado con {} estrategias disponibles", strategies.size());
    }

    /**
     * Establece la estrategia de notificación a usar.
     *
     * @param strategyName nombre de la estrategia (EMAIL, SMS, PUSH)
     * @throws IllegalArgumentException si la estrategia no existe
     */
    public void setStrategy(String strategyName) {
        if (!strategies.containsKey(strategyName)) {
            throw new IllegalArgumentException("Estrategia no soportada: " + strategyName);
        }
        this.currentStrategy = strategies.get(strategyName);
        logger.info("Estrategia de notificación cambiada a: {}", strategyName);
    }

    /**
     * Envía una notificación usando la estrategia actual.
     *
     * @param recipient destinatario
     * @param message mensaje a enviar
     * @return true si se envió exitosamente
     */
    public boolean notify(String recipient, String message) {
        if (currentStrategy == null) {
            logger.error("Ninguna estrategia configurada");
            return false;
        }
        logger.debug("Enviando notificación vía {}: {}", currentStrategy.getStrategyName(), recipient);
        return currentStrategy.send(recipient, message);
    }

    /**
     * Envía una notificación usando una estrategia específica.
     *
     * @param strategyName nombre de la estrategia a usar
     * @param recipient destinatario
     * @param message mensaje a enviar
     * @return true si se envió exitosamente
     */
    public boolean notifyWith(String strategyName, String recipient, String message) {
        NotificationStrategy strategy = strategies.get(strategyName);
        if (strategy == null) {
            logger.error("Estrategia no encontrada: {}", strategyName);
            return false;
        }
        logger.debug("Enviando notificación vía {}: {}", strategyName, recipient);
        return strategy.send(recipient, message);
    }

    /**
     * Obtiene las estrategias disponibles.
     *
     * @return lista de nombres de estrategias disponibles
     */
    public String[] getAvailableStrategies() {
        return strategies.keySet().toArray(new String[0]);
    }

    /**
     * Obtiene la estrategia actual.
     *
     * @return nombre de la estrategia actual
     */
    public String getCurrentStrategy() {
        return currentStrategy != null ? currentStrategy.getStrategyName() : "NONE";
    }
}
