package org.example.gui

import service.IEventoService
import model.Usuario
import model.Evento
import model.TipoEntrada
import java.awt.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.swing.*
import javax.swing.table.DefaultTableModel

class MisEventosPanel(
    private val usuario: Usuario,
    private val eventoService: IEventoService,
    private val onEventoEditado: () -> Unit
) : JPanel() {

    private val tableModel = DefaultTableModel()
    private val table = JTable(tableModel)
    private val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    init {
        layout = BorderLayout()
        createTablePanel()
        refrescarLista()
    }

    private fun createTablePanel() {
        // Configurar columnas
        tableModel.addColumn("ID")
        tableModel.addColumn("Nombre")
        tableModel.addColumn("Fecha")
        tableModel.addColumn("Ubicación")
        tableModel.addColumn("Categoría")
        tableModel.addColumn("Entrada")

        // Configurar tabla
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
        table.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS

        // Ocultar columna ID
        table.columnModel.getColumn(0).minWidth = 0
        table.columnModel.getColumn(0).maxWidth = 0
        table.columnModel.getColumn(0).width = 0

        val scrollPane = JScrollPane(table)
        add(scrollPane, BorderLayout.CENTER)

        // Panel de botones
        val buttonPanel = JPanel(FlowLayout(FlowLayout.CENTER))

        val verDetallesButton = JButton("Ver Detalles")
        verDetallesButton.addActionListener { verDetalles() }
        buttonPanel.add(verDetallesButton)

        val editarButton = JButton("Editar Evento")
        editarButton.addActionListener { editarEvento() }
        buttonPanel.add(editarButton)

        val refrescarButton = JButton("Refrescar")
        refrescarButton.addActionListener { refrescarLista() }
        buttonPanel.add(refrescarButton)

        add(buttonPanel, BorderLayout.SOUTH)
    }

    private fun refrescarLista() {
        // Limpiar tabla
        tableModel.rowCount = 0

        // Cargar solo los eventos creados por este usuario
        val eventos = eventoService.obtenerEventos().filter { it.creadorId == usuario.id }

        for (evento in eventos) {
            val entrada = if (evento.tipoEntrada == TipoEntrada.GRATIS) {
                "GRATIS"
            } else {
                "$${String.format("%.2f", evento.precioEntrada)}"
            }

            tableModel.addRow(arrayOf(
                evento.id,
                evento.nombre,
                evento.fecha.format(formatoFecha),
                evento.ubicacion,
                evento.categoria,
                entrada
            ))
        }
    }

    private fun verDetalles() {
        val selectedRow = table.selectedRow
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor seleccione un evento",
                "Aviso",
                JOptionPane.INFORMATION_MESSAGE
            )
            return
        }

        val eventoId = tableModel.getValueAt(selectedRow, 0).toString()
        val evento = eventoService.obtenerEventoPorId(eventoId)

        if (evento != null) {
            mostrarDetallesEvento(evento)
        }
    }

    private fun mostrarDetallesEvento(evento: Evento) {
        val entrada = if (evento.tipoEntrada == TipoEntrada.GRATIS) {
            "GRATIS"
        } else {
            "$${String.format("%.2f", evento.precioEntrada)}"
        }

        val promedio = eventoService.obtenerPromedioRecomendaciones(evento.id)
        val calificacion = if (promedio > 0) {
            "${"⭐".repeat(promedio.toInt())} (${String.format("%.1f", promedio)}/5)"
        } else {
            "Sin calificaciones aún"
        }

        val mensaje = """
            Nombre: ${evento.nombre}
            Descripción: ${evento.descripcion}
            Fecha: ${evento.fecha.format(formatoFecha)}
            Ubicación: ${evento.ubicacion}
            Categoría: ${evento.categoria}
            Entrada: $entrada
            Calificación: $calificacion
        """.trimIndent()

        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Detalles del Evento",
            JOptionPane.INFORMATION_MESSAGE
        )
    }

    private fun editarEvento() {
        val selectedRow = table.selectedRow
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor seleccione un evento",
                "Aviso",
                JOptionPane.INFORMATION_MESSAGE
            )
            return
        }

        val eventoId = tableModel.getValueAt(selectedRow, 0).toString()
        val evento = eventoService.obtenerEventoPorId(eventoId)

        if (evento == null) {
            JOptionPane.showMessageDialog(
                this,
                "Evento no encontrado",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
            return
        }

        // Crear diálogo de edición
        val dialog = JDialog(SwingUtilities.getWindowAncestor(this) as? Frame, "Editar Evento", true)
        dialog.layout = BorderLayout()
        dialog.setSize(500, 400)
        dialog.setLocationRelativeTo(this)

        val editPanel = crearPanelEdicion(evento, dialog)
        dialog.add(editPanel, BorderLayout.CENTER)

        dialog.isVisible = true
    }

    private fun crearPanelEdicion(evento: Evento, dialog: JDialog): JPanel {
        val panel = JPanel(GridBagLayout())
        panel.border = BorderFactory.createEmptyBorder(20, 20, 20, 20)

        val gbc = GridBagConstraints()
        gbc.insets = Insets(5, 5, 5, 5)
        gbc.fill = GridBagConstraints.HORIZONTAL

        val nombreField = JTextField(evento.nombre, 20)
        val descripcionArea = JTextArea(evento.descripcion, 5, 20)
        val fechaField = JTextField(evento.fecha.format(formatoFecha), 20)
        val ubicacionField = JTextField(evento.ubicacion, 20)
        val categoriaField = JTextField(evento.categoria, 20)

        var row = 0

        // Nombre
        gbc.gridx = 0
        gbc.gridy = row
        panel.add(JLabel("Nombre:"), gbc)
        gbc.gridx = 1
        panel.add(nombreField, gbc)
        row++

        // Descripción
        gbc.gridx = 0
        gbc.gridy = row
        panel.add(JLabel("Descripción:"), gbc)
        gbc.gridx = 1
        val scrollPane = JScrollPane(descripcionArea)
        scrollPane.preferredSize = Dimension(250, 80)
        panel.add(scrollPane, gbc)
        row++

        // Fecha
        gbc.gridx = 0
        gbc.gridy = row
        panel.add(JLabel("Fecha:"), gbc)
        gbc.gridx = 1
        panel.add(fechaField, gbc)
        row++

        // Ubicación
        gbc.gridx = 0
        gbc.gridy = row
        panel.add(JLabel("Ubicación:"), gbc)
        gbc.gridx = 1
        panel.add(ubicacionField, gbc)
        row++

        // Categoría
        gbc.gridx = 0
        gbc.gridy = row
        panel.add(JLabel("Categoría:"), gbc)
        gbc.gridx = 1
        panel.add(categoriaField, gbc)
        row++

        // Botones
        gbc.gridx = 0
        gbc.gridy = row
        gbc.gridwidth = 2
        val buttonPanel = JPanel(FlowLayout(FlowLayout.CENTER))

        val guardarButton = JButton("Guardar")
        guardarButton.addActionListener {
            if (guardarCambios(evento, nombreField, descripcionArea, fechaField, ubicacionField, categoriaField)) {
                dialog.dispose()
                refrescarLista()
                onEventoEditado()
            }
        }
        buttonPanel.add(guardarButton)

        val cancelarButton = JButton("Cancelar")
        cancelarButton.addActionListener { dialog.dispose() }
        buttonPanel.add(cancelarButton)

        panel.add(buttonPanel, gbc)

        return panel
    }

    private fun guardarCambios(
        eventoOriginal: Evento,
        nombreField: JTextField,
        descripcionArea: JTextArea,
        fechaField: JTextField,
        ubicacionField: JTextField,
        categoriaField: JTextField
    ): Boolean {
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
            return false
        }

        val fecha = try {
            LocalDateTime.parse(fechaStr, formatoFecha)
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(
                this,
                "Formato de fecha inválido. Use dd/MM/yyyy HH:mm",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
            return false
        }

        val eventoActualizado = eventoOriginal.copy(
            nombre = nombre,
            descripcion = descripcion,
            fecha = fecha,
            ubicacion = ubicacion,
            categoria = categoria
        )

        val resultado = eventoService.editarEvento(eventoActualizado)

        if (resultado) {
            JOptionPane.showMessageDialog(
                this,
                "Evento actualizado exitosamente!",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            )
            return true
        } else {
            JOptionPane.showMessageDialog(
                this,
                "No se pudo actualizar el evento",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
            return false
        }
    }
}