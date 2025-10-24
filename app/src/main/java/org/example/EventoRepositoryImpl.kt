package repository

import model.Evento
import model.Asistencia
import model.Recomendacion
import model.TipoEntrada
import java.util.UUID

class EventoRepositoryImpl : IEventoRepository {
    private val eventos = mutableListOf<Evento>()
    private val asistencias = mutableListOf<Asistencia>()
    private val recomendaciones = mutableListOf<Recomendacion>()

    override fun guardar(evento: Evento): Evento {
        val eventoConId = if (evento.id.isEmpty()) {
            evento.copy(id = UUID.randomUUID().toString())
        } else {
            evento
        }
        eventos.add(eventoConId)
        return eventoConId
    }

    override fun obtenerTodos(): List<Evento> {
        return eventos.toList()
    }

    override fun obtenerPorId(id: String): Evento? {
        return eventos.find { it.id == id }
    }

    override fun guardarAsistencia(asistencia: Asistencia): Boolean {
        if (existeAsistencia(asistencia.usuarioId, asistencia.eventoId)) {
            return false
        }
        asistencias.add(asistencia)
        return true
    }

    override fun obtenerAsistencias(): List<Asistencia> {
        return asistencias.toList()
    }

    override fun existeAsistencia(usuarioId: String, eventoId: String): Boolean {
        return asistencias.any { it.usuarioId == usuarioId && it.eventoId == eventoId }
    }
}