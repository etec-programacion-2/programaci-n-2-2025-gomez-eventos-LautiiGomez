package service

import model.Usuario

interface IUsuarioService {
    fun registrarUsuario(usuario: Usuario): Usuario
    fun obtenerUsuario(id: Int): Usuario?
}