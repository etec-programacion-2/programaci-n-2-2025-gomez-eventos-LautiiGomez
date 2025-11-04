package org.example.gui

import service.UsuarioService
import service.EventoService
import repository.UsuarioRepositoryJson
import repository.EventoRepositoryJson
import javax.swing.SwingUtilities
import javax.swing.UIManager

fun main() {
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    } catch (e: Exception) {
        e.printStackTrace()
    }

    val usuarioRepository = UsuarioRepositoryJson()
    val eventoRepository = EventoRepositoryJson()

    val usuarioService = UsuarioService(usuarioRepository)
    val eventoService = EventoService(eventoRepository)

    SwingUtilities.invokeLater {
        val loginFrame = LoginFrame(usuarioService, eventoService)
        loginFrame.isVisible = true
    }
}