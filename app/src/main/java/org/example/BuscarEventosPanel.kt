package org.example.gui

import service.IEventoService
import model.Usuario
import model.Evento
import model.TipoEntrada
import java.awt.*
import java.time.format.DateTimeFormatter
import javax.swing.*
import javax.swing.table.DefaultTableModel

class BuscarEventosPanel(
    private val usuario: Usuario,
    private val eventoService: IEventoService
) : JPanel() {

    private val tableModel = DefaultTableModel()
    private val table = JTable(tableModel)
    private val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    private val ubicacionField = JTextField(20)
    private val precioMaxField = JTextField(10)

    init {
        layout = BorderLayout()
        createSearchPanel()
        createTablePanel()
    }

    private fun createSearchPanel() {
        val searchPanel = JPanel()
        searchPanel.layout = FlowLayout(FlowLayout.LEFT)
        searchPanel.border = BorderFactory.createTitledBorder("Filtros de Búsqueda")

        // Búsqueda por ubicación
        searchPanel.add(JLabel("Ubicación:"))
        searchPanel.add(ubicacionField)

        val buscarUbicacionButton = JButton("Buscar")
        buscarUbicacionButton.addActionListener { buscarPorUbicacion() }
        searchPanel.add(buscarUbicacionButton)

        searchPanel.add(Box.createHorizontalStrut(20))

        // Búsqueda por precio
        searchPanel.add(JLabel("Precio máximo: $"))
        searchPanel.add(precioMaxField)

        val buscarPrecioButton = JButton("Buscar")
        buscarPrecioButton.addActionListener { buscarPorPrecio() }
        searchPanel.add(buscarPrecioButton)

        searchPanel.add(Box.createHorizontalStrut(20))

        // Búsqueda de gratuitos
        val gratuitosButton = JButton("Solo Gratuitos")
        gratuitosButton.addActionListener { buscarGratuitos() }
        searchPanel.add(gratuitosButton)

        searchPanel.add(Box.createHorizontalStrut(20))

        // Mostrar todos
        val todosButton = JButton("Mostrar Todos")
        todosButton.addActionListener { mostrarTodos() }
        searchPanel.add(todosButton)

        add(searchPanel, BorderLayout.NORTH)
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

        val confirmarAsistenciaButton = JButton("Confirmar Asistencia")
        confirmarAsistenciaButton.addActionListener { confirmarAsistencia() }
        buttonPanel.add(confirmarAsistenciaButton)

        add(buttonPanel, BorderLayout.SOUTH)
    }

    private fun buscarPorUbicacion() {
        val ubicacion = ubicacionField.text.trim()

        if (ubicacion.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor ingrese una ubicación",
                "Aviso",
                JOptionPane.INFORMATION_MESSAGE
            )
            return
        }

        val eventos = eventoService.buscarPorUbicacion(ubicacion)
        mostrarEventos(eventos, "Resultados para '$ubicacion'")
    }

    private fun buscarPorPrecio() {
        val precioStr = precioMaxField.text.trim()

        if (precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor ingrese un precio máximo",
                "Aviso",
                JOptionPane.INFORMATION_MESSAGE
            )
            return
        }

        val precioMax = try {
            precioStr.toDouble()
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(
                this,
                "Precio inválido",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
            return
        }

        val eventos = eventoService.buscarPorPrecio(precioMax)
        mostrarEventos(eventos, "Eventos hasta $$precioMax")
    }

    private fun buscarGratuitos() {
        val eventos = eventoService.buscarGratuitos()
        mostrarEventos(eventos, "Eventos Gratuitos")
    }

    private fun mostrarTodos() {
        val eventos = eventoService.obtenerEventos()
        mostrarEventos(eventos, "Todos los Eventos")
    }

    private fun mostrarEventos(eventos: List<Evento>, titulo: String) {
        // Limpiar tabla
        tableModel.rowCount = 0

        if (eventos.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No se encontraron eventos",
                "Aviso",
                JOptionPane.INFORMATION_MESSAGE
            )
            return
        }

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

        JOptionPane.showMessageDialog(
            this,
            "Se encontraron ${eventos.size} eventos",
            titulo,
            JOptionPane.INFORMATION_MESSAGE
        )
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
}