# DemoPG - Tweet API

## 📋 Descripción
API REST para gestión de tweets, desarrollada con **Spring Boot 3.4.3** y **Java 21**.

## 🏗️ Estructura del Proyecto

```
demopg/
├── src/
│   ├── main/
│   │   ├── java/com/postgres/demopg/
│   │   │   ├── controllers/        # Controladores REST
│   │   │   │   └── TweetController.java
│   │   │   ├── models/             # Entidades JPA
│   │   │   │   └── Tweet.java
│   │   │   ├── dto/                # Data Transfer Objects
│   │   │   │   └── CreateTweetRequest.java
│   │   │   ├── repository/         # Interfaces de acceso a datos
│   │   │   │   └── TweetRepository.java
│   │   │   ├── CorsConfig.java     # Configuración de CORS
│   │   │   └── DemopgApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── Dockerfile
```

## 🔄 Refactoring Realizado

### ✅ Cambios Principales

1. **Mejora de Entidades**
   - `Tweet.java`: Refactorizado completamente
     - Agregados campos `createdAt` y `updatedAt`
     - Nombre de campo: `tweet` → `content` (más descriptivo)
     - Mejor validación con mensajes claros
     - JavaDoc completo
     - Aumentado límite de caracteres: 140 → 280
     - Implementado `toString()`

2. **Creación de DTOs**
   - Creado paquete `dto/`
   - `CreateTweetRequest.java`: DTO para crear/actualizar tweets
   - Separación de responsabilidades modelo-presentación

3. **Logging Profesional**
   - Reemplazado uso implícito con **SLF4J Logger**
   - Agregados en `TweetController`:
     - 10+ logs informativos
     - Niveles apropiados (INFO, DEBUG, WARN)
   - Configuración en `application.properties`

4. **Controlador Mejorado**
   - Eliminado `@CrossOrigin` (CORS centralizado)
   - Agregados 5 métodos de utilidad:
     - `GET /api/tweets?page=0&size=10` - Listado paginado
     - `GET /api/tweets/{id}` - Obtener tweet específico
     - `POST /api/tweets` - Crear tweet
     - `PUT /api/tweets/{id}` - Actualizar tweet
     - `DELETE /api/tweets/{id}` - Eliminar tweet
   - Manejo de errores HTTP (201, 404, 500)
   - JavaDoc en todos los métodos

5. **CORS Centralizado**
   - Configuración única en `CorsConfig.java`
   - Eliminado duplicado en controlador
   - Agregados: OPTIONS, maxAge, confirmCredentials

6. **POM.xml Mejorado**
   - Versión: `0.0.1-SNAPSHOT` → `1.0.0`
   - Información de proyecto completada
   - Dependencias reorganizadas
   - Eliminada dependencia duplicada
   - Agregado Maven Compiler Plugin

7. **Configuración Avanzada**
   - `application.properties` ampliado con:
     - Batch processing Hibernate
     - Configuración de logging levels
     - Compression
     - Error handling

## 🚀 Endpoints de la API

### Tweets
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/tweets` | Obtener tweets (paginado) |
| GET | `/api/tweets/{id}` | Obtener tweet específico |
| POST | `/api/tweets` | Crear nuevo tweet |
| PUT | `/api/tweets/{id}` | Actualizar tweet |
| DELETE | `/api/tweets/{id}` | Eliminar tweet |

### Ejemplo de Uso

**Crear un tweet:**
```bash
curl -X POST http://localhost:8080/api/tweets \
  -H "Content-Type: application/json" \
  -d '{"content":"Hola desde DemoPG!"}'
```

**Obtener tweets (paginado):**
```bash
curl http://localhost:8080/api/tweets?page=0&size=10
```

**Actualizar un tweet:**
```bash
curl -X PUT http://localhost:8080/api/tweets/1 \
  -H "Content-Type: application/json" \
  -d '{"content":"Contenido actualizado"}'
```

**Eliminar un tweet:**
```bash
curl -X DELETE http://localhost:8080/api/tweets/1
```

## 🛠️ Stack Tecnológico

| Componente | Versión |
|-----------|---------|
| Java | 21 |
| Spring Boot | 3.4.3 |
| PostgreSQL | Variable |
| Maven | 3.9+ |
| JPA/Hibernate | Spring Data JPA |

## 📦 Dependencias Principales

- `spring-boot-starter-web`: API REST
- `spring-boot-starter-data-jpa`: Acceso a datos
- `jakarta.validation`: Validación
- `postgresql`: Driver PostgreSQL
- `spring-boot-starter-logging`: SLF4J + Logback

## 🔐 Configuración de Ambiente

### Variables Requeridas:
```properties
spring.datasource.url=jdbc:postgresql://host:port/dbname
spring.datasource.username=usuario
spring.datasource.password=contraseña
server.port=8080
```

## 📝 Convenciones de Código

- **Clases**: PascalCase (`Tweet`, `TweetController`)
- **Métodos**: camelCase (`getTweets()`, `createTweet()`)
- **Constantes**: UPPER_SNAKE_CASE
- **Paquetes**: lowercase (`com.postgres.demopg.dto`)
- **Logging**: Usar `logger.info()`, `logger.debug()` de SLF4J

## 🧪 Ejecutar Localmente

```bash
# Clonar el repositorio
git clone <repo-url>

# Navegar al directorio
cd demopg

# Compilar con Maven
./mvnw clean install

# Ejecutar la aplicación
./mvnw spring-boot:run

# La API estará disponible en http://localhost:8080
```

## 🐳 Docker

**Construir imagen:**
```bash
docker build -t demopg:1.0.0 .
```

**Ejecutar contenedor:**
```bash
docker run -e DATABASE_URL=... -p 8080:8080 demopg:1.0.0
```

## 🔍 Logging

Configurado con SLF4J + Logback:

```
2024-04-24 10:30:45 - Creando nuevo tweet con contenido: Hola
2024-04-24 10:30:46 - Tweet creado exitosamente con ID: 1
```

## ✨ Próximas Mejoras

- [ ] Autenticación JWT
- [ ] Spring Security
- [ ] Tests unitarios
- [ ] Swagger/OpenAPI
- [ ] Reacciones a tweets
- [ ] Búsqueda full-text
- [ ] Rate limiting
- [ ] Redis caching

---

**Versión**: 1.0.0  
**Última actualización**: 24 de Abril, 2026
