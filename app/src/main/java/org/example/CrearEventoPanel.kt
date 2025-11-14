package org.example.gui

import service.IEventoService
import model.Usuario
import model.Evento
import model.TipoEntrada
import java.awt.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.swing.*

class CrearEventoPanel(
    private val usuario: Usuario,
    private val eventoService: IEventoService,
    private val onEventoCreado: () -> Unit
) : JPanel() {

    private val nombreField = JTextField(30)
    private val descripcionArea = JTextArea(5, 30)
    private val fechaField = JTextField(20)
    private val ubicacionField = JTextField(30)
    private val categoriaField = JTextField(30)
    private val tipoEntradaCombo = JComboBox(arrayOf("Gratis", "Pago"))
    private val precioField = JTextField(10)

    init {
        layout = BorderLayout()
        createFormPanel()
    }

    private fun createFormPanel() {
        val formPanel = JPanel(GridBagLayout())
        formPanel.border = BorderFactory.createEmptyBorder(20, 20, 20, 20)

        val gbc = GridBagConstraints()
        gbc.insets = Insets(5, 5, 5, 5)
        gbc.fill = GridBagConstraints.HORIZONTAL
        gbc.anchor = GridBagConstraints.WEST

        var row = 0

        // Título
        gbc.gridx = 0
        gbc.gridy = row
        gbc.gridwidth = 2
        val titleLabel = JLabel("Crear Nuevo Evento")
        titleLabel.font = Font("Arial", Font.BOLD, 16)
        formPanel.add(titleLabel, gbc)
        row++

        gbc.gridwidth = 1

        // Nombre
        addFormRow(formPanel, gbc, row++, "Nombre:", nombreField)

        // Descripción
        gbc.gridx = 0
        gbc.gridy = row
        formPanel.add(JLabel("Descripción:"), gbc)

        gbc.gridx = 1
        gbc.gridy = row
        val scrollPane = JScrollPane(descripcionArea)
        scrollPane.preferredSize = Dimension(300, 100)
        formPanel.add(scrollPane, gbc)
        row++

        // Fecha
        gbc.gridx = 0
        gbc.gridy = row
        formPanel.add(JLabel("Fecha (dd/MM/yyyy HH:mm):"), gbc)

        gbc.gridx = 1
        gbc.gridy = row
        formPanel.add(fechaField, gbc)
        row++

        // Ubicación
        addFormRow(formPanel, gbc, row++, "Ubicación:", ubicacionField)

        // Categoría
        addFormRow(formPanel, gbc, row++, "Categoría:", categoriaField)

        // Tipo de entrada
        gbc.gridx = 0
        gbc.gridy = row
        formPanel.add(JLabel("Tipo de entrada:"), gbc)

        gbc.gridx = 1
        gbc.gridy = row
        val entradaPanel = JPanel(FlowLayout(FlowLayout.LEFT, 5, 0))
        entradaPanel.add(tipoEntradaCombo)
        entradaPanel.add(JLabel("Precio: $"))
        entradaPanel.add(precioField)
        formPanel.add(entradaPanel, gbc)
        row++

        // Inicialmente deshabilitar el campo de precio
        precioField.isEnabled = false
        tipoEntradaCombo.addActionListener {
            precioField.isEnabled = tipoEntradaCombo.selectedIndex == 1
            if (tipoEntradaCombo.selectedIndex == 0) {
                precioField.text = "0"
            }
        }

        // Botones
        gbc.gridx = 0
        gbc.gridy = row
        gbc.gridwidth = 2
        val buttonPanel = JPanel(FlowLayout(FlowLayout.CENTER))

        val crearButton = JButton("Crear Evento")
        crearButton.addActionListener { crearEvento() }
        buttonPanel.add(crearButton)

        val limpiarButton = JButton("Limpiar")
        limpiarButton.addActionListener { limpiarFormulario() }
        buttonPanel.add(limpiarButton)

        formPanel.add(buttonPanel, gbc)

        // Agregar el panel al scrollpane
        val mainScrollPane = JScrollPane(formPanel)
        add(mainScrollPane, BorderLayout.CENTER)
    }

    private fun addFormRow(panel: JPanel, gbc: GridBagConstraints, row: Int, label: String, field: JTextField) {
        gbc.gridx = 0
        gbc.gridy = row
        panel.add(JLabel(label), gbc)

        gbc.gridx = 1
        gbc.gridy = row
        panel.add(field, gbc)
    }

    private fun crearEvento() {
        val nombre = nombreField.text.trim()
        val descripcion = descripcionArea.text.trim()
        val fechaStr = fechaField.text.trim()
        val ubicacion = ubicacionField.text.trim()
        val categoria = categoriaField.text.trim()

        if (nombre.isEmpty() || descripcion.isEmpty() || fechaStr.isEmpty() ||
            ubicacion.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor complete todos los campos",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
            return
        }

        val fecha = try {
            LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(
                this,
                "Formato de fecha inválido. Use dd/MM/yyyy HH:mm",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
            return
        }

        val tipoEntrada = if (tipoEntradaCombo.selectedIndex == 0)
            TipoEntrada.GRATIS
        else
            TipoEntrada.PAGO

        val precio = if (tipoEntrada == TipoEntrada.PAGO) {
            try {
                precioField.text.trim().toDouble()
            } catch (e: Exception) {
                JOptionPane.showMessageDialog(
                    this,
                    "Precio inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                )
                return
            }
        } else 0.0

        val evento = Evento(
            id = "",
            nombre = nombre,
            descripcion = descripcion,
            fecha = fecha,
            ubicacion = ubicacion,
            creadorId = usuario.id,
            categoria = categoria,
            tipoEntrada = tipoEntrada,
            precioEntrada = precio
        )

        eventoService.crearEvento(evento)

        JOptionPane.showMessageDialog(
            this,
            "Evento creado exitosamente!",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE
        )

        limpiarFormulario()
        onEventoCreado()
    }

    private fun limpiarFormulario() {
        nombreField.text = ""
        descripcionArea.text = ""
        fechaField.text = ""
        ubicacionField.text = ""
        categoriaField.text = ""
        tipoEntradaCombo.selectedIndex = 0
        precioField.text = ""
    }
}