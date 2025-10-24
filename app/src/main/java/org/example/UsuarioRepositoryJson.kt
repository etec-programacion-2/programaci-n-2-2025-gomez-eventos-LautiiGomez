package repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import model.Usuario
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.UUID

class UsuarioRepositoryJson(
    private val usuariosFile: String = "usuarios.json"
) : IUsuarioRepository {

    private val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    private var usuarios: MutableList<Usuario>

    init {
        usuarios = cargarUsuarios()
    }

    private fun cargarUsuarios(): MutableList<Usuario> {
        val file = File(usuariosFile)
        if (!file.exists()) {
            return mutableListOf()
        }

        return try {
            FileReader(file).use { reader ->
                val type = object : TypeToken<MutableList<Usuario>>() {}.type
                gson.fromJson(reader, type) ?: mutableListOf()
            }
        } catch (e: Exception) {
            println("Error al cargar usuarios: ${e.message}")
            mutableListOf()
        }
    }

    private fun guardarUsuarios() {
        try {
            FileWriter(usuariosFile).use { writer ->
                gson.toJson(usuarios, writer)
            }
        } catch (e: Exception) {
            println("Error al guardar usuarios: ${e.message}")
        }
    }

    override fun guardar(usuario: Usuario): Usuario {
        val usuarioConId = if (usuario.id.isEmpty()) {
            usuario.copy(id = UUID.randomUUID().toString())
        } else {
            usuario
        }
        usuarios.add(usuarioConId)
        guardarUsuarios()
        return usuarioConId
    }

    override fun obtenerTodos(): List<Usuario> {
        return usuarios.toList()
    }

    override fun obtenerPorId(id: String): Usuario? {
        return usuarios.find { it.id == id }
    }

    override fun obtenerPorEmail(email: String): Usuario? {
        return usuarios.find { it.email == email }
    }

    override fun existePorEmail(email: String): Boolean {
        return usuarios.any { it.email == email }
    }
}