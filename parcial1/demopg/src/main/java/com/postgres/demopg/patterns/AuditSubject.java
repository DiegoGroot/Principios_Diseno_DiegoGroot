package com.postgres.demopg.patterns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Subject/Observable para eventos de auditoría.
 * Notifica a todos los observadores registrados de cambios.
 *
 * @author Diego Groot
 * @version 1.0
 */
public class AuditSubject {
    private static final Logger logger = LoggerFactory.getLogger(AuditSubject.class);
    private final List<AuditObserver> observers;

    public AuditSubject() {
        this.observers = new ArrayList<>();
        logger.debug("AuditSubject inicializado");
    }

    /**
     * Registra un observador para recibir notificaciones de auditoría.
     *
     * @param observer observador a registrar
     */
    public void registerObserver(AuditObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            logger.debug("Observador registrado: {}", observer.getClass().getSimpleName());
        }
    }

    /**
     * Desregistra un observador.
     *
     * @param observer observador a desregistrar
     */
    public void removeObserver(AuditObserver observer) {
        observers.remove(observer);
        logger.debug("Observador desregistrado: {}", observer.getClass().getSimpleName());
    }

    /**
     * Notifica a todos los observadores de un evento.
     *
     * @param event evento de auditoría
     */
    public void notifyObservers(AuditEvent event) {
        logger.debug("Notificando {} observadores de evento: {}", observers.size(), event.getEventType());
        for (AuditObserver observer : observers) {
            observer.update(event);
        }
    }

    /**
     * Obtiene el número de observadores registrados.
     *
     * @return cantidad de observadores
     */
    public int getObserverCount() {
        return observers.size();
    }
}
