package service

import model.Evento
import model.Asistencia
import repository.IEventoRepository

class EventoService(
    private val eventoRepository: IEventoRepository
) : IEventoService {

    override fun crearEvento(evento: Evento): Evento {
        return eventoRepository.guardar(evento)
    }

    override fun obtenerEventos(): List<Evento> {
        return eventoRepository.obtenerTodos()
    }

    override fun confirmarAsistencia(usuarioId: String, eventoId: String): Boolean {
        // Verificar que el evento exista
        val eventoExiste = eventoRepository.obtenerPorId(eventoId) != null
        if (!eventoExiste) return false

        // Intentar guardar la asistencia
        val asistencia = Asistencia(usuarioId, eventoId)
        return eventoRepository.guardarAsistencia(asistencia)
    }
}