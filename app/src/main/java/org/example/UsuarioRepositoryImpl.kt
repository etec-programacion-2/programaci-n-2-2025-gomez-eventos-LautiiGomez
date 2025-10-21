package repository

import model.Usuario
import java.util.UUID

class UsuarioRepositoryImpl : IUsuarioRepository {
    private val usuarios = mutableListOf<Usuario>()

    override fun guardar(usuario: Usuario): Usuario {
        val usuarioConId = if (usuario.id.isEmpty()) {
            usuario.copy(id = UUID.randomUUID().toString())
        } else {
            usuario
        }
        usuarios.add(usuarioConId)
        return usuarioConId
    }

    override fun obtenerTodos(): List<Usuario> {
        return usuarios.toList()
    }

    override fun obtenerPorId(id: String): Usuario? {
        return usuarios.find { it.id == id }
    }

    override fun existePorEmail(email: String): Boolean {
        return usuarios.any { it.email == email }
    }
}