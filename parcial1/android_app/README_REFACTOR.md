# Android Versions API - Documentación

## 📋 Descripción
API REST para gestión de versiones de Android y usuarios, desarrollada con **Spring Boot 4.0.4** y **Java 21**.

## 🏗️ Estructura del Proyecto

```
android_app/
├── src/
│   ├── main/
│   │   ├── java/com/postgres/android_proyect/
│   │   │   ├── controllers/        # Controladores REST
│   │   │   │   ├── UserController.java
│   │   │   │   └── AndroidVersionController.java
│   │   │   ├── models/             # Entidades JPA
│   │   │   │   ├── User.java
│   │   │   │   └── AndroidVersion.java
│   │   │   ├── dto/                # Data Transfer Objects
│   │   │   │   └── CreateAndroidVersionRequest.java
│   │   │   ├── repository/         # Interfaces de acceso a datos
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── AndroidRepository.java
│   │   │   ├── CorsConfig.java     # Configuración de CORS
│   │   │   └── AndroidProyectApplication.java
│   │   └── resources/
│   │       ├── application.properties        # Config por defecto
│   │       ├── application-local.properties  # Config local
│   │       └── application-prod.properties   # Config producción
│   └── test/
├── pom.xml
└── Dockerfile
```

## 🔄 Refactoring Realizado

### ✅ Cambios Principales

1. **Renombrado de Clases** (Convención PascalCase)
   - `android_tweet.java` → `AndroidVersion.java`
   - `Androidversioncontroller.java` → `AndroidVersionController.java`

2. **Reorganización de Paquetes**
   - DTOs movidos a paquete dedicado: `com.postgres.android_proyect.dto`
   - Mejor separación de responsabilidades

3. **Logging Centralizado**
   - Reemplazado `System.out.println()` con **SLF4J Logger**
   - Implementación consistente en todos los controladores
   - Niveles de log configurables en `application.properties`

4. **Configuración CORS Consolidada**
   - Eliminada decorador `@CrossOrigin` de controladores
   - CORS centralizado en `CorsConfig.java`
   - Métodos HTTP adicionales: `OPTIONS`

5. **Mejoras en POM.xml**
   - Dependencias mejor organizadas y comentadas
   - Removida dependencia incompleta `spring-boot-starter-webmvc-test`
   - Agregada `spring-boot-starter-test` correcta
   - Información de proyecto completa (nombre, descripción, dev)

6. **Documentación de Código**
   - JavaDoc agregado a todos los métodos públicos
   - Comentarios explicativos en configuraciones

7. **Configuración Mejorada**
   - `application.properties` ampliado con:
     - Batch configuration para Hibernate
     - SQL formatting en debug
     - Logging levels específicos
     - Error handling mejorado

## 🚀 Endpoints de la API

### Usuarios
- `GET /api/users` - Obtener todos los usuarios
- `POST /api/users` - Registrar nuevo usuario
- `POST /api/users/login` - Autenticar usuario
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario

### Versiones de Android
- `GET /api/android-versions?userId={id}` - Obtener versiones de usuario
- `POST /api/android-versions?userId={id}` - Crear nueva versión
- `GET /api/android-versions/{id}` - Obtener versión específica
- `PUT /api/android-versions/{id}` - Actualizar versión
- `DELETE /api/android-versions/{id}` - Eliminar versión

## 🛠️ Stack Tecnológico

| Componente | Versión |
|-----------|---------|
| Java | 21 |
| Spring Boot | 4.0.4 |
| PostgreSQL | (Variable según env) |
| Maven | 3.9+ |
| JPA/Hibernate | Spring Data JPA |

## 📦 Dependencias Principales

```xml
- spring-boot-starter-web: API REST
- spring-boot-starter-data-jpa: Acceso a datos
- jakarta.validation: Validación de datos
- hibernate-validator: Implementación de validación
- postgresql: Driver PostgreSQL
- h2database: BD para testing
- spring-boot-starter-logging: SLF4J + Logback
```

## 🔐 Configuración de Ambiente

### Variables de Entorno Requeridas:
```bash
DATABASE_URL=jdbc:postgresql://host:port/dbname
DB_USER=usuario
DB_PASSWORD=contraseña
PORT=8080
```

### Configuración Local (application-local.properties)
```properties
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
logging.level.com.postgres.android_proyect=DEBUG
```

## 🧪 Testing

Para ejecutar tests:
```bash
mvn test
```

## 🐳 Docker

Para construir la imagen:
```bash
docker build -t android-api .
```

Para ejecutar:
```bash
docker run -e DATABASE_URL=... -e DB_USER=... -e DB_PASSWORD=... -p 8080:8080 android-api
```

## 📝 Convenciones de Código

- **Clases**: PascalCase (`AndroidVersion`, `UserController`)
- **Métodos**: camelCase (`getUserById()`, `createVersion()`)
- **Constantes**: UPPER_SNAKE_CASE (`DATABASE_URL`)
- **Paquetes**: all lowercase (`com.postgres.android_proyect.dto`)
- **Logging**: Usar `logger.info()`, `logger.warn()`, `logger.error()` de SLF4J

## 🔍 Logging

Los logs se configuran en `application.properties`:

```properties
logging.level.root=INFO
logging.level.com.postgres.android_proyect=DEBUG
```

Ejemplo de salida:
```
2024-04-24 10:30:45 - Creando nueva versión de Android para usuario: 1
2024-04-24 10:30:46 - Versión de Android creada exitosamente. ID: 42
```

## 📚 Recursos Útiles

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Jakarta Validation](https://jakarta.ee/specifications/validation/)
- [PostgreSQL JDBC](https://jdbc.postgresql.org/)

## ✨ Próximas Mejoras Sugeridas

- [ ] Implementar autenticación JWT
- [ ] Agregar Spring Security
- [ ] Crear tests unitarios y de integración
- [ ] Implementar paginación en endpoints GET
- [ ] Agregar API documentation con Springdoc OpenAPI/Swagger
- [ ] Implementar caching (Redis)
- [ ] Agregar auditoría de cambios (created_at, updated_at)
- [ ] Implementar soft delete para entidades

---

**Última actualización**: 24 de Abril, 2026
