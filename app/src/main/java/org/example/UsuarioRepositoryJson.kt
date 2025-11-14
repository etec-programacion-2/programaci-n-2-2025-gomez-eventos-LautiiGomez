package repository

import com.google.gson.reflect.TypeToken
import model.Usuario
import java.util.UUID

/**
 * Implementación de repositorio de usuarios usando JSON.
 * Hereda de BaseRepository para reutilizar funcionalidad común.
 * Demuestra Herencia y Polimorfismo.
 */
class UsuarioRepositoryJson(
    private val nombreArchivo: String = "usuarios.json"
) : BaseRepository<Usuario>(), IUsuarioRepository {

    // Encapsulamiento: datos privados
    private var usuarios: MutableList<Usuario>

    init {
        usuarios = cargarDatos()
    }

    // Polimorfismo: implementación específica del método abstracto
    override fun getNombreArchivo(): String = nombreArchivo

    // Polimorfismo: implementación específica del TypeToken
    override fun getTypeToken(): TypeToken<MutableList<Usuario>> {
        return object : TypeToken<MutableList<Usuario>>() {}
    }

    override fun guardar(usuario: Usuario): Usuario {
        val usuarioConId = if (usuario.id.isEmpty()) {
            usuario.copy(id = UUID.randomUUID().toString())
        } else {
            usuario
        }
        usuarios.add(usuarioConId)
        guardarDatos(usuarios)
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