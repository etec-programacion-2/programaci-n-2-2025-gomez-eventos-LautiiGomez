package service

import model.Usuario
import java.util.UUID

class UsuarioService : IUsuarioService {
    private val usuarios = mutableListOf<Usuario>()

    override fun registrarUsuario(usuario: Usuario): Usuario {
        val nuevoUsuario = usuario.copy(id = UUID.randomUUID().toString())
        usuarios.add(nuevoUsuario)
        return nuevoUsuario
    }

    override fun obtenerUsuario(id: String): Usuario? {
        return usuarios.find { it.id == id }
    }
}