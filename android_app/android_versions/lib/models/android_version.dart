// lib/models/android_version.dart

class AndroidVersion {
  final int id;
  final String nombre;
  final String fecha;
  final String descripcion;
  final String caracteristicas;
  final String urlPhoto;

  AndroidVersion({
    required this.id,
    required this.nombre,
    required this.fecha,
    required this.descripcion,
    required this.caracteristicas,
    required this.urlPhoto,
  });

  /// Convert JSON to AndroidVersion object
  factory AndroidVersion.fromJson(Map<String, dynamic> json) {
    return AndroidVersion(
      id:              json['id']              ?? 0,
      nombre:          json['nombre']          ?? '',
      fecha:           json['fecha']           ?? '',
      descripcion:     json['descripcion']     ?? '',
      caracteristicas: json['caracteristicas'] ?? '',
      urlPhoto:        json['urlPhoto']        ?? '',
    );
  }

  /// Convert AndroidVersion object to JSON
  Map<String, dynamic> toJson() {
    return {
      'id':              id,
      'nombre':          nombre,
      'fecha':           fecha,
      'descripcion':     descripcion,
      'caracteristicas': caracteristicas,
      'urlPhoto':        urlPhoto,
    };
  }

  /// Lista de características separadas por coma
  List<String> get caracteristicasList =>
      caracteristicas.split(',').map((e) => e.trim()).toList();

  @override
  String toString() =>
      'AndroidVersion(id: $id, nombre: $nombre, fecha: $fecha)';
}
