package repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.time.LocalDateTime

/**
 * Clase base abstracta que implementa funcionalidad común para repositorios JSON.
 * Demuestra Herencia y Encapsulamiento protegiendo la lógica de persistencia.
 */
abstract class BaseRepository<T> {

    // Encapsulamiento: propiedades protegidas
    protected val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .setPrettyPrinting()
        .create()

    // Método abstracto que debe ser implementado por subclases (Polimorfismo)
    protected abstract fun getNombreArchivo(): String

    // Método abstracto para obtener el TypeToken específico de cada tipo
    protected abstract fun getTypeToken(): TypeToken<MutableList<T>>

    // Método común para todas las subclases (reutilización por Herencia)
    protected fun cargarDatos(): MutableList<T> {
        val file = File(getNombreArchivo())
        if (!file.exists()) {
            return mutableListOf()
        }

        return try {
            FileReader(file).use { reader ->
                gson.fromJson(reader, getTypeToken().type) ?: mutableListOf()
            }
        } catch (e: Exception) {
            println("Error al cargar datos desde ${getNombreArchivo()}: ${e.message}")
            mutableListOf()
        }
    }

    // Método común para persistir datos (reutilización por Herencia)
    protected fun guardarDatos(datos: List<T>) {
        try {
            FileWriter(getNombreArchivo()).use { writer ->
                gson.toJson(datos, writer)
            }
        } catch (e: Exception) {
            println("Error al guardar datos en ${getNombreArchivo()}: ${e.message}")
        }
    }
}