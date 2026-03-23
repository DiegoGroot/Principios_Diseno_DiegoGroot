import 'package:http/http.dart' as http;
import 'dart:convert';
import '../models/tweet.dart';
import '../models/tweet_response.dart';
import 'package:flutter/foundation.dart';

class TweetService {
  static final TweetService _instance = TweetService._internal();

 final String baseUrl = kIsWeb
   ? '/api'
   : 'https://tweeter-api-diegogroot.onrender.com/api/tweets';
  late http.Client _httpClient;

  TweetService._internal() {
    _httpClient = http.Client();
  }

  factory TweetService() {
    return _instance;
  }

  static TweetService getInstance() {
    return _instance;
  }

  Future<List<Tweet>> fetchTweets() async {
    try {
      final response = await _httpClient.get(
        Uri.parse('$baseUrl/tweets'),
      );

      if (response.statusCode == 200) {
        final jsonData = jsonDecode(response.body) as Map<String, dynamic>;
        final tweetResponse = TweetResponse.fromJson(jsonData);
        return tweetResponse.content;
      } else {
        throw Exception(
          'Failed to load tweets, Status code: ${response.statusCode}',
        );
      }
    } catch (e) {
      print('ERROR: $e'); print('ERROR: $e'); throw Exception('Error fetching tweet: $e');
    }
  }

  void dispose() {
    _httpClient.close();
  }
}


