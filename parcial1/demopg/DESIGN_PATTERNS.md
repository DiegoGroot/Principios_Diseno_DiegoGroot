# Patrones de Diseño Implementados en DemoPG

## 📋 Resumen
Este documento describe los patrones de diseño aplicados en el proyecto DemoPG para mejorar la calidad, mantenibilidad y escalabilidad del código.

---

## 🏗️ Patrones Implementados

### 1. **Builder Pattern** (`TweetBuilder`)
**Ubicación:** `com.postgres.demopg.patterns.TweetBuilder`

#### Propósito
Facilita la construcción compleja de objetos `Tweet` de manera fluida y legible.

#### Ventajas
- ✅ Construcción paso a paso
- ✅ Validación en cada paso
- ✅ Código más legible y mantenible
- ✅ Evita constructores con muchos parámetros

#### Uso
```java
Tweet tweet = TweetBuilder.builder()
    .withContent("Hola Spring Boot!")
    .withUserId(1L)
    .build();
```

---

### 2. **Strategy Pattern** (Notificaciones)
**Ubicación:** `com.postgres.demopg.patterns.Notification*`

#### Componentes
- `NotificationStrategy` - Interfaz base
- `EmailNotificationStrategy` - Envío por email
- `SMSNotificationStrategy` - Envío por SMS
- `PushNotificationStrategy` - Notificaciones push
- `NotificationService` - Orquestador de estrategias

#### Propósito
Permite cambiar dinámicamente el algoritmo de notificación sin modificar el código cliente.

#### Ventajas
- ✅ Flexibilidad para cambiar comportamientos
- ✅ Fácil agregar nuevas estrategias
- ✅ Responsabilidad única
- ✅ Facilita testing

#### Uso
```java
NotificationService notificationService = new NotificationService();
notificationService.setStrategy("EMAIL");
notificationService.notify("user@example.com", "Mensaje importante");

notificationService.setStrategy("SMS");
notificationService.notify("1234567890", "Mensaje corto");
```

---

### 3. **Singleton Pattern** (`AppConfiguration`)
**Ubicación:** `com.postgres.demopg.patterns.AppConfiguration`

#### Propósito
Garantiza una única instancia de configuración global en toda la aplicación.

#### Ventajas
- ✅ Control centralizado de configuración
- ✅ Acceso global sin parámetros
- ✅ Inicialización lazy
- ✅ Thread-safe

#### Uso
```java
AppConfiguration config = AppConfiguration.getInstance();
int maxTweetLength = config.getMaxTweetLength();
String apiVersion = config.getApiVersion();
```

---

### 4. **Observer Pattern** (Auditoría)
**Ubicación:** `com.postgres.demopg.patterns.Audit*`

#### Componentes
- `AuditObserver` - Interfaz de observadores
- `AuditEvent` - Evento de auditoría
- `AuditSubject` - Sujeto observable
- `AuditLogObserver` - Implementación de logging

#### Propósito
Notifica automáticamente a múltiples observadores cuando ocurren cambios en la aplicación.

#### Ventajas
- ✅ Desacoplamiento entre sujeto y observadores
- ✅ Fácil agregar nuevos observadores
- ✅ Auditoría automática de cambios
- ✅ Patrón basado en eventos

#### Uso
```java
AuditSubject auditSubject = new AuditSubject();
auditSubject.registerObserver(new AuditLogObserver());

AuditEvent event = new AuditEvent("CREATE", "Tweet", 1L, "New tweet created", "user123");
auditSubject.notifyObservers(event);
```

---

## 🔄 Flujo de Integración Recomendado

### Con TweetController
```
1. Usuario crea tweet
2. TweetController recibe request
3. TweetBuilder construye objeto
4. TweetRepository persiste
5. AuditSubject notifica cambio
6. AuditLogObserver registra en logs
7. NotificationService envía notificación
```

---

## 📊 Estructura de Directorios

```
src/main/java/com/postgres/demopg/patterns/
├── TweetBuilder.java                 # Builder Pattern
├── NotificationStrategy.java         # Strategy Pattern - Interface
├── EmailNotificationStrategy.java    # Strategy - Email
├── SMSNotificationStrategy.java      # Strategy - SMS
├── PushNotificationStrategy.java     # Strategy - Push
├── NotificationService.java          # Strategy - Orquestador
├── AppConfiguration.java             # Singleton Pattern
├── AuditObserver.java               # Observer Pattern - Interface
├── AuditEvent.java                  # Observer Pattern - Evento
├── AuditSubject.java                # Observer Pattern - Subject
├── AuditLogObserver.java            # Observer Pattern - Impl.
└── DESIGN_PATTERNS.md               # Este archivo
```

---

## 🚀 Próximas Mejoras

### Patrones a Implementar
- [ ] Factory Pattern para creación de entidades
- [ ] Facade Pattern para simplificar APIs complejas
- [ ] Decorator Pattern para validación
- [ ] State Pattern para máquinas de estado
- [ ] Adapter Pattern para integraciones externas

---

## 📚 Referencias

- Gang of Four Design Patterns
- Design Patterns in Java
- Refactoring.Guru Design Patterns

---

## ✍️ Autor
Diego Groot - 2026
