// lib/services/android_service.dart

import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/android_version.dart';

class AndroidService {
  // Cambia esta URL cuando hagas deploy
  static const String baseUrl = 'https://android-versions.onrender.com/api/android-versions';

  /// GET — obtener todas las versiones (paginado)
  static Future<List<AndroidVersion>> getAll({int page = 0, int size = 20}) async {
    final response = await http.get(
      Uri.parse('$baseUrl?page=$page&size=$size'),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      final List content = data['content'];
      return content.map((e) => AndroidVersion.fromJson(e)).toList();
    } else {
      throw Exception('Error al cargar versiones');
    }
  }

  /// GET — obtener por ID
  static Future<AndroidVersion> getById(int id) async {
    final response = await http.get(Uri.parse('$baseUrl/$id'));

    if (response.statusCode == 200) {
      return AndroidVersion.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Versión no encontrada');
    }
  }

  /// POST — crear nueva versión
  static Future<AndroidVersion> create(AndroidVersion version) async {
    final response = await http.post(
      Uri.parse(baseUrl),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'nombre':          version.nombre,
        'fecha':           version.fecha,
        'descripcion':     version.descripcion,
        'caracteristicas': version.caracteristicas,
        'urlPhoto':        version.urlPhoto,
      }),
    );

    if (response.statusCode == 201) {
      return AndroidVersion.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Error al crear versión');
    }
  }

  /// DELETE — eliminar por ID
  static Future<void> delete(int id) async {
    final response = await http.delete(Uri.parse('$baseUrl/$id'));
    if (response.statusCode != 200 && response.statusCode != 204) {
      throw Exception('Error al eliminar versión');
    }
  }
}
