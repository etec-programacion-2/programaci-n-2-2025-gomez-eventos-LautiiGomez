package service

import model.Evento
import model.Asistencia
import java.util.UUID

class EventoService : IEventoService {
    private val eventos = mutableListOf<Evento>()
    private val asistencias = mutableListOf<Asistencia>()

    override fun crearEvento(evento: Evento): Evento {
        val nuevoEvento = evento.copy(id = UUID.randomUUID().toString())
        eventos.add(nuevoEvento)
        return nuevoEvento
    }

    override fun obtenerEventos(): List<Evento> {
        return eventos.toList()
    }

    override fun confirmarAsistencia(usuarioId: String, eventoId: String): Boolean {
        val eventoExiste = eventos.any { it.id == eventoId }
        if (!eventoExiste) return false
        if (asistencias.any { it.usuarioId == usuarioId && it.eventoId == eventoId }) return false
        asistencias.add(Asistencia(usuarioId, eventoId))
        return true
    }
}