// lib/screens/home_screen.dart

import 'package:flutter/material.dart';
import '../models/android_version.dart';
import '../services/android_service.dart';
import 'detail_screen.dart';
import 'create_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  late Future<List<AndroidVersion>> _future;

  @override
  void initState() {
    super.initState();
    _load();
  }

  void _load() {
    setState(() {
      _future = AndroidService.getAll();
    });
  }

  Future<void> _delete(int id) async {
    await AndroidService.delete(id);
    _load();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF0F0F1A),
      appBar: AppBar(
        backgroundColor: const Color(0xFF1A0A2E),
        elevation: 0,
        title: Row(
          children: [
            Container(
              padding: const EdgeInsets.all(6),
              decoration: BoxDecoration(
                color: const Color(0xFF3DDC84).withOpacity(0.12),
                borderRadius: BorderRadius.circular(8),
              ),
              child: const Icon(Icons.android,
                  color: Color(0xFF3DDC84), size: 22),
            ),
            const SizedBox(width: 10),
            const Text(
              'Android Versions',
              style: TextStyle(
                color: Color(0xFF3DDC84),
                fontWeight: FontWeight.bold,
                fontSize: 18,
              ),
            ),
          ],
        ),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh, color: Color(0xFF6272A4)),
            onPressed: _load,
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: const Color(0xFF3DDC84),
        foregroundColor: const Color(0xFF0F0F1A),
        onPressed: () async {
          final created =
              await Navigator.push(context,
                  MaterialPageRoute(builder: (_) => const CreateScreen()));
          if (created == true) _load();
        },
        child: const Icon(Icons.add),
      ),
      body: FutureBuilder<List<AndroidVersion>>(
        future: _future,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(
              child: CircularProgressIndicator(color: Color(0xFF3DDC84)),
            );
          }
          if (snapshot.hasError) {
            return Center(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  const Icon(Icons.error_outline,
                      color: Colors.redAccent, size: 48),
                  const SizedBox(height: 12),
                  Text('${snapshot.error}',
                      style: const TextStyle(color: Color(0xFF6272A4))),
                  const SizedBox(height: 16),
                  TextButton.icon(
                    onPressed: _load,
                    icon: const Icon(Icons.refresh,
                        color: Color(0xFF3DDC84)),
                    label: const Text('Reintentar',
                        style: TextStyle(color: Color(0xFF3DDC84))),
                  ),
                ],
              ),
            );
          }

          final versions = snapshot.data ?? [];
          if (versions.isEmpty) {
            return const Center(
              child: Text('No hay versiones aún.',
                  style: TextStyle(color: Color(0xFF6272A4))),
            );
          }

          return ListView.builder(
            padding: const EdgeInsets.all(16),
            itemCount: versions.length,
            itemBuilder: (context, i) =>
                _VersionCard(version: versions[i], onDelete: _delete),
          );
        },
      ),
    );
  }
}

// ── Tarjeta individual ───────────────────────────────────────────
class _VersionCard extends StatelessWidget {
  final AndroidVersion version;
  final Future<void> Function(int) onDelete;

  const _VersionCard({required this.version, required this.onDelete});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () => Navigator.push(
        context,
        MaterialPageRoute(builder: (_) => DetailScreen(version: version)),
      ),
      child: Container(
        margin: const EdgeInsets.only(bottom: 14),
        decoration: BoxDecoration(
          color: const Color(0xFF1A0A2E),
          borderRadius: BorderRadius.circular(16),
          border: Border.all(color: const Color(0xFF3D1F6E)),
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // ── Imagen ───────────────────────────────────────────
            ClipRRect(
              borderRadius:
                  const BorderRadius.vertical(top: Radius.circular(16)),
              child: version.urlPhoto.isNotEmpty
                  ? Image.network(
                      version.urlPhoto,
                      height: 140,
                      width: double.infinity,
                      fit: BoxFit.cover,
                      errorBuilder: (_, __, ___) => _placeholder(),
                    )
                  : _placeholder(),
            ),

            Padding(
              padding: const EdgeInsets.all(14),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // ── Nombre + ID ───────────────────────────────
                  Row(
                    children: [
                      Expanded(
                        child: Text(
                          version.nombre,
                          style: const TextStyle(
                            color: Color(0xFF3DDC84),
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                      Container(
                        padding: const EdgeInsets.symmetric(
                            horizontal: 8, vertical: 2),
                        decoration: BoxDecoration(
                          color: const Color(0xFF3DDC84).withOpacity(0.1),
                          borderRadius: BorderRadius.circular(20),
                          border: Border.all(
                              color:
                                  const Color(0xFF3DDC84).withOpacity(0.25)),
                        ),
                        child: Text(
                          'ID ${version.id}',
                          style: const TextStyle(
                              color: Color(0xFF3DDC84), fontSize: 11),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 4),

                  // ── Fecha ─────────────────────────────────────
                  Text(
                    version.fecha,
                    style: const TextStyle(
                        color: Color(0xFF6272A4), fontSize: 12),
                  ),
                  const SizedBox(height: 8),

                  // ── Descripción ───────────────────────────────
                  Text(
                    version.descripcion,
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                    style: const TextStyle(
                        color: Color(0xFFAAAAAA), fontSize: 13, height: 1.5),
                  ),
                  const SizedBox(height: 10),

                  // ── Tags características ──────────────────────
                  Wrap(
                    spacing: 6,
                    runSpacing: 6,
                    children: version.caracteristicasList
                        .take(3)
                        .map((c) => Container(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 8, vertical: 3),
                              decoration: BoxDecoration(
                                color: const Color(0xFF242435),
                                borderRadius: BorderRadius.circular(20),
                                border: Border.all(
                                    color: const Color(0xFF3D3D5C)),
                              ),
                              child: Text(
                                c,
                                style: const TextStyle(
                                    color: Color(0xFF8888AA), fontSize: 11),
                              ),
                            ))
                        .toList(),
                  ),

                  // ── Botón delete ──────────────────────────────
                  Align(
                    alignment: Alignment.centerRight,
                    child: IconButton(
                      icon: const Icon(Icons.delete_outline,
                          color: Color(0xFF6272A4), size: 20),
                      onPressed: () async {
                        final ok = await showDialog<bool>(
                          context: context,
                          builder: (_) => AlertDialog(
                            backgroundColor: const Color(0xFF1A0A2E),
                            title: const Text('Eliminar',
                                style: TextStyle(color: Colors.white)),
                            content: Text(
                                '¿Eliminar "${version.nombre}"?',
                                style: const TextStyle(
                                    color: Color(0xFF6272A4))),
                            actions: [
                              TextButton(
                                  onPressed: () =>
                                      Navigator.pop(context, false),
                                  child: const Text('Cancelar',
                                      style: TextStyle(
                                          color: Color(0xFF6272A4)))),
                              TextButton(
                                  onPressed: () =>
                                      Navigator.pop(context, true),
                                  child: const Text('Eliminar',
                                      style: TextStyle(
                                          color: Colors.redAccent))),
                            ],
                          ),
                        );
                        if (ok == true) await onDelete(version.id);
                      },
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _placeholder() {
    return Container(
      height: 140,
      width: double.infinity,
      color: const Color(0xFF13091F),
      child: const Center(
        child: Icon(Icons.android, color: Color(0xFF3DDC84), size: 50),
      ),
    );
  }
}
