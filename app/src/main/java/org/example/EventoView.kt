package view

import model.Evento
import model.TipoEntrada
import java.time.format.DateTimeFormatter

class EventoView {
    private val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    fun mostrarEventos(eventos: List<Evento>) {
        if (eventos.isEmpty()) {
            println("No hay eventos registrados.")
            return
        }

        println("\n╔════════════════════════════════════════════════════════════════════════════╗")
        println("║                           LISTA DE EVENTOS                                 ║")
        println("╚════════════════════════════════════════════════════════════════════════════╝")

        eventos.forEachIndexed { index, evento ->
            println("\n┌─── Evento ${index + 1} ${"─".repeat(maxOf(0, 68 - (index + 1).toString().length))}")
            println("│ ID: ${evento.id}")
            println("│ Nombre: ${evento.nombre}")
            println("│ Descripción: ${evento.descripcion}")
            println("│ Fecha: ${evento.fecha.format(formatoFecha)}")
            println("│ Ubicación: ${evento.ubicacion}")
            println("│ Categoría: ${evento.categoria}")
            println("│ Entrada: ${formatearEntrada(evento)}")
            println("│ Creador ID: ${evento.creadorId}")
            println("└${"─".repeat(77)}")
        }

        println("\nTotal de eventos: ${eventos.size}")
    }

    fun mostrarEventoCreado(evento: Evento) {
        println("\n╔════════════════════════════════════════════════════════════════════════════╗")
        println("║                    ✓ EVENTO CREADO EXITOSAMENTE                           ║")
        println("╚════════════════════════════════════════════════════════════════════════════╝")
        println("\n┌─── Detalles del Evento ${"─".repeat(53)}")
        println("│ ID: ${evento.id}")
        println("│ Nombre: ${evento.nombre}")
        println("│ Descripción: ${evento.descripcion}")
        println("│ Fecha: ${evento.fecha.format(formatoFecha)}")
        println("│ Ubicación: ${evento.ubicacion}")
        println("│ Categoría: ${evento.categoria}")
        println("│ Entrada: ${formatearEntrada(evento)}")
        println("└${"─".repeat(77)}")
    }

    fun mostrarEventoConPromedio(evento: Evento, promedio: Double) {
        println("\n╔════════════════════════════════════════════════════════════════════════════╗")
        println("║                       DETALLES DEL EVENTO                                  ║")
        println("╚════════════════════════════════════════════════════════════════════════════╝")
        println("\n┌─── Detalles del Evento ${"─".repeat(53)}")
        println("│ ID: ${evento.id}")
        println("│ Nombre: ${evento.nombre}")
        println("│ Descripción: ${evento.descripcion}")
        println("│ Fecha: ${evento.fecha.format(formatoFecha)}")
        println("│ Ubicación: ${evento.ubicacion}")
        println("│ Categoría: ${evento.categoria}")
        println("│ Entrada: ${formatearEntrada(evento)}")
        if (promedio > 0) {
            println("│ Calificación promedio: ${"⭐".repeat(promedio.toInt())} (${String.format("%.1f", promedio)}/5)")
        } else {
            println("│ Calificación promedio: Sin calificaciones aún")
        }
        println("└${"─".repeat(77)}")
    }

    fun mostrarEventosDisponibles(eventos: List<Evento>) {
        println("\n╔════════════════════════════════════════════════════════════════════════════╗")
        println("║                        EVENTOS DISPONIBLES                                 ║")
        println("╚════════════════════════════════════════════════════════════════════════════╝\n")

        eventos.forEachIndexed { index, evento ->
            val entrada = formatearEntrada(evento)
            println("${index + 1}. ${evento.nombre} - ${evento.fecha.format(formatoFecha)}")
            println("   📍 ${evento.ubicacion} | 🎫 $entrada | 📂 ${evento.categoria}")
        }
    }

    private fun formatearEntrada(evento: Evento): String {
        return when (evento.tipoEntrada) {
            TipoEntrada.GRATIS -> "GRATIS"
            TipoEntrada.PAGO -> "$${String.format("%.2f", evento.precioEntrada)}"
        }
    }

    fun mostrarMensajeExito(mensaje: String) {
        println("\n✓ $mensaje")
    }

    fun mostrarMensajeError(mensaje: String) {
        println("\n✗ Error: $mensaje")
    }
}