package org.example

import service.IUsuarioService
import service.IEventoService
import view.EventoView
import view.UsuarioView
import model.Usuario
import model.Evento
import model.TipoEntrada
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Scanner

class CliController(
    private val usuarioService: IUsuarioService,
    private val eventoService: IEventoService
) {
    private val scanner = Scanner(System.`in`)
    private val eventoView = EventoView()
    private val usuarioView = UsuarioView()
    private var usuarioActual: Usuario? = null

    fun iniciar() {
        println("=== Buscador de Eventos Locales ===")
        println("Bienvenido al sistema de gestión de eventos\n")

        // Primero el usuario debe iniciar sesión o registrarse
        if (!autenticar()) {
            println("Hasta pronto!")
            scanner.close()
            return
        }

        var continuar = true
        while (continuar) {
            mostrarMenu()
            print("\nIngrese su opción: ")
            val comando = scanner.nextLine().trim()

            continuar = procesarComando(comando)
        }

        scanner.close()
        println("\n¡Hasta pronto, ${usuarioActual?.nombre}!")
    }

    private fun autenticar(): Boolean {
        while (true) {
            println("\n--- Autenticación ---")
            println("1. Iniciar sesión")
            println("2. Registrarse")
            println("3. Salir")
            print("\nIngrese su opción: ")

            when (scanner.nextLine().trim()) {
                "1" -> {
                    if (iniciarSesion()) return true
                }
                "2" -> {
                    registrarUsuario()
                    if (usuarioActual != null) return true
                }
                "3" -> return false
                else -> println("Opción no válida")
            }
        }
    }

    private fun iniciarSesion(): Boolean {
        println("\n=== Iniciar Sesión ===")
        print("Email: ")
        val email = scanner.nextLine().trim()

        if (email.isBlank()) {
            usuarioView.mostrarMensajeError("El email es obligatorio.")
            return false
        }

        val usuario = usuarioService.iniciarSesion(email)
        if (usuario != null) {
            usuarioActual = usuario
            println("\n✓ ¡Bienvenido/a de nuevo, ${usuario.nombre}!")
            return true
        } else {
            usuarioView.mostrarMensajeError("No se encontró un usuario con ese email.")
            return false
        }
    }

    private fun mostrarMenu() {
        println("\n╔════════════════════════════════════════╗")
        println("║         MENÚ PRINCIPAL                 ║")
        println("╠════════════════════════════════════════╣")
        println("║ 1. Crear evento                        ║")
        println("║ 2. Editar evento                       ║")
        println("║ 3. Listar todos los eventos            ║")
        println("║ 4. Buscar eventos por ubicación        ║")
        println("║ 5. Buscar eventos por precio           ║")
        println("║ 6. Confirmar asistencia                ║")
        println("║ 7. Recomendar evento                   ║")
        println("║ 8. Salir                               ║")
        println("╚════════════════════════════════════════╝")
    }

    private fun procesarComando(comando: String): Boolean {
        return when (comando) {
            "1" -> {
                crearEvento()
                true
            }
            "2" -> {
                editarEvento()
                true
            }
            "3" -> {
                listarEventos()
                true
            }
            "4" -> {
                buscarPorUbicacion()
                true
            }
            "5" -> {
                buscarPorPrecio()
                true
            }
            "6" -> {
                confirmarAsistencia()
                true
            }
            "7" -> {
                recomendarEvento()
                true
            }
            "8" -> false
            else -> {
                println("Comando no reconocido. Por favor, intente nuevamente.")
                true
            }
        }
    }

    private fun registrarUsuario() {
        println("\n=== Registrar Usuario ===")

        print("Nombre: ")
        val nombre = scanner.nextLine().trim()

        print("Email: ")
        val email = scanner.nextLine().trim()

        if (nombre.isBlank() || email.isBlank()) {
            usuarioView.mostrarMensajeError("El nombre y el email son obligatorios.")
            return
        }

        val usuario = Usuario(
            id = "",
            nombre = nombre,
            email = email
        )

        val usuarioRegistrado = usuarioService.registrarUsuario(usuario)
        usuarioActual = usuarioRegistrado

        usuarioView.mostrarUsuarioRegistrado(usuarioRegistrado)
    }

    private fun crearEvento() {
        println("\n=== Crear Evento ===")

        print("Nombre del evento: ")
        val nombre = scanner.nextLine().trim()

        print("Descripción: ")
        val descripcion = scanner.nextLine().trim()

        print("Fecha y hora (formato: dd/MM/yyyy HH:mm): ")
        val fechaStr = scanner.nextLine().trim()

        print("Ubicación: ")
        val ubicacion = scanner.nextLine().trim()

        print("Categoría: ")
        val categoria = scanner.nextLine().trim()

        println("\nTipo de entrada:")
        println("1. Gratis")
        println("2. Pago")
        print("Selección: ")
        val tipoEntradaOpcion = scanner.nextLine().trim()

        var tipoEntrada = TipoEntrada.GRATIS
        var precio = 0.0

        when (tipoEntradaOpcion) {
            "1" -> tipoEntrada = TipoEntrada.GRATIS
            "2" -> {
                tipoEntrada = TipoEntrada.PAGO
                print("Ingrese el precio de la entrada: $")
                try {
                    precio = scanner.nextLine().trim().toDouble()
                    if (precio < 0) {
                        eventoView.mostrarMensajeError("El precio no puede ser negativo.")
                        return
                    }
                } catch (e: NumberFormatException) {
                    eventoView.mostrarMensajeError("Precio inválido.")
                    return
                }
            }
            else -> {
                eventoView.mostrarMensajeError("Opción inválida.")
                return
            }
        }

        if (nombre.isBlank() || descripcion.isBlank() || fechaStr.isBlank() || ubicacion.isBlank() || categoria.isBlank()) {
            eventoView.mostrarMensajeError("Todos los campos son obligatorios.")
            return
        }

        val fecha = try {
            LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        } catch (e: DateTimeParseException) {
            eventoView.mostrarMensajeError("Formato de fecha inválido. Use dd/MM/yyyy HH:mm")
            return
        }

        val evento = Evento(
            id = "",
            nombre = nombre,
            descripcion = descripcion,
            fecha = fecha,
            ubicacion = ubicacion,
            creadorId = usuarioActual!!.id,
            categoria = categoria,
            tipoEntrada = tipoEntrada,
            precioEntrada = precio
        )

        val eventoCreado = eventoService.crearEvento(evento)
        eventoView.mostrarEventoCreado(eventoCreado)
    }

    private fun editarEvento() {
        println("\n=== Editar Evento ===")

        val eventos = eventoService.obtenerEventos()
        if (eventos.isEmpty()) {
            eventoView.mostrarMensajeError("No hay eventos para editar.")
            return
        }

        // Mostrar solo eventos creados por el usuario actual
        val misEventos = eventos.filter { it.creadorId == usuarioActual?.id }
        if (misEventos.isEmpty()) {
            eventoView.mostrarMensajeError("No has creado ningún evento aún.")
            return
        }

        println("\nTus eventos:")
        misEventos.forEachIndexed { index, evento ->
            println("${index + 1}. ${evento.nombre} (ID: ${evento.id})")
        }

        print("\nIngrese el ID del evento a editar: ")
        val eventoId = scanner.nextLine().trim()

        val evento = eventoService.obtenerEventoPorId(eventoId)
        if (evento == null || evento.creadorId != usuarioActual?.id) {
            eventoView.mostrarMensajeError("Evento no encontrado o no tienes permiso para editarlo.")
            return
        }

        println("\nEditando: ${evento.nombre}")
        println("Deja en blanco para mantener el valor actual\n")

        print("Nuevo nombre [${evento.nombre}]: ")
        val nombre = scanner.nextLine().trim().ifBlank { evento.nombre }

        print("Nueva descripción [${evento.descripcion}]: ")
        val descripcion = scanner.nextLine().trim().ifBlank { evento.descripcion }

        print("Nueva fecha y hora [${evento.fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}]: ")
        val fechaStr = scanner.nextLine().trim()
        val fecha = if (fechaStr.isBlank()) {
            evento.fecha
        } else {
            try {
                LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            } catch (e: DateTimeParseException) {
                eventoView.mostrarMensajeError("Formato de fecha inválido.")
                return
            }
        }

        print("Nueva ubicación [${evento.ubicacion}]: ")
        val ubicacion = scanner.nextLine().trim().ifBlank { evento.ubicacion }

        print("Nueva categoría [${evento.categoria}]: ")
        val categoria = scanner.nextLine().trim().ifBlank { evento.categoria }

        val eventoActualizado = evento.copy(
            nombre = nombre,
            descripcion = descripcion,
            fecha = fecha,
            ubicacion = ubicacion,
            categoria = categoria
        )

        if (eventoService.editarEvento(eventoActualizado)) {
            eventoView.mostrarMensajeExito("Evento actualizado correctamente!")
            eventoView.mostrarEventoCreado(eventoActualizado)
        } else {
            eventoView.mostrarMensajeError("No se pudo actualizar el evento.")
        }
    }

    private fun listarEventos() {
        println("\n=== Lista de Eventos ===")
        val eventos = eventoService.obtenerEventos()
        eventoView.mostrarEventos(eventos)
    }

    private fun buscarPorUbicacion() {
        println("\n=== Buscar Eventos por Ubicación ===")
        print("Ingrese la ubicación (ej: Mendoza): ")
        val ubicacion = scanner.nextLine().trim()

        if (ubicacion.isBlank()) {
            eventoView.mostrarMensajeError("Debe ingresar una ubicación.")
            return
        }

        val eventos = eventoService.buscarPorUbicacion(ubicacion)
        if (eventos.isEmpty()) {
            println("\nNo se encontraron eventos en '$ubicacion'")
        } else {
            println("\nEventos encontrados en '$ubicacion':")
            eventoView.mostrarEventos(eventos)
        }
    }

    private fun buscarPorPrecio() {
        println("\n=== Buscar Eventos por Precio ===")
        println("1. Ver eventos gratuitos")
        println("2. Buscar por precio máximo")
        print("Selección: ")

        when (scanner.nextLine().trim()) {
            "1" -> {
                val eventos = eventoService.buscarGratuitos()
                if (eventos.isEmpty()) {
                    println("\nNo hay eventos gratuitos disponibles.")
                } else {
                    println("\nEventos gratuitos:")
                    eventoView.mostrarEventos(eventos)
                }
            }
            "2" -> {
                print("Ingrese el precio máximo: $")
                try {
                    val precioMaximo = scanner.nextLine().trim().toDouble()
                    if (precioMaximo < 0) {
                        eventoView.mostrarMensajeError("El precio no puede ser negativo.")
                        return
                    }
                    val eventos = eventoService.buscarPorPrecio(precioMaximo)
                    if (eventos.isEmpty()) {
                        println("\nNo se encontraron eventos con precio menor o igual a $$precioMaximo")
                    } else {
                        println("\nEventos encontrados:")
                        eventoView.mostrarEventos(eventos)
                    }
                } catch (e: NumberFormatException) {
                    eventoView.mostrarMensajeError("Precio inválido.")
                }
            }
            else -> eventoView.mostrarMensajeError("Opción inválida.")
        }
    }

    private fun confirmarAsistencia() {
        println("\n=== Confirmar Asistencia ===")

        val eventos = eventoService.obtenerEventos()
        if (eventos.isEmpty()) {
            eventoView.mostrarMensajeError("No hay eventos disponibles.")
            return
        }

        eventoView.mostrarEventosDisponibles(eventos)

        print("\nIngrese el ID del evento: ")
        val eventoId = scanner.nextLine().trim()

        if (eventoId.isBlank()) {
            eventoView.mostrarMensajeError("Debe ingresar un ID de evento.")
            return
        }

        val resultado = eventoService.confirmarAsistencia(usuarioActual!!.id, eventoId)

        if (resultado) {
            eventoView.mostrarMensajeExito("Asistencia confirmada exitosamente!")
        } else {
            eventoView.mostrarMensajeError("No se pudo confirmar la asistencia. Verifique que el evento exista y no haya confirmado previamente.")
        }
    }

    private fun recomendarEvento() {
        println("\n=== Recomendar Evento ===")

        val eventos = eventoService.obtenerEventos()
        if (eventos.isEmpty()) {
            eventoView.mostrarMensajeError("No hay eventos disponibles para recomendar.")
            return
        }

        eventoView.mostrarEventosDisponibles(eventos)

        print("\nIngrese el ID del evento: ")
        val eventoId = scanner.nextLine().trim()

        if (eventoId.isBlank()) {
            eventoView.mostrarMensajeError("Debe ingresar un ID de evento.")
            return
        }

        val evento = eventoService.obtenerEventoPorId(eventoId)
        if (evento == null) {
            eventoView.mostrarMensajeError("Evento no encontrado.")
            return
        }

        print("\nCalifica este evento del 1 al 5: ")
        try {
            val puntuacion = scanner.nextLine().trim().toInt()
            if (puntuacion !in 1..5) {
                eventoView.mostrarMensajeError("La puntuación debe estar entre 1 y 5.")
                return
            }

            val resultado = eventoService.recomendarEvento(usuarioActual!!.id, eventoId, puntuacion)
            if (resultado) {
                eventoView.mostrarMensajeExito("¡Gracias por tu recomendación!")
                val promedio = eventoService.obtenerPromedioRecomendaciones(eventoId)
                eventoView.mostrarEventoConPromedio(evento, promedio)
            } else {
                eventoView.mostrarMensajeError("No se pudo guardar la recomendación.")
            }
        } catch (e: NumberFormatException) {
            eventoView.mostrarMensajeError("Debe ingresar un número válido.")
        }
    }
}