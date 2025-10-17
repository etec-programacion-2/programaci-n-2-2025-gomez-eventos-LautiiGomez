package org.example

import service.IUsuarioService
import service.IEventoService
import model.Usuario
import model.Evento
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Scanner

class CliController(
    private val usuarioService: IUsuarioService,
    private val eventoService: IEventoService
) {
    private val scanner = Scanner(System.`in`)
    private var usuarioActual: Usuario? = null

    fun iniciar() {
        println("=== Buscador de Eventos Locales ===")
        println("Bienvenido al sistema de gestión de eventos\n")

        var continuar = true
        while (continuar) {
            mostrarMenu()
            print("\nIngrese su opción: ")
            val comando = scanner.nextLine().trim().lowercase()

            continuar = procesarComando(comando)
        }

        scanner.close()
        println("\n¡Hasta pronto!")
    }

    private fun mostrarMenu() {
        println("\n--- Menú Principal ---")
        println("1. Registrar usuario")
        println("2. Crear evento")
        println("3. Listar eventos")
        println("4. Confirmar asistencia")
        println("5. Salir")
        println("----------------------")
    }

    private fun procesarComando(comando: String): Boolean {
        return when (comando) {
            "1", "registrar usuario" -> {
                registrarUsuario()
                true
            }
            "2", "crear evento" -> {
                crearEvento()
                true
            }
            "3", "listar eventos" -> {
                listarEventos()
                true
            }
            "4", "confirmar asistencia" -> {
                confirmarAsistencia()
                true
            }
            "5", "salir" -> {
                false
            }
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
            println("Error: El nombre y el email son obligatorios.")
            return
        }

        val usuario = Usuario(
            id = "", // El servicio generará el ID
            nombre = nombre,
            email = email
        )

        val usuarioRegistrado = usuarioService.registrarUsuario(usuario)
        usuarioActual = usuarioRegistrado

        println("\n✓ Usuario registrado exitosamente!")
        println("ID: ${usuarioRegistrado.id}")
        println("Nombre: ${usuarioRegistrado.nombre}")
        println("Email: ${usuarioRegistrado.email}")
    }

    private fun crearEvento() {
        println("\n=== Crear Evento ===")

        if (usuarioActual == null) {
            println("Error: Debe registrarse primero para crear eventos.")
            return
        }

        print("Nombre del evento: ")
        val nombre = scanner.nextLine().trim()

        print("Descripción: ")
        val descripcion = scanner.nextLine().trim()

        print("Fecha y hora (formato: dd/MM/yyyy HH:mm): ")
        val fechaStr = scanner.nextLine().trim()

        print("Ubicación: ")
        val ubicacion = scanner.nextLine().trim()

        if (nombre.isBlank() || descripcion.isBlank() || fechaStr.isBlank() || ubicacion.isBlank()) {
            println("Error: Todos los campos son obligatorios.")
            return
        }

        val fecha = try {
            LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        } catch (e: DateTimeParseException) {
            println("Error: Formato de fecha inválido. Use dd/MM/yyyy HH:mm")
            return
        }

        val evento = Evento(
            id = "", // El servicio generará el ID
            nombre = nombre,
            descripcion = descripcion,
            fecha = fecha,
            ubicacion = ubicacion,
            creadorId = usuarioActual!!.id
        )

        val eventoCreado = eventoService.crearEvento(evento)

        println("\n✓ Evento creado exitosamente!")
        println("ID: ${eventoCreado.id}")
        println("Nombre: ${eventoCreado.nombre}")
        println("Fecha: ${eventoCreado.fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")
        println("Ubicación: ${eventoCreado.ubicacion}")
    }

    private fun listarEventos() {
        println("\n=== Lista de Eventos ===")

        val eventos = eventoService.obtenerEventos()

        if (eventos.isEmpty()) {
            println("No hay eventos registrados.")
            return
        }

        eventos.forEachIndexed { index, evento ->
            println("\n--- Evento ${index + 1} ---")
            println("ID: ${evento.id}")
            println("Nombre: ${evento.nombre}")
            println("Descripción: ${evento.descripcion}")
            println("Fecha: ${evento.fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")
            println("Ubicación: ${evento.ubicacion}")
            println("Creador ID: ${evento.creadorId}")
        }

        println("\nTotal de eventos: ${eventos.size}")
    }

    private fun confirmarAsistencia() {
        println("\n=== Confirmar Asistencia ===")

        if (usuarioActual == null) {
            println("Error: Debe registrarse primero para confirmar asistencia.")
            return
        }

        val eventos = eventoService.obtenerEventos()
        if (eventos.isEmpty()) {
            println("No hay eventos disponibles.")
            return
        }

        println("\nEventos disponibles:")
        eventos.forEachIndexed { index, evento ->
            println("${index + 1}. ${evento.nombre} - ${evento.fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")
        }

        print("\nIngrese el ID del evento: ")
        val eventoId = scanner.nextLine().trim()

        if (eventoId.isBlank()) {
            println("Error: Debe ingresar un ID de evento.")
            return
        }

        val resultado = eventoService.confirmarAsistencia(usuarioActual!!.id, eventoId)

        if (resultado) {
            println("\n✓ Asistencia confirmada exitosamente!")
        } else {
            println("\nError: No se pudo confirmar la asistencia. Verifique que el evento exista y no haya confirmado previamente.")
        }
    }
}