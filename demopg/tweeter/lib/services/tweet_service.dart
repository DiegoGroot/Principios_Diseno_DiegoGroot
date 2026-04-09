import 'package:http/http.dart' as http;
import 'dart:convert';
import '../models/tweet.dart';

class TweetService {
  // ── Singleton ─────────────────────────────────────────────────────────────
  static final TweetService _instance = TweetService._internal();
  factory TweetService() => _instance;
  static TweetService getInstance() => _instance;

  TweetService._internal() {
    _httpClient = http.Client();
  }

  // ── Config ────────────────────────────────────────────────────────────────
  static const String _baseUrl = 'https://tweeter-api-diegogroot.onrender.com';
  late http.Client _httpClient;

  // ── GET /api/tweets ───────────────────────────────────────────────────────
  // La API devuelve respuesta paginada: { "content": [...], ... }
  Future<List<Tweet>> fetchTweets() async {
    try {
      final response = await _httpClient.get(
        Uri.parse('$_baseUrl/api/tweets'),
      );
      if (response.statusCode == 200) {
        final Map<String, dynamic> jsonData = jsonDecode(response.body);
        final List<dynamic> content = jsonData['content'];
        return content.map((item) => Tweet.fromJson(item)).toList();
      } else {
        throw Exception('Failed to load tweets. Status code: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error fetching tweets: $e');
    }
  }

  // ── POST /api/tweets ──────────────────────────────────────────────────────
  Future<Tweet> createTweet(String content) async {
    try {
      if (content.isEmpty) throw Exception('Tweet content cannot be empty');
      final response = await _httpClient.post(
        Uri.parse('$_baseUrl/api/tweets'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'tweet': content}),
      );
      if (response.statusCode == 200 || response.statusCode == 201) {
        return Tweet.fromJson(jsonDecode(response.body));
      } else {
        throw Exception('Failed to create tweet. Status code: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error creating tweet: $e');
    }
  }

  // ── DELETE /api/tweets/:id ────────────────────────────────────────────────
  Future<void> deleteTweet(int id) async {
    try {
      final response = await _httpClient.delete(
        Uri.parse('$_baseUrl/api/tweets/$id'),
      );
      if (response.statusCode != 200 && response.statusCode != 204) {
        throw Exception('Failed to delete tweet. Status code: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error deleting tweet: $e');
    }
  }

  // ── Liberar recursos ──────────────────────────────────────────────────────
  void dispose() {
    _httpClient.close();
  }
}
