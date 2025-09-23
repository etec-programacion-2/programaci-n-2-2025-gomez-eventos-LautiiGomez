package org.example

import model.Usuario
import model.Evento
import model.Asistencia
import java.time.LocalDateTime
import java.util.UUID

fun main() {
    val usuario1 = Usuario(id = UUID.randomUUID().toString(), nombre = "Ana Gómez", email = "ana@gmail.com")
    val usuario2 = Usuario(id = UUID.randomUUID().toString(), nombre = "Luis Pérez", email = "luis@gmail.com")

    val evento1 = Evento(
        id = UUID.randomUUID().toString(),
        nombre = "Feria de Tecnología",
        descripcion = "Evento con charlas y exposiciones de tecnología.",
        fecha = LocalDateTime.of(2025, 9, 15, 18, 0),
        ubicacion = "Salón Municipal",
        creadorId = usuario1.id
    )

    val asistencia1 = Asistencia(usuarioId = usuario2.id, eventoId = evento1.id)

    println("Usuarios:")
    println(usuario1)
    println(usuario2)
    println("\nEvento:")
    println(evento1)
    println("\nAsistencia:")
    println(asistencia1)
}