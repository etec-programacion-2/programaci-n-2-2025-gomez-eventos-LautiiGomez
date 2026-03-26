package view

import model.Usuario

class UsuarioView {

    fun mostrarUsuarioRegistrado(usuario: Usuario) {
        println("\n╔════════════════════════════════════════════════════════════════════════════╗")
        println("║                  ✓ USUARIO REGISTRADO EXITOSAMENTE                        ║")
        println("╚════════════════════════════════════════════════════════════════════════════╝")
        println("\n┌─── Datos del Usuario ${"─".repeat(56)}")
        println("│ ID: ${usuario.id}")
        println("│ Nombre: ${usuario.nombre}")
        println("│ Email: ${usuario.email}")
        println("└${"─".repeat(77)}")
    }

    fun mostrarUsuario(usuario: Usuario) {
        println("\n┌─── Usuario ${"─".repeat(64)}")
        println("│ ID: ${usuario.id}")
        println("│ Nombre: ${usuario.nombre}")
        println("│ Email: ${usuario.email}")
        println("└${"─".repeat(77)}")
    }

    fun mostrarMensajeExito(mensaje: String) {
        println("\n✓ $mensaje")
    }

    fun mostrarMensajeError(mensaje: String) {
        println("\n✗ Error: $mensaje")
    }
}