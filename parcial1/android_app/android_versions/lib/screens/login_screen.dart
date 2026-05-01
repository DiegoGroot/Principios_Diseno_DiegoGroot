import 'package:flutter/material.dart';
import '../models/user.dart';
import '../services/user_service.dart';
import 'home_screen.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nombreCtrl = TextEditingController();
  final _correoCtrl = TextEditingController();
  final _contrasenaCtrl = TextEditingController();
  bool _loading = false;
  bool _obscurePassword = true;
  bool _isLoginMode = true; // true = login, false = registro

  Future<void> _submit() async {
    if (!_formKey.currentState!.validate()) return;
    setState(() => _loading = true);

    try {
      User user;
      if (_isLoginMode) {
        user = await UserService.login(
          _correoCtrl.text.trim(),
          _contrasenaCtrl.text.trim(),
        );
      } else {
        user = await UserService.register(
          _nombreCtrl.text.trim(),
          _correoCtrl.text.trim(),
          _contrasenaCtrl.text.trim(),
        );
      }

      if (!mounted) return;
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (_) => HomeScreen(currentUser: user)),
      );
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('$e'), backgroundColor: Colors.redAccent),
      );
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  @override
  void dispose() {
    _nombreCtrl.dispose();
    _correoCtrl.dispose();
    _contrasenaCtrl.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF0F0F1A),
      body: SafeArea(
        child: Center(
          child: SingleChildScrollView(
            padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                const SizedBox(height: 32),
                const Icon(Icons.favorite, size: 72, color: Color(0xFF3DDC84)),
                const SizedBox(height: 22),
                Text(
                  _isLoginMode ? 'Iniciar sesión' : 'Crear cuenta',
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    color: Colors.white,
                    fontSize: 28,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const SizedBox(height: 8),
                Text(
                  _isLoginMode
                      ? 'Ingresa tu correo y contraseña'
                      : 'Registra tu usuario para acceder',
                  textAlign: TextAlign.center,
                  style: const TextStyle(color: Color(0xFF6272A4), fontSize: 14),
                ),
                const SizedBox(height: 32),
                Form(
                  key: _formKey,
                  child: Column(
                    children: [
                      if (!_isLoginMode)
                        _field(_nombreCtrl, 'Nombre', 'Diego Groot', Icons.person),
                      _field(_correoCtrl, 'Correo', 'diego@ejemplo.com', Icons.email),
                      _passwordField(),
                    ],
                  ),
                ),
                const SizedBox(height: 20),
                ElevatedButton(
                  onPressed: _loading ? null : _submit,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: const Color(0xFF3DDC84),
                    foregroundColor: const Color(0xFF0F0F1A),
                    minimumSize: const Size.fromHeight(50),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(14),
                    ),
                  ),
                  child: _loading
                      ? const CircularProgressIndicator(color: Color(0xFF0F0F1A))
                      : Text(
                          _isLoginMode ? 'Iniciar sesión' : 'Registrarse',
                          style: const TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 16),
                        ),
                ),
                const SizedBox(height: 16),
                TextButton(
                  onPressed: () {
                    setState(() {
                      _isLoginMode = !_isLoginMode;
                      _formKey.currentState?.reset();
                    });
                  },
                  child: Text(
                    _isLoginMode
                        ? '¿No tienes cuenta? Regístrate'
                        : '¿Ya tienes cuenta? Inicia sesión',
                    style: const TextStyle(color: Color(0xFF3DDC84)),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _field(TextEditingController controller, String label, String hint,
      IconData icon) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 18),
      child: TextFormField(
        controller: controller,
        style: const TextStyle(color: Colors.white),
        decoration: InputDecoration(
          labelText: label,
          hintText: hint,
          labelStyle: const TextStyle(color: Color(0xFF6272A4)),
          hintStyle: const TextStyle(color: Color(0xFF3D3D5C)),
          prefixIcon: Icon(icon, color: const Color(0xFF3DDC84)),
          filled: true,
          fillColor: const Color(0xFF1A0A2E),
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(14),
            borderSide: const BorderSide(color: Color(0xFF3D1F6E)),
          ),
          enabledBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(14),
            borderSide: const BorderSide(color: Color(0xFF3D1F6E)),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(14),
            borderSide: const BorderSide(color: Color(0xFF3DDC84), width: 1.5),
          ),
        ),
        validator: (v) =>
            (v == null || v.trim().isEmpty) ? 'Campo requerido' : null,
      ),
    );
  }

  Widget _passwordField() {
    return Padding(
      padding: const EdgeInsets.only(bottom: 18),
      child: TextFormField(
        controller: _contrasenaCtrl,
        obscureText: _obscurePassword,
        style: const TextStyle(color: Colors.white),
        decoration: InputDecoration(
          labelText: 'Contraseña',
          hintText: 'Password123',
          labelStyle: const TextStyle(color: Color(0xFF6272A4)),
          hintStyle: const TextStyle(color: Color(0xFF3D3D5C)),
          prefixIcon: const Icon(Icons.lock, color: Color(0xFF3DDC84)),
          suffixIcon: IconButton(
            icon: Icon(
              _obscurePassword ? Icons.visibility_off : Icons.visibility,
              color: const Color(0xFF6272A4),
            ),
            onPressed: () =>
                setState(() => _obscurePassword = !_obscurePassword),
          ),
          filled: true,
          fillColor: const Color(0xFF1A0A2E),
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(14),
            borderSide: const BorderSide(color: Color(0xFF3D1F6E)),
          ),
          enabledBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(14),
            borderSide: const BorderSide(color: Color(0xFF3D1F6E)),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(14),
            borderSide: const BorderSide(color: Color(0xFF3DDC84), width: 1.5),
          ),
        ),
        validator: (v) {
          if (v == null || v.isEmpty) return 'Contraseña requerida';
          if (v.length < 6) return 'Mínimo 6 caracteres';
          return null;
        },
      ),
    );
  }
}