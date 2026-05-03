import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/android_version.dart';

class AndroidService {
  static const String _base = 'https://android-versions.onrender.com';
  static const String baseUrl = '$_base/api/android-versions';
  static const String tweetUrl = '$_base/api/tweets';

  static Future<List<AndroidVersion>> getAll(int userId) async {
    final response = await http.get(Uri.parse('$baseUrl?userId=$userId'));
    if (response.statusCode == 200) {
      List<dynamic> body = jsonDecode(response.body);
      return body.map((item) => AndroidVersion.fromJson(item)).toList();
    } else {
      throw Exception('Error al conectar con el servidor');
    }
  }

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
      throw Exception('Error al crear version: ' + response.body);
    }
  }

  static Future<void> delete(int id) async {
    await http.delete(Uri.parse('$baseUrl/$id'));
  }

  static Future<List<Map<String, dynamic>>> getComentarios(int tweetId) async {
    final response = await http.get(Uri.parse('$tweetUrl/$tweetId/comentarios'));
    if (response.statusCode == 200) {
      List<dynamic> body = jsonDecode(response.body);
      return body.cast<Map<String, dynamic>>();
    } else {
      throw Exception('Error al obtener comentarios');
    }
  }

  static Future<void> agregarComentario(int tweetId, String texto) async {
    final response = await http.post(
      Uri.parse('$tweetUrl/$tweetId/comentarios'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'texto': texto}),
    );
    if (response.statusCode != 200) {
      throw Exception('Error al agregar comentario: ' + response.statusCode.toString() + ' - ' + response.body);
    }
  }

  static Future<Map<String, dynamic>> getConteoReacciones(int tweetId) async {
    final response = await http.get(Uri.parse('$tweetUrl/$tweetId/reacciones/conteo'));
    if (response.statusCode == 200) {
      return Map<String, dynamic>.from(jsonDecode(response.body));
    } else {
      return {};
    }
  }

  static Future<void> agregarReaccion(int tweetId, String tipo) async {
    final response = await http.post(
      Uri.parse('$tweetUrl/$tweetId/reacciones'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'tipo': tipo}),
    );
    if (response.statusCode != 200) {
      throw Exception('Error al agregar reaccion: ' + response.statusCode.toString());
    }
  }
}
