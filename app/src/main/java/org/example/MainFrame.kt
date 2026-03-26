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
) : JFrame("Finder - ${usuario.nombre}") {

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

        val topPanel = JPanel(FlowLayout(FlowLayout.RIGHT))
        topPanel.border = BorderFactory.createEmptyBorder(5, 10, 5, 10)
        topPanel.add(JLabel("Usuario: ${usuario.nombre} (${usuario.email})"))
        topPanel.setBackground(Color.WHITE)

        val logoutButton = JButton("Cerrar Sesión")
        logoutButton.addActionListener { cerrarSesion() }
        topPanel.add(logoutButton)

        add(topPanel, BorderLayout.NORTH)
        add(tabbedPane, BorderLayout.CENTER)
    }

    private fun createTabs() {
        val crearEventoPanel = CrearEventoPanel(usuario, eventoService) {
            (tabbedPane.getComponentAt(1) as? ListaEventosPanel)?.refrescarLista()
        }
        tabbedPane.addTab("➕ Crear Evento", crearEventoPanel)

        val listaEventosPanel = ListaEventosPanel(usuario, eventoService)
        tabbedPane.addTab("📋 Todos los Eventos", listaEventosPanel)

        val buscarEventosPanel = BuscarEventosPanel(usuario, eventoService)
        tabbedPane.addTab("🔍 Buscar Eventos", buscarEventosPanel)

        val misEventosPanel = MisEventosPanel(usuario, eventoService) {
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