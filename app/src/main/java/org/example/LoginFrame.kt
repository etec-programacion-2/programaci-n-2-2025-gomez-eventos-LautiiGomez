package org.example.gui

import service.IUsuarioService
import service.IEventoService
import model.Usuario
import java.awt.*
import javax.swing.*

class LoginFrame(
    private val usuarioService: IUsuarioService,
    private val eventoService: IEventoService
) : JFrame("Finder - Login") {

    private val emailField = JTextField(20)
    private val nombreField = JTextField(20)
    private val cardLayout = CardLayout()
    private val mainPanel = JPanel(cardLayout)

    init {
        setupFrame()
        createLoginPanel()
        createRegisterPanel()
    }

    private fun setupFrame() {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(400, 300)
        setLocationRelativeTo(null)
        layout = BorderLayout()

        val titleLabel = JLabel("Finder", SwingConstants.CENTER)
        titleLabel.font = Font("Arial", Font.BOLD, 18)
        titleLabel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        add(titleLabel, BorderLayout.NORTH)

        add(mainPanel, BorderLayout.CENTER)

        val imageIcon = ImageIcon("../../../resources/Finder.png") // Ruta relativa a la imagen

        // Crear un JLabel con la imagen
        val logoLabel = JLabel(imageIcon)
        add(logoLabel, BorderLayout.SOUTH)
    }

    private fun createLoginPanel() {
        val loginPanel = JPanel(GridBagLayout())
        val gbc = GridBagConstraints()
        gbc.insets = Insets(5, 5, 5, 5)
        gbc.fill = GridBagConstraints.HORIZONTAL

        gbc.gridx = 0
        gbc.gridy = 0
        loginPanel.add(JLabel("Email:"), gbc)

        gbc.gridx = 1
        gbc.gridy = 0
        loginPanel.add(emailField, gbc)

        gbc.gridx = 0
        gbc.gridy = 1
        gbc.gridwidth = 2
        val loginButton = JButton("Iniciar Sesión")
        loginButton.addActionListener { handleLogin() }
        loginPanel.add(loginButton, gbc)

        gbc.gridy = 2
        val registerLink = JButton("¿No tienes cuenta? Regístrate")
        registerLink.isBorderPainted = false
        registerLink.isContentAreaFilled = false
        registerLink.foreground = Color.BLUE
        registerLink.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        registerLink.addActionListener {
            cardLayout.show(mainPanel, "REGISTER")
        }
        loginPanel.add(registerLink, gbc)

        mainPanel.add(loginPanel, "LOGIN")
    }

    private fun createRegisterPanel() {
        val registerPanel = JPanel(GridBagLayout())
        val gbc = GridBagConstraints()
        gbc.insets = Insets(5, 5, 5, 5)
        gbc.fill = GridBagConstraints.HORIZONTAL

        gbc.gridx = 0
        gbc.gridy = 0
        registerPanel.add(JLabel("Nombre:"), gbc)

        gbc.gridx = 1
        gbc.gridy = 0
        registerPanel.add(nombreField, gbc)

        gbc.gridx = 0
        gbc.gridy = 1
        registerPanel.add(JLabel("Email:"), gbc)

        val regEmailField = JTextField(20)
        gbc.gridx = 1
        gbc.gridy = 1
        registerPanel.add(regEmailField, gbc)

        gbc.gridx = 0
        gbc.gridy = 2
        gbc.gridwidth = 2
        val registerButton = JButton("Registrarse")
        registerButton.addActionListener {
            handleRegister(nombreField.text, regEmailField.text)
        }
        registerPanel.add(registerButton, gbc)

        gbc.gridy = 3
        val loginLink = JButton("¿Ya tienes cuenta? Inicia sesión")
        loginLink.isBorderPainted = false
        loginLink.isContentAreaFilled = false
        loginLink.foreground = Color.BLUE
        loginLink.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        loginLink.addActionListener {
            cardLayout.show(mainPanel, "LOGIN")
        }
        registerPanel.add(loginLink, gbc)

        mainPanel.add(registerPanel, "REGISTER")
    }

    private fun handleLogin() {
        val email = emailField.text.trim()

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor ingrese su email",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
            return
        }

        val usuario = usuarioService.iniciarSesion(email)
        if (usuario != null) {
            abrirVentanaPrincipal(usuario)
        } else {
            JOptionPane.showMessageDialog(
                this,
                "No se encontró un usuario con ese email",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }

    private fun handleRegister(nombre: String, email: String) {
        if (nombre.trim().isEmpty() || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor complete todos los campos",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
            return
        }

        val usuario = Usuario(id = "", nombre = nombre.trim(), email = email.trim())
        val usuarioRegistrado = usuarioService.registrarUsuario(usuario)

        JOptionPane.showMessageDialog(
            this,
            "Usuario registrado exitosamente!",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE
        )

        abrirVentanaPrincipal(usuarioRegistrado)
    }

    private fun abrirVentanaPrincipal(usuario: Usuario) {
        val mainFrame = MainFrame(usuario, usuarioService, eventoService)
        mainFrame.isVisible = true
        dispose()
    }
}