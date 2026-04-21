import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/android_version.dart';

class AndroidService {
  static const String baseUrl = 'https://android-versions.onrender.com/api/android-versions';

  /// Obtener versiones del usuario
  static Future<List<AndroidVersion>> getAll(int userId) async {
    final response = await http.get(Uri.parse('$baseUrl?userId=$userId'));
    if (response.statusCode == 200) {
      List<dynamic> body = jsonDecode(response.body);
      return body.map((item) => AndroidVersion.fromJson(item)).toList();
    } else {
      throw Exception('Error al conectar con Render');
    }
  }

  /// Crear versión para el usuario
  static Future<AndroidVersion> create(AndroidVersion version, int userId) async {
    final response = await http.post(
      Uri.parse('$baseUrl?userId=$userId'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'nombre': version.nombre,
        'fecha': version.fecha,
        'descripcion': version.descripcion,
        'caracteristicas': version.caracteristicas,
        'urlPhoto': version.urlPhoto,
      }),
    );
    if (response.statusCode == 200) {
      return AndroidVersion.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Error al crear versión');
    }
  }

  /// Eliminar versión
  static Future<void> delete(int id) async {
    final response = await http.delete(Uri.parse('$baseUrl/$id'));
    if (response.statusCode != 204) {
      throw Exception('Error al eliminar versión');
    }
  }
}