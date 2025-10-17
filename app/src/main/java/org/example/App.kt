package org.example

import service.UsuarioService
import service.EventoService
import org.example.CliController

fun main() {
    val usuarioService = UsuarioService()
    val eventoService = EventoService()
    val cliController = CliController(usuarioService, eventoService)
    cliController.iniciar()
}