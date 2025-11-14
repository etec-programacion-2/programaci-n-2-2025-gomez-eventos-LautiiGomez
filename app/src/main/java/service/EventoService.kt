package service

import model.Evento
import model.Asistencia
import model.Recomendacion
import repository.IEventoRepository

class EventoService(
    private val eventoRepository: IEventoRepository
) : IEventoService {

    override fun crearEvento(evento: Evento): Evento {
        return eventoRepository.guardar(evento)
    }

    override fun editarEvento(evento: Evento): Boolean {
        return eventoRepository.actualizar(evento)
    }

    override fun obtenerEventos(): List<Evento> {
        return eventoRepository.obtenerTodos()
    }

    override fun obtenerEventoPorId(id: String): Evento? {
        return eventoRepository.obtenerPorId(id)
    }

    override fun buscarPorUbicacion(ubicacion: String): List<Evento> {
        return eventoRepository.obtenerPorUbicacion(ubicacion)
    }

    override fun buscarPorPrecio(precioMaximo: Double): List<Evento> {
        return eventoRepository.obtenerPorPrecio(precioMaximo)
    }

    override fun buscarGratuitos(): List<Evento> {
        return eventoRepository.obtenerGratuitos()
    }

    override fun confirmarAsistencia(usuarioId: String, eventoId: String): Boolean {
        // Verificar que el evento exista
        val eventoExiste = eventoRepository.obtenerPorId(eventoId) != null
        if (!eventoExiste) return false

        // Intentar guardar la asistencia
        val asistencia = Asistencia(usuarioId, eventoId)
        return eventoRepository.guardarAsistencia(asistencia)
    }

    override fun recomendarEvento(usuarioId: String, eventoId: String, puntuacion: Int): Boolean {
        // Validar puntuación
        if (puntuacion !in 1..5) return false

        // Verificar que el evento exista
        val eventoExiste = eventoRepository.obtenerPorId(eventoId) != null
        if (!eventoExiste) return false

        val recomendacion = Recomendacion(usuarioId, eventoId, puntuacion)
        return eventoRepository.guardarRecomendacion(recomendacion)
    }

    override fun obtenerPromedioRecomendaciones(eventoId: String): Double {
        return eventoRepository.promedioRecomendaciones(eventoId)
    }
}