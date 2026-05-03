import 'package:flutter/material.dart';
import '../models/android_version.dart';
import '../services/android_service.dart';

class DetailScreen extends StatefulWidget {
  final AndroidVersion version;
  const DetailScreen({super.key, required this.version});

  @override
  State<DetailScreen> createState() => _DetailScreenState();
}

class _DetailScreenState extends State<DetailScreen> {
  final TextEditingController _comentarioCtrl = TextEditingController();
  List<Map<String, dynamic>> _comentarios = [];
  Map<String, dynamic> _reacciones = {};
  bool _loadingComentarios = true;
  bool _loadingReacciones = true;

  final List<Map<String, String>> _tiposReaccion = [
    {'tipo': 'like', 'emoji': '👍'},
    {'tipo': 'love', 'emoji': '❤️'},
    {'tipo': 'haha', 'emoji': '😂'},
    {'tipo': 'wow', 'emoji': '😮'},
    {'tipo': 'sad', 'emoji': '😢'},
  ];

  @override
  void initState() {
    super.initState();
    if (widget.version.id != null) {
      _cargarDatos();
    }
  }

  Future<void> _cargarDatos() async {
    await Future.wait([_cargarComentarios(), _cargarReacciones()]);
  }

  Future<void> _cargarComentarios() async {
    try {
      final data = await AndroidService.getComentarios(widget.version.id!);
      if (mounted)
        setState(() {
          _comentarios = data;
          _loadingComentarios = false;
        });
    } catch (_) {
      if (mounted)
        setState(() {
          _loadingComentarios = false;
        });
    }
  }

  Future<void> _cargarReacciones() async {
    try {
      final data = await AndroidService.getConteoReacciones(widget.version.id!);
      if (mounted)
        setState(() {
          _reacciones = data;
          _loadingReacciones = false;
        });
    } catch (_) {
      if (mounted)
        setState(() {
          _loadingReacciones = false;
        });
    }
  }

  Future<void> _enviarComentario() async {
    final texto = _comentarioCtrl.text.trim();
    if (texto.isEmpty || widget.version.id == null) return;
    try {
      await AndroidService.agregarComentario(widget.version.id!, texto);
      _comentarioCtrl.clear();
      await _cargarComentarios();
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Error: $e'), backgroundColor: Colors.red),
        );
      }
    }
  }

  Future<void> _reaccionar(String tipo) async {
    if (widget.version.id == null) return;
    try {
      await AndroidService.agregarReaccion(widget.version.id!, tipo);
      await _cargarReacciones();
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Error: $e'), backgroundColor: Colors.red),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF0F0F1A),
      body: CustomScrollView(
        slivers: [
          SliverAppBar(
            expandedHeight: 220,
            pinned: true,
            backgroundColor: const Color(0xFF1A0A2E),
            leading: IconButton(
              icon: const Icon(Icons.arrow_back_ios, color: Color(0xFF3DDC84)),
              onPressed: () => Navigator.pop(context),
            ),
            flexibleSpace: FlexibleSpaceBar(
              background: widget.version.urlPhoto.isNotEmpty
                  ? Image.network(
                      widget.version.urlPhoto,
                      fit: BoxFit.cover,
                      errorBuilder: (_, __, ___) => _gradientPlaceholder(),
                    )
                  : _gradientPlaceholder(),
            ),
          ),

          SliverToBoxAdapter(
            child: Padding(
              padding: const EdgeInsets.all(20),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // ── Nombre + badge ID ──────────────────────────
                  Row(
                    children: [
                      Expanded(
                        child: Text(
                          widget.version.nombre,
                          style: const TextStyle(
                            color: Color(0xFF3DDC84),
                            fontSize: 24,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                      Container(
                        padding: const EdgeInsets.symmetric(
                          horizontal: 12,
                          vertical: 4,
                        ),
                        decoration: BoxDecoration(
                          color: const Color(
                            0xFF3DDC84,
                          ).withValues(alpha: 0.12),
                          borderRadius: BorderRadius.circular(20),
                          border: Border.all(
                            color: const Color(
                              0xFF3DDC84,
                            ).withValues(alpha: 0.3),
                          ),
                        ),
                        child: Text(
                          'ID ${widget.version.id ?? "-"}',
                          style: const TextStyle(
                            color: Color(0xFF3DDC84),
                            fontSize: 12,
                          ),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 6),

                  // ── Fecha ──────────────────────────────────────
                  Row(
                    children: [
                      const Icon(
                        Icons.calendar_today,
                        size: 14,
                        color: Color(0xFF6272A4),
                      ),
                      const SizedBox(width: 6),
                      Text(
                        widget.version.fecha,
                        style: const TextStyle(
                          color: Color(0xFF6272A4),
                          fontSize: 13,
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 20),

                  // ── Descripción ────────────────────────────────
                  _sectionTitle('Descripción'),
                  const SizedBox(height: 8),
                  Text(
                    widget.version.descripcion,
                    style: const TextStyle(
                      color: Color(0xFFBBBBCC),
                      fontSize: 14,
                      height: 1.7,
                    ),
                  ),
                  const SizedBox(height: 24),

                  // ── Características ────────────────────────────
                  _sectionTitle('Características'),
                  const SizedBox(height: 12),
                  ...widget.version.caracteristicasList.map(
                    (c) => _featureItem(c),
                  ),
                  const SizedBox(height: 24),

                  // ── Reacciones ─────────────────────────────────
                  _sectionTitle('Reacciones'),
                  const SizedBox(height: 12),
                  _loadingReacciones
                      ? const Center(
                          child: CircularProgressIndicator(
                            color: Color(0xFF3DDC84),
                          ),
                        )
                      : Row(
                          mainAxisAlignment: MainAxisAlignment.spaceAround,
                          children: _tiposReaccion.map((r) {
                            final count = _reacciones[r['tipo']] ?? 0;
                            return GestureDetector(
                              onTap: () => _reaccionar(r['tipo']!),
                              child: Column(
                                children: [
                                  Text(
                                    r['emoji']!,
                                    style: const TextStyle(fontSize: 28),
                                  ),
                                  const SizedBox(height: 4),
                                  Text(
                                    '$count',
                                    style: const TextStyle(
                                      color: Color(0xFF3DDC84),
                                      fontSize: 13,
                                    ),
                                  ),
                                ],
                              ),
                            );
                          }).toList(),
                        ),
                  const SizedBox(height: 24),

                  // ── Comentarios ────────────────────────────────
                  _sectionTitle('Comentarios'),
                  const SizedBox(height: 12),

                  // Input
                  Container(
                    decoration: BoxDecoration(
                      color: const Color(0xFF1A1A2E),
                      borderRadius: BorderRadius.circular(12),
                      border: Border.all(
                        color: const Color(0xFF3DDC84).withValues(alpha: 0.3),
                      ),
                    ),
                    child: Row(
                      children: [
                        Expanded(
                          child: TextField(
                            controller: _comentarioCtrl,
                            style: const TextStyle(color: Colors.white),
                            decoration: const InputDecoration(
                              hintText: 'Escribe un comentario...',
                              hintStyle: TextStyle(color: Color(0xFF6272A4)),
                              border: InputBorder.none,
                              contentPadding: EdgeInsets.symmetric(
                                horizontal: 16,
                                vertical: 12,
                              ),
                            ),
                          ),
                        ),
                        IconButton(
                          icon: const Icon(
                            Icons.send,
                            color: Color(0xFF3DDC84),
                          ),
                          onPressed: _enviarComentario,
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 12),

                  // Lista comentarios
                  _loadingComentarios
                      ? const Center(
                          child: CircularProgressIndicator(
                            color: Color(0xFF3DDC84),
                          ),
                        )
                      : _comentarios.isEmpty
                      ? const Center(
                          child: Text(
                            'No hay comentarios aún. ¡Sé el primero!',
                            style: TextStyle(
                              color: Color(0xFF6272A4),
                              fontSize: 13,
                            ),
                          ),
                        )
                      : Column(
                          children: _comentarios
                              .map((c) => _comentarioItem(c))
                              .toList(),
                        ),

                  const SizedBox(height: 32),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _comentarioItem(Map<String, dynamic> comentario) {
    return Container(
      margin: const EdgeInsets.only(bottom: 10),
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: const Color(0xFF1A1A2E),
        borderRadius: BorderRadius.circular(10),
        border: Border.all(
          color: const Color(0xFF3DDC84).withValues(alpha: 0.15),
        ),
      ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Icon(Icons.comment, color: Color(0xFF3DDC84), size: 16),
          const SizedBox(width: 8),
          Expanded(
            child: Text(
              comentario['texto'] ?? '',
              style: const TextStyle(
                color: Color(0xFFCCCCDD),
                fontSize: 14,
                height: 1.5,
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _gradientPlaceholder() {
    return Container(
      decoration: const BoxDecoration(
        gradient: LinearGradient(
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
          colors: [Color(0xFF1A0A2E), Color(0xFF0E0618)],
        ),
      ),
      child: const Center(
        child: Icon(Icons.android, size: 80, color: Color(0xFF3DDC84)),
      ),
    );
  }

  Widget _sectionTitle(String title) {
    return Text(
      title,
      style: const TextStyle(
        color: Color(0xFFF5E642),
        fontSize: 13,
        fontWeight: FontWeight.bold,
        letterSpacing: 1.2,
      ),
    );
  }

  Widget _featureItem(String text) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 10),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Padding(
            padding: EdgeInsets.only(top: 3),
            child: Icon(Icons.arrow_right, color: Color(0xFF3DDC84), size: 18),
          ),
          const SizedBox(width: 6),
          Expanded(
            child: Text(
              text,
              style: const TextStyle(
                color: Color(0xFFCCCCDD),
                fontSize: 14,
                height: 1.5,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
