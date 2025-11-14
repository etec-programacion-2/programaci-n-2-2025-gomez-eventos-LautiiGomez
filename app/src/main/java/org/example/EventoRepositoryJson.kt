package repository

import com.google.gson.reflect.TypeToken
import model.Evento
import model.Asistencia
import model.Recomendacion
import model.TipoEntrada
import java.util.UUID

/**
 * Implementación de repositorio de eventos usando JSON.
 * Demuestra Herencia, Encapsulamiento y Polimorfismo mediante
 * la gestión de múltiples entidades relacionadas.
 */
class EventoRepositoryJson(
    private val archivoEventos: String = "eventos.json",
    private val archivoAsistencias: String = "asistencias.json",
    private val archivoRecomendaciones: String = "recomendaciones.json"
) : IEventoRepository {

    // Encapsulamiento: repositorios internos privados
    private val eventoRepo = EventoRepo(archivoEventos)
    private val asistenciaRepo = AsistenciaRepo(archivoAsistencias)
    private val recomendacionRepo = RecomendacionRepo(archivoRecomendaciones)

    // Delegación a repositorios especializados
    override fun guardar(evento: Evento): Evento {
        return eventoRepo.guardar(evento)
    }

    override fun actualizar(evento: Evento): Boolean {
        return eventoRepo.actualizar(evento)
    }

    override fun obtenerTodos(): List<Evento> {
        return eventoRepo.obtenerTodos()
    }

    override fun obtenerPorId(id: String): Evento? {
        return eventoRepo.obtenerPorId(id)
    }

    override fun obtenerPorUbicacion(ubicacion: String): List<Evento> {
        return eventoRepo.obtenerPorUbicacion(ubicacion)
    }

    override fun obtenerPorPrecio(precioMaximo: Double): List<Evento> {
        return eventoRepo.obtenerPorPrecio(precioMaximo)
    }

    override fun obtenerGratuitos(): List<Evento> {
        return eventoRepo.obtenerGratuitos()
    }

    override fun guardarAsistencia(asistencia: Asistencia): Boolean {
        return asistenciaRepo.guardar(asistencia)
    }

    override fun obtenerAsistencias(): List<Asistencia> {
        return asistenciaRepo.obtenerTodos()
    }

    override fun existeAsistencia(usuarioId: String, eventoId: String): Boolean {
        return asistenciaRepo.existe(usuarioId, eventoId)
    }

    override fun guardarRecomendacion(recomendacion: Recomendacion): Boolean {
        return recomendacionRepo.guardar(recomendacion)
    }

    override fun obtenerRecomendaciones(eventoId: String): List<Recomendacion> {
        return recomendacionRepo.obtenerPorEvento(eventoId)
    }

    override fun promedioRecomendaciones(eventoId: String): Double {
        return recomendacionRepo.calcularPromedio(eventoId)
    }

    // Clases internas que heredan de BaseRepository (Herencia)
    private class EventoRepo(
        private val archivo: String
    ) : BaseRepository<Evento>() {

        private var eventos: MutableList<Evento> = cargarDatos()

        override fun getNombreArchivo(): String = archivo

        override fun getTypeToken(): TypeToken<MutableList<Evento>> {
            return object : TypeToken<MutableList<Evento>>() {}
        }

        fun guardar(evento: Evento): Evento {
            val eventoConId = if (evento.id.isEmpty()) {
                evento.copy(id = UUID.randomUUID().toString())
            } else {
                evento
            }
            eventos.add(eventoConId)
            guardarDatos(eventos)
            return eventoConId
        }

        fun actualizar(evento: Evento): Boolean {
            val index = eventos.indexOfFirst { it.id == evento.id }
            if (index == -1) return false

            eventos[index] = evento
            guardarDatos(eventos)
            return true
        }

        fun obtenerTodos(): List<Evento> = eventos.toList()

        fun obtenerPorId(id: String): Evento? {
            return eventos.find { it.id == id }
        }

        fun obtenerPorUbicacion(ubicacion: String): List<Evento> {
            return eventos.filter {
                it.ubicacion.contains(ubicacion, ignoreCase = true)
            }
        }

        fun obtenerPorPrecio(precioMaximo: Double): List<Evento> {
            return eventos.filter {
                it.tipoEntrada == TipoEntrada.GRATIS || it.precioEntrada <= precioMaximo
            }
        }

        fun obtenerGratuitos(): List<Evento> {
            return eventos.filter { it.tipoEntrada == TipoEntrada.GRATIS }
        }
    }

    private class AsistenciaRepo(
        private val archivo: String
    ) : BaseRepository<Asistencia>() {

        private var asistencias: MutableList<Asistencia> = cargarDatos()

        override fun getNombreArchivo(): String = archivo

        override fun getTypeToken(): TypeToken<MutableList<Asistencia>>  {
            return object : TypeToken<MutableList<Asistencia>>() {}
        }

        fun guardar(asistencia: Asistencia): Boolean {
            if (existe(asistencia.usuarioId, asistencia.eventoId)) {
                return false
            }
            asistencias.add(asistencia)
            guardarDatos(asistencias)
            return true
        }

        fun obtenerTodos(): List<Asistencia> = asistencias.toList()

        fun existe(usuarioId: String, eventoId: String): Boolean {
            return asistencias.any {
                it.usuarioId == usuarioId && it.eventoId == eventoId
            }
        }
    }

    private class RecomendacionRepo(
        private val archivo: String
    ) : BaseRepository<Recomendacion>() {

        private var recomendaciones: MutableList<Recomendacion> = cargarDatos()

        override fun getNombreArchivo(): String = archivo

        override fun getTypeToken(): TypeToken<MutableList<Recomendacion>> {
            return object : TypeToken<MutableList<Recomendacion>>() {}
        }

        fun guardar(recomendacion: Recomendacion): Boolean {
            val index = recomendaciones.indexOfFirst {
                it.usuarioId == recomendacion.usuarioId &&
                        it.eventoId == recomendacion.eventoId
            }

            if (index != -1) {
                recomendaciones[index] = recomendacion
            } else {
                recomendaciones.add(recomendacion)
            }

            guardarDatos(recomendaciones)
            return true
        }

        fun obtenerPorEvento(eventoId: String): List<Recomendacion> {
            return recomendaciones.filter { it.eventoId == eventoId }
        }

        fun calcularPromedio(eventoId: String): Double {
            val recs = obtenerPorEvento(eventoId)
            if (recs.isEmpty()) return 0.0
            return recs.map { it.puntuacion }.average()
        }
    }
}