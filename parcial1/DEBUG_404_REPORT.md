# 404 POST /api/users Endpoint - Debug Report

## Executive Summary
The 404 error on `POST /api/users` is most likely caused by **database table not being created** or **backend service not running**. The endpoint is properly configured, but Hibernate is not set to auto-create tables, and there's a **mismatch between frontend and backend URLs**.

---

## 1. USER MODEL & DATABASE SCHEMA

### User Entity Structure
**File**: `src/main/java/com/postgres/android_proyect/models/User.java`

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String nombre;
    
    @NotBlank
    @Size(max = 150)
    @Column(unique = true)
    private String correo;  // Email - UNIQUE constraint
    
    @NotBlank
    private String contrasena;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<android_tweet> versiones;
}
```

### Database Configuration
**File**: `src/main/resources/application.properties`

```properties
# Database Connection
spring.datasource.url=jdbc:postgresql://localhost:5432/android_versions
spring.datasource.username=postgres
spring.datasource.password=postgres

# Server
server.port=8082

# JPA
spring.jpa.open-in-view=false
```

### ⚠️ CRITICAL ISSUE: No Schema Auto-Creation
```
❌ Missing: spring.jpa.hibernate.ddl-auto=create
❌ Missing: spring.jpa.hibernate.ddl-auto=update
```

**What this means**: Hibernate will NOT automatically create the `users` table when Spring Boot starts. You must manually create it.

### Expected Database Schema
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    contrasena TEXT NOT NULL
);

CREATE TABLE android_versions (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    fecha VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    caracteristicas VARCHAR(1000),
    url_photo TEXT,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

---

## 2. USERREPOSITORY ANALYSIS

**File**: `src/main/java/com/postgres/android_proyect/repository/UserRepository.java`

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCorreo(String correo);  // Find by email
    boolean existsByCorreo(String correo);        // Check if email exists
}
```

**Inherited Methods** (from JpaRepository):
- `findAll()` - Get all users
- `findById(Long id)` - Get user by ID
- `save(User user)` - Create/update user
- `deleteById(Long id)` - Delete user
- `existsById(Long id)` - Check if user exists

---

## 3. USERCONTROLLER - FULL ENDPOINT MAPPING

**File**: `src/main/java/com/postgres/android_proyect/controllers/UserController.java`

```java
@CrossOrigin(origins = "*", maxAge = 3600)  // CORS enabled for all origins
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    // 1️⃣ GET /api/users
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }
    
    // 2️⃣ GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    // 3️⃣ POST /api/users (REGISTRATION)
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        // Check if email already exists
        if (userRepository.existsByCorreo(user.getCorreo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El correo ya está registrado");
        }
        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);  // 201
    }
    
    // 4️⃣ POST /api/users/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> found = userRepository.findByCorreo(loginRequest.getCorreo());
        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario no encontrado");
        }
        User user = found.get();
        if (!user.getContrasena().equals(loginRequest.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Contraseña incorrecta");
        }
        return ResponseEntity.ok(user);  // 200
    }
    
    // 5️⃣ PUT /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody User updated) {
        Optional<User> existing = userRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = existing.get();
        user.setNombre(updated.getNombre());
        user.setCorreo(updated.getCorreo());
        user.setContrasena(updated.getContrasena());
        return ResponseEntity.ok(userRepository.save(user));
    }
    
    // 6️⃣ DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();  // 204
    }
}
```

### Expected Request/Response Examples

**Register (POST /api/users)**
```json
REQUEST:
POST /api/users HTTP/1.1
Content-Type: application/json

{
  "nombre": "Diego Groot",
  "correo": "diego@example.com",
  "contrasena": "password123"
}

RESPONSE (201 Created):
{
  "id": 1,
  "nombre": "Diego Groot",
  "correo": "diego@example.com",
  "contrasena": "password123"
}

RESPONSE (409 Conflict - email exists):
"El correo ya está registrado"
```

**Login (POST /api/users/login)**
```json
REQUEST:
{
  "correo": "diego@example.com",
  "contrasena": "password123"
}

RESPONSE (200 OK):
{
  "id": 1,
  "nombre": "Diego Groot",
  "correo": "diego@example.com",
  "contrasena": "password123"
}

RESPONSE (401 Unauthorized):
"Usuario no encontrado" or "Contraseña incorrecta"
```

---

## 4. FLUTTER FRONTEND - API CALLS

### User Model
**File**: `android_versions/lib/models/user.dart`

```dart
class User {
  final int id;
  final String nombre;
  final String correo;
  final String contrasena;
  
  // JSON serialization for API calls
  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      nombre: json['nombre'],
      correo: json['correo'],
      contrasena: json['contrasena'],
    );
  }
  
  Map<String, dynamic> toJson() => {
    'id': id,
    'nombre': nombre,
    'correo': correo,
    'contrasena': contrasena,
  };
}
```

### UserService - API Integration
**File**: `android_versions/lib/services/user_service.dart`

```dart
class UserService {
  static const String baseUrl = 'https://android-versions.onrender.com/api/users';
  
  /// Register new user
  static Future<User> register(String nombre, String correo, String contrasena) async {
    final response = await http.post(
      Uri.parse(baseUrl),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'nombre': nombre,
        'correo': correo,
        'contrasena': contrasena,
      }),
    );

    if (response.statusCode == 201) {
      final user = User.fromJson(jsonDecode(response.body));
      await saveUserLocal(user);
      return user;
    } else if (response.statusCode == 409) {
      throw Exception('El correo ya está registrado');
    } else {
      throw Exception('Error al registrar: ${response.body}');
    }
  }
  
  /// Login with email and password
  static Future<User> login(String correo, String contrasena) async {
    final response = await http.post(
      Uri.parse('$baseUrl/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'correo': correo,
        'contrasena': contrasena,
      }),
    );

    if (response.statusCode == 200) {
      final user = User.fromJson(jsonDecode(response.body));
      await saveUserLocal(user);
      return user;
    } else if (response.statusCode == 401) {
      throw Exception('Correo o contraseña incorrectos');
    } else {
      throw Exception('Error al iniciar sesión');
    }
  }
  
  /// Update user data
  static Future<User> update(User user) async {
    final response = await http.put(
      Uri.parse('$baseUrl/${user.id}'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'nombre': user.nombre,
        'correo': user.correo,
        'contrasena': user.contrasena,
      }),
    );

    if (response.statusCode == 200) {
      final updated = User.fromJson(jsonDecode(response.body));
      await saveUserLocal(updated);
      return updated;
    } else {
      throw Exception('Error al actualizar usuario');
    }
  }
  
  /// Delete account
  static Future<void> deleteAccount(int id) async {
    final response = await http.delete(Uri.parse('$baseUrl/$id'));
    if (response.statusCode == 204) {
      await clearUser();
    }
  }
}
```

### Login Screen Usage
**File**: `android_versions/lib/screens/login_screen.dart`

```dart
class _LoginScreenState extends State<LoginScreen> {
  bool _isLoginMode = true;  // Toggle between login and registration
  
  Future<void> _submit() async {
    try {
      User user;
      if (_isLoginMode) {
        user = await UserService.login(
          _correoCtrl.text.trim(),
          _contrasenaCtrl.text.trim(),
        );
      } else {
        // REGISTRATION CALL
        user = await UserService.register(
          _nombreCtrl.text.trim(),
          _correoCtrl.text.trim(),
          _contrasenaCtrl.text.trim(),
        );
      }
      // Navigate to HomeScreen
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (_) => HomeScreen(currentUser: user)),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('$e'), backgroundColor: Colors.redAccent),
      );
    }
  }
}
```

---

## 5. CONFIGURATION ISSUES - ROOT CAUSE ANALYSIS

### Issue #1: ⚠️ URL Mismatch (PRIMARY)
**Frontend URL**: `https://android-versions.onrender.com/api/users`
**Backend URL**: `http://localhost:8082/api/users`

**Problem**: The Flutter app is hardcoded to call a Render-deployed backend, NOT your local development server.

**Why this causes 404**:
- If your local backend is running → You're calling a different server (404 from wrong server)
- If your local backend is NOT running → The Render production server doesn't have the same database setup

### Issue #2: ⚠️ Database Not Initialized (LIKELY ROOT CAUSE)
**Missing Configuration**:
```properties
# Add this to application.properties
spring.jpa.hibernate.ddl-auto=update  # or 'create' for fresh start
spring.jpa.show-sql=true              # For debugging
```

**Problem**: The `users` table is not being created automatically.

**How to verify**:
```bash
# Connect to PostgreSQL
psql -U postgres -d android_versions

# Check if table exists
\dt users;

# If not, create it manually:
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    contrasena TEXT NOT NULL
);
```

### Issue #3: ⚠️ Backend Startup Failure
The context shows `Exit Code: 1` from `mvn spring-boot:run` in `android_versions` folder.

**Likely causes**:
1. PostgreSQL database `android_versions` doesn't exist
2. Database connection fails → Spring Boot startup fails
3. JPA initialization fails because table doesn't exist

**Check by running**:
```bash
# Start Spring Boot with better logging
cd /home/diego-groot/Documentos/Principios_Diseno_DiegoGroot/android_app
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.jpa.show-sql=true --spring.jpa.hibernate.ddl-auto=create"
```

---

## 6. CONFIGURATION ISSUES THAT PREVENT ENDPOINT DISCOVERY

### Current Spring Boot Configuration
✅ **What's configured correctly**:
- CORS enabled for all origins
- @RestController and @RequestMapping annotations present
- HTTP methods defined (@PostMapping, @GetMapping, etc.)
- Validation annotations present (@Valid, @NotBlank)

❌ **What's missing or broken**:
1. **No auto schema creation**
   - `spring.jpa.hibernate.ddl-auto` not set
   - Causes: Table doesn't exist → SQL errors → Endpoint appears unavailable
   
2. **No logging enabled**
   - Can't see startup errors
   - Can't debug query issues
   
3. **Wrong API URL in Flutter**
   - Frontend calls production Render
   - Backend runs locally
   - Frontend never reaches local backend

### How Endpoint Discovery Works
```
1. Spring Boot starts → Scans @SpringBootApplication package
2. Finds @RestController classes
3. Maps @RequestMapping("/api/users") → Base path
4. Maps @PostMapping → POST /api/users
5. When client sends POST request → Spring routes to create() method
6. If database is unreachable → 500 error (not 404)
7. If endpoint not found → 404 error
```

**If you're getting 404**:
- Endpoint not registered (check @RequestMapping paths)
- OR frontend calling wrong server (most likely)
- OR backend not running

---

## 7. RECOMMENDED FIXES

### Fix #1: Enable Database Auto-Schema Creation
**File**: `src/main/resources/application.properties`

```properties
# Add these lines:
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.springframework.web=DEBUG
```

### Fix #2: Update Flutter to Use Local Backend
**File**: `android_versions/lib/services/user_service.dart`

```dart
// Change from:
static const String baseUrl = 'https://android-versions.onrender.com/api/users';

// To (for local development):
static const String baseUrl = 'http://localhost:8082/api/users';

// Or (for production):
static const String baseUrl = 'https://your-production-server.com/api/users';
```

### Fix #3: Verify PostgreSQL Database Exists
```bash
# Create database if it doesn't exist
psql -U postgres -c "CREATE DATABASE android_versions;"

# Verify it exists
psql -U postgres -l | grep android_versions
```

### Fix #4: Start Backend with Debugging
```bash
cd /home/diego-groot/Documentos/Principios_Diseno_DiegoGroot/android_app
mvn spring-boot:run \
  -Dspring-boot.run.arguments="--spring.jpa.hibernate.ddl-auto=create --spring.jpa.show-sql=true"
```

This will:
- Create tables automatically on startup
- Log all SQL queries to console
- Show any connection errors clearly

---

## 8. CHECKLIST FOR DEBUGGING

- [ ] PostgreSQL is running on localhost:5432
- [ ] Database `android_versions` exists
- [ ] Spring Boot starts without errors on port 8082
- [ ] `users` table exists in database (check with `\dt users;` in psql)
- [ ] Flutter base URL matches backend URL (localhost:8082 for dev)
- [ ] Postman can successfully POST to `http://localhost:8082/api/users`
- [ ] No CORS errors in browser console (if testing from web)
- [ ] Request body includes: `nombre`, `correo`, `contrasena`

---

## 9. QUICK TEST WITH CURL

```bash
# Test if backend is running
curl -X POST http://localhost:8082/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Test User",
    "correo": "test@example.com",
    "contrasena": "password123"
  }'

# Expected response: 201 Created (with user JSON)
# Or: 409 Conflict (if email exists)
# Or: 404 Not Found (backend not running or table doesn't exist)
# Or: 500 Internal Server Error (database connection failed)
```

---

## Summary

| Component | Status | Details |
|-----------|--------|---------|
| User Entity | ✅ Correct | 4 fields, proper validation |
| UserRepository | ✅ Correct | 2 custom methods implemented |
| UserController | ✅ Correct | All 6 endpoints configured |
| CORS Config | ✅ Correct | Enabled for all origins |
| Database Schema | ❌ Missing | No auto-creation configured |
| Frontend URL | ⚠️ Mismatch | Points to Render, not localhost |
| Hibernation Config | ❌ Missing | `ddl-auto` not set |
| Logging | ❌ Missing | Can't see startup errors |

**Most Likely Cause**: Combination of missing database table creation and frontend pointing to wrong URL.

**Quick Fix**: Add `spring.jpa.hibernate.ddl-auto=create` to `application.properties` and update Flutter `baseUrl` to `localhost:8082`.
