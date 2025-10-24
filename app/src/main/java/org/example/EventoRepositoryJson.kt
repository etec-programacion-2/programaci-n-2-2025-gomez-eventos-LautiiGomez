package repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import model.Evento
import model.Asistencia
import model.Recomendacion
import model.TipoEntrada
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.time.LocalDateTime
import java.util.UUID

class EventoRepositoryJson(
    private val eventosFile: String = "eventos.json",
    private val asistenciasFile: String = "asistencias.json",
    private val recomendacionesFile: String = "recomendaciones.json"
) : IEventoRepository {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .setPrettyPrinting()
        .create()

    private var eventos: MutableList<Evento>
    private var asistencias: MutableList<Asistencia>
    private var recomendaciones: MutableList<Recomendacion>

    init {
        eventos = cargarEventos()
        asistencias = cargarAsistencias()
        recomendaciones = cargarRecomendaciones()
    }

    private fun cargarEventos(): MutableList<Evento> {
        val file = File(eventosFile)
        if (!file.exists()) {
            return mutableListOf()
        }

        return try {
            FileReader(file).use { reader ->
                val type = object : TypeToken<MutableList<Evento>>() {}.type
                gson.fromJson(reader, type) ?: mutableListOf()
            }
        } catch (e: Exception) {
            println("Error al cargar eventos: ${e.message}")
            mutableListOf()
        }
    }

    private fun cargarAsistencias(): MutableList<Asistencia> {
        val file = File(asistenciasFile)
        if (!file.exists()) {
            return mutableListOf()
        }

        return try {
            FileReader(file).use { reader ->
                val type = object : TypeToken<MutableList<Asistencia>>() {}.type
                gson.fromJson(reader, type) ?: mutableListOf()
            }
        } catch (e: Exception) {
            println("Error al cargar asistencias: ${e.message}")
            mutableListOf()
        }
    }

    private fun cargarRecomendaciones(): MutableList<Recomendacion> {
        val file = File(recomendacionesFile)
        if (!file.exists()) {
            return mutableListOf()
        }

        return try {
            FileReader(file).use { reader ->
                val type = object : TypeToken<MutableList<Recomendacion>>() {}.type
                gson.fromJson(reader, type) ?: mutableListOf()
            }
        } catch (e: Exception) {
            println("Error al cargar recomendaciones: ${e.message}")
            mutableListOf()
        }
    }

    private fun guardarEventos() {
        try {
            FileWriter(eventosFile).use { writer ->
                gson.toJson(eventos, writer)
            }
        } catch (e: Exception) {
            println("Error al guardar eventos: ${e.message}")
        }
    }

    private fun guardarAsistencias() {
        try {
            FileWriter(asistenciasFile).use { writer ->
                gson.toJson(asistencias, writer)
            }
        } catch (e: Exception) {
            println("Error al guardar asistencias: ${e.message}")
        }
    }

    private fun guardarRecomendaciones() {
        try {
            FileWriter(recomendacionesFile).use { writer ->
                gson.toJson(recomendaciones, writer)
            }
        } catch (e: Exception) {
            println("Error al guardar recomendaciones: ${e.message}")
        }
    }

    override fun guardar(evento: Evento): Evento {
        val eventoConId = if (evento.id.isEmpty()) {
            evento.copy(id = UUID.randomUUID().toString())
        } else {
            evento
        }
        eventos.add(eventoConId)
        guardarEventos()
        return eventoConId
    }

    override fun actualizar(evento: Evento): Boolean {
        val index = eventos.indexOfFirst { it.id == evento.id }
        if (index == -1) return false

        eventos[index] = evento
        guardarEventos()
        return true
    }

    override fun obtenerTodos(): List<Evento> {
        return eventos.toList()
    }

    override fun obtenerPorId(id: String): Evento? {
        return eventos.find { it.id == id }
    }

    override fun obtenerPorUbicacion(ubicacion: String): List<Evento> {
        return eventos.filter {
            it.ubicacion.contains(ubicacion, ignoreCase = true)
        }
    }

    override fun obtenerPorPrecio(precioMaximo: Double): List<Evento> {
        return eventos.filter {
            it.tipoEntrada == TipoEntrada.GRATIS || it.precioEntrada <= precioMaximo
        }
    }

    override fun obtenerGratuitos(): List<Evento> {
        return eventos.filter { it.tipoEntrada == TipoEntrada.GRATIS }
    }

    override fun guardarAsistencia(asistencia: Asistencia): Boolean {
        if (existeAsistencia(asistencia.usuarioId, asistencia.eventoId)) {
            return false
        }
        asistencias.add(asistencia)
        guardarAsistencias()
        return true
    }

    override fun obtenerAsistencias(): List<Asistencia> {
        return asistencias.toList()
    }

    override fun existeAsistencia(usuarioId: String, eventoId: String): Boolean {
        return asistencias.any { it.usuarioId == usuarioId && it.eventoId == eventoId }
    }

    override fun guardarRecomendacion(recomendacion: Recomendacion): Boolean {
        // Si ya existe una recomendación del mismo usuario para el mismo evento, la reemplazamos
        val index = recomendaciones.indexOfFirst {
            it.usuarioId == recomendacion.usuarioId && it.eventoId == recomendacion.eventoId
        }

        if (index != -1) {
            recomendaciones[index] = recomendacion
        } else {
            recomendaciones.add(recomendacion)
        }

        guardarRecomendaciones()
        return true
    }

    override fun obtenerRecomendaciones(eventoId: String): List<Recomendacion> {
        return recomendaciones.filter { it.eventoId == eventoId }
    }

    override fun promedioRecomendaciones(eventoId: String): Double {
        val recs = obtenerRecomendaciones(eventoId)
        if (recs.isEmpty()) return 0.0
        return recs.map { it.puntuacion }.average()
    }
}