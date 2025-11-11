package org.example

import service.UsuarioService
import service.EventoService
import service.IUsuarioService
import service.IEventoService
import repository.UsuarioRepositoryJson
import repository.EventoRepositoryJson

/**
 * Clase responsable de la configuración e inicialización de repositorios y servicios.
 * Implementa el patrón Factory para la creación de dependencias.
 */
class RepositorioConfig {
    private val usuarioRepository = UsuarioRepositoryJson()
    private val eventoRepository = EventoRepositoryJson()

    fun obtenerUsuarioService(): IUsuarioService {
        return UsuarioService(usuarioRepository)
    }

    fun obtenerEventoService(): IEventoService {
        return EventoService(eventoRepository)
    }
}