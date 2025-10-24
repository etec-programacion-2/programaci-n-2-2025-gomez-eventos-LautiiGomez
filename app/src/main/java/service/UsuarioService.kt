package service

import model.Usuario
import repository.IUsuarioRepository

class UsuarioService(
    private val usuarioRepository: IUsuarioRepository
) : IUsuarioService {

    override fun registrarUsuario(usuario: Usuario): Usuario {
        return usuarioRepository.guardar(usuario)
    }

    override fun obtenerUsuario(id: String): Usuario? {
        return usuarioRepository.obtenerPorId(id)
    }

    override fun iniciarSesion(email: String): Usuario? {
        return usuarioRepository.obtenerPorEmail(email)
    }
}