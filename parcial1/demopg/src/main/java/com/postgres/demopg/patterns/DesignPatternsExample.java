package com.postgres.demopg.patterns;

/**
 * Ejemplo de uso de los patrones de diseño implementados en DemoPG.
 * Este archivo sirve como referencia de cómo usar cada patrón.
 *
 * @author Diego Groot
 * @version 1.0
 */
public class DesignPatternsExample {

    public static void main(String[] args) {
        System.out.println("=== DemoPG Design Patterns Examples ===\n");

        // Ejemplo 1: Builder Pattern
        exampleBuilder();

        // Ejemplo 2: Strategy Pattern
        exampleStrategy();

        // Ejemplo 3: Singleton Pattern
        exampleSingleton();

        // Ejemplo 4: Observer Pattern
        exampleObserver();
    }

    /**
     * Demuestra el uso del Builder Pattern para crear Tweets.
     */
    public static void exampleBuilder() {
        System.out.println("1️⃣ BUILDER PATTERN");
        System.out.println("-".repeat(50));

        try {
            // Crear un tweet usando el builder
            com.postgres.demopg.models.Tweet tweet1 = TweetBuilder.builder()
                    .withContent("¡Hola Spring Boot!")
                    .withCreatedAt(java.time.LocalDateTime.now())
                    .build();

            System.out.println("✅ Tweet creado: " + tweet1.getContent());

            // Crear otro tweet
            com.postgres.demopg.models.Tweet tweet2 = TweetBuilder.builder()
                    .withId(1L)
                    .withContent("Los patrones de diseño son increíbles")
                    .build();

            System.out.println("✅ Tweet #" + tweet2.getId() + ": " + tweet2.getContent());

            // Intentar crear un tweet inválido (esto lanzaría excepción)
            // TweetBuilder.builder()
            //     .withContent("x".repeat(300))
            //     .build(); // ❌ Excepción: excede 280 caracteres

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * Demuestra el uso del Strategy Pattern para notificaciones.
     */
    public static void exampleStrategy() {
        System.out.println("2️⃣ STRATEGY PATTERN");
        System.out.println("-".repeat(50));

        NotificationService notifier = new NotificationService();

        // Usar estrategia EMAIL
        System.out.println("🔔 Enviando por EMAIL:");
        notifier.setStrategy("EMAIL");
        notifier.notify("diego@example.com", "Tu tweet fue publicado exitosamente");

        // Usar estrategia SMS
        System.out.println("\n📱 Enviando por SMS:");
        notifier.setStrategy("SMS");
        notifier.notify("1234567890", "Nuevo tweet publicado");

        // Usar estrategia PUSH
        System.out.println("\n📲 Enviando PUSH Notification:");
        notifier.setStrategy("PUSH");
        notifier.notify("device_token_abc123xyz789", "Alguien comentó tu tweet");

        // Listar estrategias disponibles
        System.out.println("\n📋 Estrategias disponibles:");
        for (String strategy : notifier.getAvailableStrategies()) {
            System.out.println("   - " + strategy);
        }

        System.out.println();
    }

    /**
     * Demuestra el uso del Singleton Pattern para configuración.
     */
    public static void exampleSingleton() {
        System.out.println("3️⃣ SINGLETON PATTERN");
        System.out.println("-".repeat(50));

        // Obtener instancia de configuración
        AppConfiguration config1 = AppConfiguration.getInstance();
        AppConfiguration config2 = AppConfiguration.getInstance();

        // Verificar que es la misma instancia
        System.out.println("¿Misma instancia? " + (config1 == config2 ? "✅ Sí" : "❌ No"));

        // Acceder a configuración
        System.out.println("\n⚙️ Configuración de la aplicación:");
        System.out.println("   Versión App: " + config1.getAppVersion());
        System.out.println("   Versión API: " + config1.getApiVersion());
        System.out.println("   Max Tweet Length: " + config1.getMaxTweetLength() + " caracteres");
        System.out.println("   Request Timeout: " + config1.getRequestTimeoutMs() + " ms");

        System.out.println("\n📊 Info: " + config1.getInfo());

        System.out.println();
    }

    /**
     * Demuestra el uso del Observer Pattern para auditoría.
     */
    public static void exampleObserver() {
        System.out.println("4️⃣ OBSERVER PATTERN");
        System.out.println("-".repeat(50));

        // Crear el subject (observable)
        AuditSubject auditSubject = new AuditSubject();

        // Crear y registrar observadores
        AuditLogObserver logObserver = new AuditLogObserver();
        auditSubject.registerObserver(logObserver);

        System.out.println("📋 Observadores registrados: " + auditSubject.getObserverCount());

        // Crear y notificar eventos
        System.out.println("\n📝 Notificando eventos de auditoría:");

        AuditEvent createEvent = new AuditEvent(
                "CREATE",
                "Tweet",
                1L,
                "Nuevo tweet creado",
                "user123"
        );
        System.out.println("Evento 1: " + createEvent);
        auditSubject.notifyObservers(createEvent);

        AuditEvent updateEvent = new AuditEvent(
                "UPDATE",
                "Tweet",
                1L,
                "Tweet actualizado",
                "user123"
        );
        System.out.println("\nEvento 2: " + updateEvent);
        auditSubject.notifyObservers(updateEvent);

        AuditEvent deleteEvent = new AuditEvent(
                "DELETE",
                "Tweet",
                1L,
                "Tweet eliminado",
                "user123"
        );
        System.out.println("\nEvento 3: " + deleteEvent);
        auditSubject.notifyObservers(deleteEvent);

        System.out.println();
    }
}
