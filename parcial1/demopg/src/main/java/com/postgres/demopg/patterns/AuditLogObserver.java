package com.postgres.demopg.patterns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Observador de auditoría que registra eventos en logs.
 *
 * @author Diego Groot
 * @version 1.0
 */
public class AuditLogObserver implements AuditObserver {
    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT");

    @Override
    public void update(AuditEvent event) {
        auditLogger.info("{}", event);
    }

    public String getObserverName() {
        return "AuditLogObserver";
    }
}
