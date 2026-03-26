package org.example.gui

import service.IEventoService
import model.Usuario
import model.Evento
import model.TipoEntrada
import java.awt.*
import java.time.format.DateTimeFormatter
import javax.swing.*
import javax.swing.table.DefaultTableModel

class ListaEventosPanel(
    private val usuario: Usuario,
    private val eventoService: IEventoService
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
        tableModel.addColumn("Calificación")

        // Configurar tabla
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
        table.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS

        // Ocultar columna ID pero mantenerla en el modelo
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

        val confirmarAsistenciaButton = JButton("Confirmar Asistencia")
        confirmarAsistenciaButton.addActionListener { confirmarAsistencia() }
        buttonPanel.add(confirmarAsistenciaButton)

        val recomendarButton = JButton("Recomendar")
        recomendarButton.addActionListener { recomendarEvento() }
        buttonPanel.add(recomendarButton)

        val refrescarButton = JButton("Refrescar")
        refrescarButton.addActionListener { refrescarLista() }
        buttonPanel.add(refrescarButton)

        add(buttonPanel, BorderLayout.SOUTH)
    }

    fun refrescarLista() {
        // Limpiar tabla
        tableModel.rowCount = 0

        // Cargar eventos
        val eventos = eventoService.obtenerEventos()

        for (evento in eventos) {
            val entrada = if (evento.tipoEntrada == TipoEntrada.GRATIS) {
                "GRATIS"
            } else {
                "$${String.format("%.2f", evento.precioEntrada)}"
            }

            val promedio = eventoService.obtenerPromedioRecomendaciones(evento.id)
            val calificacion = if (promedio > 0) {
                "${"⭐".repeat(promedio.toInt())} (${String.format("%.1f", promedio)})"
            } else {
                "Sin calificar"
            }

            tableModel.addRow(arrayOf(
                evento.id,
                evento.nombre,
                evento.fecha.format(formatoFecha),
                evento.ubicacion,
                evento.categoria,
                entrada,
                calificacion
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
            val promedio = eventoService.obtenerPromedioRecomendaciones(evento.id)
            mostrarDetallesEvento(evento, promedio)
        }
    }

    private fun mostrarDetallesEvento(evento: Evento, promedio: Double) {
        val entrada = if (evento.tipoEntrada == TipoEntrada.GRATIS) {
            "GRATIS"
        } else {
            "$${String.format("%.2f", evento.precioEntrada)}"
        }

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

    private fun confirmarAsistencia() {
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
        val resultado = eventoService.confirmarAsistencia(usuario.id, eventoId)

        if (resultado) {
            JOptionPane.showMessageDialog(
                this,
                "Asistencia confirmada exitosamente!",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            )
        } else {
            JOptionPane.showMessageDialog(
                this,
                "No se pudo confirmar la asistencia. Ya puede haber confirmado previamente.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }

    private fun recomendarEvento() {
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

        val opciones = arrayOf("⭐", "⭐⭐", "⭐⭐⭐", "⭐⭐⭐⭐", "⭐⭐⭐⭐⭐")
        val seleccion = JOptionPane.showInputDialog(
            this,
            "Califica el evento '${evento.nombre}':",
            "Recomendar Evento",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        )

        if (seleccion != null) {
            val puntuacion = opciones.indexOf(seleccion) + 1
            val resultado = eventoService.recomendarEvento(usuario.id, eventoId, puntuacion)

            if (resultado) {
                JOptionPane.showMessageDialog(
                    this,
                    "¡Gracias por tu recomendación!",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                )
                refrescarLista()
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "No se pudo guardar la recomendación",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }
}