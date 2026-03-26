package repository

import model.Evento
import model.Asistencia
import model.Recomendacion

interface IEventoRepository {
    fun guardar(evento: Evento): Evento
    fun actualizar(evento: Evento): Boolean
    fun obtenerTodos(): List<Evento>
    fun obtenerPorId(id: String): Evento?
    fun obtenerPorUbicacion(ubicacion: String): List<Evento>
    fun obtenerPorPrecio(precioMaximo: Double): List<Evento>
    fun obtenerGratuitos(): List<Evento>
    fun guardarAsistencia(asistencia: Asistencia): Boolean
    fun obtenerAsistencias(): List<Asistencia>
    fun existeAsistencia(usuarioId: String, eventoId: String): Boolean
    fun guardarRecomendacion(recomendacion: Recomendacion): Boolean
    fun obtenerRecomendaciones(eventoId: String): List<Recomendacion>
    fun promedioRecomendaciones(eventoId: String): Double
}