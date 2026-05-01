package com.postgres.demopg.patterns;

import java.time.LocalDateTime;

/**
 * Evento de auditoría para registrar cambios en la aplicación.
 *
 * @author Diego Groot
 * @version 1.0
 */
public class AuditEvent {
    private final String eventType; // CREATE, UPDATE, DELETE, READ
    private final String entityType; // Tweet, User, etc.
    private final Long entityId;
    private final String details;
    private final String userId;
    private final LocalDateTime timestamp;

    public AuditEvent(String eventType, String entityType, Long entityId, String details, String userId) {
        this.eventType = eventType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.details = details;
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
    }

    public String getEventType() {
        return eventType;
    }

    public String getEntityType() {
        return entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public String getDetails() {
        return details;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s#%d - %s (by %s at %s)",
                eventType, entityType, entityType, entityId, details, userId, timestamp);
    }
}
