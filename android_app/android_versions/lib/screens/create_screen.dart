// lib/screens/create_screen.dart

import 'package:flutter/material.dart';
import '../models/android_version.dart';
import '../services/android_service.dart';

class CreateScreen extends StatefulWidget {
  const CreateScreen({super.key});

  @override
  State<CreateScreen> createState() => _CreateScreenState();
}

class _CreateScreenState extends State<CreateScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nombreCtrl        = TextEditingController();
  final _fechaCtrl         = TextEditingController();
  final _descripcionCtrl   = TextEditingController();
  final _caracteristicasCtrl = TextEditingController();
  final _urlPhotoCtrl      = TextEditingController();
  bool _loading = false;

  Future<void> _submit() async {
    if (!_formKey.currentState!.validate()) return;
    setState(() => _loading = true);

    try {
      final nueva = AndroidVersion(
        id:              0,
        nombre:          _nombreCtrl.text.trim(),
        fecha:           _fechaCtrl.text.trim(),
        descripcion:     _descripcionCtrl.text.trim(),
        caracteristicas: _caracteristicasCtrl.text.trim(),
        urlPhoto:        _urlPhotoCtrl.text.trim(),
      );
      await AndroidService.create(nueva);
      if (mounted) {
        Navigator.pop(context, true);
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('✅ Versión creada correctamente'),
            backgroundColor: Color(0xFF3DDC84),
          ),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e'),
            backgroundColor: Colors.redAccent),
      );
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  @override
  void dispose() {
    _nombreCtrl.dispose();
    _fechaCtrl.dispose();
    _descripcionCtrl.dispose();
    _caracteristicasCtrl.dispose();
    _urlPhotoCtrl.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF0F0F1A),
      appBar: AppBar(
        backgroundColor: const Color(0xFF1A0A2E),
        title: const Text('Nueva versión',
            style: TextStyle(color: Color(0xFFF5E642))),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios, color: Color(0xFF3DDC84)),
          onPressed: () => Navigator.pop(context),
        ),
        elevation: 0,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              _field(_nombreCtrl, 'Nombre', 'Android 15', Icons.android),
              _field(_fechaCtrl, 'Fecha', '3 Octubre 2024', Icons.calendar_today),
              _field(_descripcionCtrl, 'Descripción', 'Descripción de la versión...',
                  Icons.description, maxLines: 3),
              _field(_caracteristicasCtrl, 'Características',
                  'Feature 1, Feature 2, Feature 3', Icons.list),
              _field(_urlPhotoCtrl, 'URL de imagen (opcional)', 'https://...',
                  Icons.image, required: false),
              const SizedBox(height: 28),
              SizedBox(
                width: double.infinity,
                height: 50,
                child: ElevatedButton(
                  onPressed: _loading ? null : _submit,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: const Color(0xFF3DDC84),
                    foregroundColor: const Color(0xFF0F0F1A),
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(12)),
                  ),
                  child: _loading
                      ? const CircularProgressIndicator(color: Color(0xFF0F0F1A))
                      : const Text('Crear versión',
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 16)),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _field(
    TextEditingController ctrl,
    String label,
    String hint,
    IconData icon, {
    int maxLines = 1,
    bool required = true,
  }) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 16),
      child: TextFormField(
        controller: ctrl,
        maxLines: maxLines,
        style: const TextStyle(color: Colors.white),
        decoration: InputDecoration(
          labelText: label,
          hintText: hint,
          labelStyle: const TextStyle(color: Color(0xFF6272A4)),
          hintStyle: const TextStyle(color: Color(0xFF3D3D5C)),
          prefixIcon: Icon(icon, color: const Color(0xFF3DDC84), size: 20),
          filled: true,
          fillColor: const Color(0xFF1A0A2E),
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
            borderSide: const BorderSide(color: Color(0xFF3D1F6E)),
          ),
          enabledBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
            borderSide: const BorderSide(color: Color(0xFF3D1F6E)),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
            borderSide: const BorderSide(color: Color(0xFF3DDC84), width: 1.5),
          ),
        ),
        validator: required
            ? (v) => (v == null || v.trim().isEmpty) ? 'Campo requerido' : null
            : null,
      ),
    );
  }
}
