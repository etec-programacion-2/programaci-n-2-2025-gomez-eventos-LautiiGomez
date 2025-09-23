package model

import java.time.LocalDateTime

data class Evento(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val fecha: LocalDateTime,
    val ubicacion: String,
    val creadorId: String
)