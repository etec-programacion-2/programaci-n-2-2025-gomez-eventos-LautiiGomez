package model

data class Recomendacion(
    val usuarioId: String,
    val eventoId: String,
    val puntuacion: Int // 1 a 5
)