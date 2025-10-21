package org.example

import service.UsuarioService
import service.EventoService
import repository.UsuarioRepositoryJson
import repository.EventoRepositoryJson
import org.example.CliController

fun main() {
    // Capa de Repositorio (Persistencia en JSON)
    val usuarioRepository = UsuarioRepositoryJson()
    val eventoRepository = EventoRepositoryJson()

    // Capa de Servicio (Lógica de Negocio)
    val usuarioService = UsuarioService(usuarioRepository)
    val eventoService = EventoService(eventoRepository)

    // Capa de Presentación (Controlador)
    val cliController = CliController(usuarioService, eventoService)

    println("📁 Cargando datos desde archivos JSON...")
    cliController.iniciar()
}