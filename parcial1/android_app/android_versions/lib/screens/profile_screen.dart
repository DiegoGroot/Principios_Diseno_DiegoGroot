import 'package:flutter/material.dart';
import '../models/user.dart';
import '../services/user_service.dart';
import 'login_screen.dart';

class ProfileScreen extends StatefulWidget {
  final User user;
  const ProfileScreen({super.key, required this.user});

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  late User _user;
  bool _showPassword = false;
  bool _showConfirmPassword = false;

  @override
  void initState() {
    super.initState();
    _user = widget.user;
  }

  // ── Editar usuario ──────────────────────────────────────────────
  Future<void> _edit() async {
    final nombreCtrl = TextEditingController(text: _user.nombre);
    final correoCtrl = TextEditingController(text: _user.correo);
    final contraCtrl = TextEditingController(text: _user.contrasena);
    final contraConfirmCtrl = TextEditingController(text: _user.contrasena);
    final formKey = GlobalKey<FormState>();

    final confirmed = await showDialog<bool>(
      context: context,
      builder: (_) => StatefulBuilder(
        builder: (context, setStateDialog) => AlertDialog(
          backgroundColor: const Color(0xFF1A0A2E),
          title: const Text('Editar cuenta',
              style: TextStyle(color: Color(0xFF3DDC84))),
          content: Form(
            key: formKey,
            child: SingleChildScrollView(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  _dialogField(nombreCtrl, 'Nombre', Icons.person),
                  const SizedBox(height: 12),
                  _dialogField(correoCtrl, 'Correo', Icons.email),
                  const SizedBox(height: 12),
                  _passwordField(
                    contraCtrl,
                    'Contraseña',
                    _showPassword,
                    (value) => setStateDialog(() => _showPassword = value),
                  ),
                  const SizedBox(height: 12),
                  _passwordField(
                    contraConfirmCtrl,
                    'Confirmar contraseña',
                    _showConfirmPassword,
                    (value) => setStateDialog(() => _showConfirmPassword = value),
                    confirmPassword: contraCtrl.text,
                  ),
                ],
              ),
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context, false),
              child: const Text('Cancelar',
                  style: TextStyle(color: Color(0xFF6272A4))),
            ),
            ElevatedButton(
              onPressed: () {
                if (formKey.currentState!.validate()) {
                  Navigator.pop(context, true);
                }
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color(0xFF3DDC84),
                foregroundColor: const Color(0xFF0F0F1A),
              ),
              child: const Text('Guardar'),
            ),
          ],
        ),
      ),
    );

    if (confirmed != true) return;

    try {
      final updated = await UserService.update(
        User(
          id: _user.id,
          nombre: nombreCtrl.text.trim(),
          correo: correoCtrl.text.trim(),
          contrasena: contraCtrl.text.trim(),
        ),
      );
      setState(() => _user = updated);
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('✅ Usuario actualizado'),
          backgroundColor: Color(0xFF3DDC84),
        ),
      );
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e'), backgroundColor: Colors.redAccent),
      );
    }
  }

  // ── Eliminar usuario ────────────────────────────────────────────
  Future<void> _delete() async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (_) => AlertDialog(
        backgroundColor: const Color(0xFF1A0A2E),
        title: const Text('Eliminar cuenta',
            style: TextStyle(color: Colors.redAccent)),
        content: const Text(
          '¿Estás seguro? Esta acción eliminará tu cuenta y todas tus versiones permanentemente.',
          style: TextStyle(color: Color(0xFF6272A4)),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text('Cancelar',
                style: TextStyle(color: Color(0xFF6272A4))),
          ),
          ElevatedButton(
            onPressed: () => Navigator.pop(context, true),
            style: ElevatedButton.styleFrom(backgroundColor: Colors.redAccent),
            child: const Text('Eliminar',
                style: TextStyle(color: Colors.white)),
          ),
        ],
      ),
    );

    if (confirmed != true) return;

    try {
      await UserService.deleteAccount(_user.id);
      if (!mounted) return;
      Navigator.pushAndRemoveUntil(
        context,
        MaterialPageRoute(builder: (_) => const LoginScreen()),
        (route) => false,
      );
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e'), backgroundColor: Colors.redAccent),
      );
    }
  }

  // ── Cerrar sesión ───────────────────────────────────────────────
  Future<void> _logout() async {
    await UserService.clearUser();
    if (!mounted) return;
    Navigator.pushAndRemoveUntil(
      context,
      MaterialPageRoute(builder: (_) => const LoginScreen()),
      (route) => false,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF0F0F1A),
      appBar: AppBar(
        backgroundColor: const Color(0xFF1A0A2E),
        elevation: 0,
        title: const Text('Configuración',
            style: TextStyle(color: Color(0xFFF5E642))),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios, color: Color(0xFF3DDC84)),
          onPressed: () => Navigator.pop(context, _user),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            // ── Datos del usuario ─────────────────────────────────
            Container(
              padding: const EdgeInsets.all(20),
              decoration: BoxDecoration(
                color: const Color(0xFF1A0A2E),
                borderRadius: BorderRadius.circular(18),
                border: Border.all(color: const Color(0xFF3D1F6E)),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text('Datos del usuario',
                      style: TextStyle(
                          color: Color(0xFF3DDC84),
                          fontSize: 16,
                          fontWeight: FontWeight.bold)),
                  const SizedBox(height: 16),
                  _infoRow(Icons.person, 'Nombre', _user.nombre),
                  const SizedBox(height: 12),
                  _infoRow(Icons.email, 'Correo', _user.correo),
                  const SizedBox(height: 12),
                  _infoRow(Icons.lock, 'Contraseña', '••••••••'),
                ],
              ),
            ),
            const SizedBox(height: 20),

            // ── Editar ────────────────────────────────────────────
            ElevatedButton.icon(
              onPressed: _edit,
              icon: const Icon(Icons.edit, color: Color(0xFF0F0F1A)),
              label: const Text('Editar cuenta'),
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color(0xFF3DDC84),
                foregroundColor: const Color(0xFF0F0F1A),
                minimumSize: const Size.fromHeight(50),
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(14)),
              ),
            ),
            const SizedBox(height: 12),

            // ── Cerrar sesión ─────────────────────────────────────
            ElevatedButton.icon(
              onPressed: _logout,
              icon: const Icon(Icons.logout, color: Color(0xFF0F0F1A)),
              label: const Text('Cerrar sesión'),
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color(0xFF6272A4),
                foregroundColor: const Color(0xFF0F0F1A),
                minimumSize: const Size.fromHeight(50),
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(14)),
              ),
            ),
            const SizedBox(height: 12),

            // ── Eliminar cuenta ───────────────────────────────────
            OutlinedButton.icon(
              onPressed: _delete,
              icon: const Icon(Icons.delete_forever, color: Colors.redAccent),
              label: const Text('Eliminar cuenta',
                  style: TextStyle(color: Colors.redAccent)),
              style: OutlinedButton.styleFrom(
                side: const BorderSide(color: Colors.redAccent),
                minimumSize: const Size.fromHeight(50),
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(14)),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _infoRow(IconData icon, String label, String value) {
    return Row(
      children: [
        Icon(icon, color: const Color(0xFF6272A4), size: 18),
        const SizedBox(width: 10),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(label,
                  style: const TextStyle(
                      color: Color(0xFF6272A4), fontSize: 12)),
              const SizedBox(height: 4),
              Text(value,
                  style: const TextStyle(
                      color: Colors.white,
                      fontSize: 15,
                      fontWeight: FontWeight.w600)),
            ],
          ),
        ),
      ],
    );
  }

  Widget _dialogField(
      TextEditingController ctrl, String label, IconData icon,
      {bool obscure = false}) {
    return TextFormField(
      controller: ctrl,
      obscureText: obscure,
      style: const TextStyle(color: Colors.white),
      decoration: InputDecoration(
        labelText: label,
        labelStyle: const TextStyle(color: Color(0xFF6272A4)),
        prefixIcon: Icon(icon, color: const Color(0xFF3DDC84), size: 20),
        filled: true,
        fillColor: const Color(0xFF0F0F1A),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
          borderSide: const BorderSide(color: Color(0xFF3D1F6E)),
        ),
        enabledBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
          borderSide: const BorderSide(color: Color(0xFF3D1F6E)),
        ),
        focusedBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
          borderSide: const BorderSide(color: Color(0xFF3DDC84), width: 1.5),
        ),
      ),
      validator: (v) =>
          (v == null || v.trim().isEmpty) ? 'Campo requerido' : null,
    );
  }

  Widget _passwordField(
    TextEditingController ctrl,
    String label,
    bool showPassword,
    Function(bool) onShowPasswordChanged, {
    String? confirmPassword,
  }) {
    return TextFormField(
      controller: ctrl,
      obscureText: !showPassword,
      style: const TextStyle(color: Colors.white),
      decoration: InputDecoration(
        labelText: label,
        labelStyle: const TextStyle(color: Color(0xFF6272A4)),
        prefixIcon: Icon(Icons.lock, color: const Color(0xFF3DDC84), size: 20),
        suffixIcon: IconButton(
          icon: Icon(
            showPassword ? Icons.visibility : Icons.visibility_off,
            color: const Color(0xFF6272A4),
          ),
          onPressed: () => onShowPasswordChanged(!showPassword),
        ),
        filled: true,
        fillColor: const Color(0xFF0F0F1A),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
          borderSide: const BorderSide(color: Color(0xFF3D1F6E)),
        ),
        enabledBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
          borderSide: const BorderSide(color: Color(0xFF3D1F6E)),
        ),
        focusedBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
          borderSide: const BorderSide(color: Color(0xFF3DDC84), width: 1.5),
        ),
      ),
      validator: (v) {
        if (v == null || v.trim().isEmpty) {
          return 'Campo requerido';
        }
        if (confirmPassword != null && v.trim() != confirmPassword.trim()) {
          return 'Las contraseñas no coinciden';
        }
        return null;
      },
    );
  }
}