package service

import model.Usuario

interface IUsuarioService {
    fun registrarUsuario(usuario: Usuario): Usuario
    fun obtenerUsuario(id: String): Usuario?
    fun iniciarSesion(email: String): Usuario?
}