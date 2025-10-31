package org.example.gui

import service.IUsuarioService
import service.IEventoService
import model.Usuario
import java.awt.*
import javax.swing.*

class MainFrame(
    private val usuario: Usuario,
    private val usuarioService: IUsuarioService,
    private val eventoService: IEventoService
) : JFrame("Buscador de Eventos - ${usuario.nombre}") {

    private val tabbedPane = JTabbedPane()

    init {
        setupFrame()
        createTabs()
    }

    private fun setupFrame() {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(900, 600)
        setLocationRelativeTo(null)
        layout = BorderLayout()

        // Panel superior con info del usuario
        val topPanel = JPanel(FlowLayout(FlowLayout.RIGHT))
        topPanel.border = BorderFactory.createEmptyBorder(5, 10, 5, 10)
        topPanel.add(JLabel("Usuario: ${usuario.nombre} (${usuario.email})"))

        val logoutButton = JButton("Cerrar Sesión")
        logoutButton.addActionListener { cerrarSesion() }
        topPanel.add(logoutButton)

        add(topPanel, BorderLayout.NORTH)
        add(tabbedPane, BorderLayout.CENTER)
    }

    private fun createTabs() {
        // Pestaña: Crear Evento
        val crearEventoPanel = CrearEventoPanel(usuario, eventoService) {
            // Callback para refrescar la lista cuando se crea un evento
            (tabbedPane.getComponentAt(1) as? ListaEventosPanel)?.refrescarLista()
        }
        tabbedPane.addTab("➕ Crear Evento", crearEventoPanel)

        // Pestaña: Lista de Eventos
        val listaEventosPanel = ListaEventosPanel(usuario, eventoService)
        tabbedPane.addTab("📋 Todos los Eventos", listaEventosPanel)

        // Pestaña: Buscar Eventos
        val buscarEventosPanel = BuscarEventosPanel(usuario, eventoService)
        tabbedPane.addTab("🔍 Buscar Eventos", buscarEventosPanel)

        // Pestaña: Mis Eventos
        val misEventosPanel = MisEventosPanel(usuario, eventoService) {
            // Callback para refrescar listas cuando se edita un evento
            (tabbedPane.getComponentAt(1) as? ListaEventosPanel)?.refrescarLista()
        }
        tabbedPane.addTab("📝 Mis Eventos", misEventosPanel)
    }

    private fun cerrarSesion() {
        val opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        )

        if (opcion == JOptionPane.YES_OPTION) {
            val loginFrame = LoginFrame(usuarioService, eventoService)
            loginFrame.isVisible = true
            dispose()
        }
    }
}