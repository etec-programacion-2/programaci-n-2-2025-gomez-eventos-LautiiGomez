package org.example.gui

import service.UsuarioService
import service.EventoService
import repository.UsuarioRepositoryJson
import repository.EventoRepositoryJson
import javax.swing.SwingUtilities
import javax.swing.UIManager

fun main() {
    // Configurar Look and Feel del sistema
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    } catch (e: Exception) {
        e.printStackTrace()
    }

    // Inicializar repositorios y servicios
    val usuarioRepository = UsuarioRepositoryJson()
    val eventoRepository = EventoRepositoryJson()

    val usuarioService = UsuarioService(usuarioRepository)
    val eventoService = EventoService(eventoRepository)

    // Lanzar la interfaz gráfica en el hilo de eventos de Swing
    SwingUtilities.invokeLater {
        val loginFrame = LoginFrame(usuarioService, eventoService)
        loginFrame.isVisible = true
    }
}

