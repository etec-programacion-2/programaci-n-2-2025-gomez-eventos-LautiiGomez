package repository

import model.Usuario

interface IUsuarioRepository {
    fun guardar(usuario: Usuario): Usuario
    fun obtenerTodos(): List<Usuario>
    fun obtenerPorId(id: String): Usuario?
    fun obtenerPorEmail(email: String): Usuario?
    fun existePorEmail(email: String): Boolean
}