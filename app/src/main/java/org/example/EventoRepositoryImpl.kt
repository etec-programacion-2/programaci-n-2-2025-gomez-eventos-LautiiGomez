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

    override fun actualizar(evento: Evento): Boolean {
        val index = eventos.indexOfFirst { it.id == evento.id }
        if (index == -1) return false

        eventos[index] = evento
        return true
    }

    override fun obtenerTodos(): List<Evento> {
        return eventos.toList()
    }

    override fun obtenerPorId(id: String): Evento? {
        return eventos.find { it.id == id }
    }

    override fun obtenerPorUbicacion(ubicacion: String): List<Evento> {
        return eventos.filter {
            it.ubicacion.contains(ubicacion, ignoreCase = true)
        }
    }

    override fun obtenerPorPrecio(precioMaximo: Double): List<Evento> {
        return eventos.filter {
            it.tipoEntrada == TipoEntrada.GRATIS || it.precioEntrada <= precioMaximo
        }
    }

    override fun obtenerGratuitos(): List<Evento> {
        return eventos.filter { it.tipoEntrada == TipoEntrada.GRATIS }
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

    override fun guardarRecomendacion(recomendacion: Recomendacion): Boolean {
        // Si ya existe una recomendación del mismo usuario para el mismo evento, la reemplazamos
        val index = recomendaciones.indexOfFirst {
            it.usuarioId == recomendacion.usuarioId && it.eventoId == recomendacion.eventoId
        }

        if (index != -1) {
            recomendaciones[index] = recomendacion
        } else {
            recomendaciones.add(recomendacion)
        }

        return true
    }

    override fun obtenerRecomendaciones(eventoId: String): List<Recomendacion> {
        return recomendaciones.filter { it.eventoId == eventoId }
    }

    override fun promedioRecomendaciones(eventoId: String): Double {
        val recs = obtenerRecomendaciones(eventoId)
        if (recs.isEmpty()) return 0.0
        return recs.map { it.puntuacion }.average()
    }
}