package model

import java.time.LocalDateTime

data class Evento(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val fecha: LocalDateTime,
    val ubicacion: String,
    val creadorId: String,
    val categoria: String,
    val tipoEntrada: TipoEntrada,
    val precioEntrada: Double = 0.0
)

enum class TipoEntrada {
    GRATIS,
    PAGO
}