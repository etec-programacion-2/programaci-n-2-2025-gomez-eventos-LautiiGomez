package service

import model.Evento

interface IEventoService {
    fun crearEvento(evento: Evento): Evento
    fun obtenerEventos(): List<Evento>
    fun confirmarAsistencia(usuarioId: Int, eventoId: Int): Boolean
}