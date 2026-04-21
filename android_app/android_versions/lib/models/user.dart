class User {
  final int id;
  final String nombre;
  final String correo;
  final String contrasena;

  User({
    required this.id,
    required this.nombre,
    required this.correo,
    required this.contrasena,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'] as int,
      nombre: json['nombre'] as String,
      correo: json['correo'] as String,
      contrasena: json['contrasena'] as String,
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'nombre': nombre,
    'correo': correo,
    'contrasena': contrasena,
  };

  User copyWith({String? nombre, String? correo, String? contrasena}) {
    return User(
      id: id,
      nombre: nombre ?? this.nombre,
      correo: correo ?? this.correo,
      contrasena: contrasena ?? this.contrasena,
    );
  }
}