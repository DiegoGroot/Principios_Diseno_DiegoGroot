import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import '../models/user.dart';

class UserService {
  static const _storageKey = 'current_user';
  static const String baseUrl = 'https://android-versions.onrender.com/api/users';

  // ── LOCAL STORAGE ──────────────────────────────────────────────

  static Future<User?> loadUser() async {
    final prefs = await SharedPreferences.getInstance();
    final jsonString = prefs.getString(_storageKey);
    if (jsonString == null || jsonString.isEmpty) return null;
    return User.fromJson(jsonDecode(jsonString));
  }

  static Future<void> saveUserLocal(User user) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_storageKey, jsonEncode(user.toJson()));
  }

  static Future<void> clearUser() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_storageKey);
  }

  // ── API CALLS ──────────────────────────────────────────────────

  /// Registrar nuevo usuario
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

  /// Login con correo y contraseña
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

  /// Actualizar datos del usuario
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

  /// Eliminar cuenta del usuario
  static Future<void> deleteAccount(int id) async {
    final response = await http.delete(Uri.parse('$baseUrl/$id'));
    if (response.statusCode == 204) {
      await clearUser();
    } else {
      throw Exception('Error al eliminar cuenta');
    }
  }
}
