package repository

import model.Evento
import model.Asistencia

interface IEventoRepository {
    fun guardar(evento: Evento): Evento
    fun obtenerTodos(): List<Evento>
    fun obtenerPorId(id: String): Evento?
    fun guardarAsistencia(asistencia: Asistencia): Boolean
    fun obtenerAsistencias(): List<Asistencia>
    fun existeAsistencia(usuarioId: String, eventoId: String): Boolean
}