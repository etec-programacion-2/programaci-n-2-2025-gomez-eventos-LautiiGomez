package org.example

import service.UsuarioService
import service.EventoService
import repository.UsuarioRepositoryJson
import repository.EventoRepositoryJson

fun main() {
    val repositorioConfig = RepositorioConfig()
    val controlador = CliController(
        repositorioConfig.obtenerUsuarioService(),
        repositorioConfig.obtenerEventoService()
    )
    controlador.iniciar()
}