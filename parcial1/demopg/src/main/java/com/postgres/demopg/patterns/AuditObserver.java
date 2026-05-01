package com.postgres.demopg.patterns;

/**
 * Observer Pattern Interface para eventos de auditoría.
 * Define el contrato para observadores de eventos de auditoría.
 *
 * @author Diego Groot
 * @version 1.0
 */
public interface AuditObserver {
    /**
     * Notifica un cambio en la auditoría.
     *
     * @param event evento de auditoría
     */
    void update(AuditEvent event);
}
