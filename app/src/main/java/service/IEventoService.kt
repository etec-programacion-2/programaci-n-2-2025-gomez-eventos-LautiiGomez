package service

import model.Evento
import model.Recomendacion

interface IEventoService {
    fun crearEvento(evento: Evento): Evento
    fun editarEvento(evento: Evento): Boolean
    fun obtenerEventos(): List<Evento>
    fun obtenerEventoPorId(id: String): Evento?
    fun buscarPorUbicacion(ubicacion: String): List<Evento>
    fun buscarPorPrecio(precioMaximo: Double): List<Evento>
    fun buscarGratuitos(): List<Evento>
    fun confirmarAsistencia(usuarioId: String, eventoId: String): Boolean
    fun recomendarEvento(usuarioId: String, eventoId: String, puntuacion: Int): Boolean
    fun obtenerPromedioRecomendaciones(eventoId: String): Double
}