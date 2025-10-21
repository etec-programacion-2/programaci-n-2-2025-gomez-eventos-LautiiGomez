package org.example

import service.UsuarioService
import service.EventoService
import repository.UsuarioRepositoryImpl
import repository.EventoRepositoryImpl
import org.example.CliController

fun main() {
    // Capa de Repositorio (Persistencia)
    val usuarioRepository = UsuarioRepositoryImpl()
    val eventoRepository = EventoRepositoryImpl()

    // Capa de Servicio (Lógica de Negocio)
    val usuarioService = UsuarioService(usuarioRepository)
    val eventoService = EventoService(eventoRepository)

    // Capa de Presentación (Controlador)
    val cliController = CliController(usuarioService, eventoService)

    cliController.iniciar()
}