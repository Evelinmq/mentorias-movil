package mx.edu.utez.mentoriasmovil.repository

import mx.edu.utez.mentoriasmovil.data.model.LoginRequest
import mx.edu.utez.mentoriasmovil.retrofit.ApiService

class UserRepository(private val api: ApiService) {

    suspend fun login(nombreUsuario: String, contrasena: String): Result<Boolean> {
        return try {
            val request = LoginRequest(nombreUsuario, contrasena)
            val response = api.loginUsuario(request)

            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Credenciales incorrectas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registrarUsuario(user: UsuarioRequest): Result<String> {
        return try {
            val response = api.registrarUsuario(user)
            if (response.isSuccessful) {
                Result.success("Usuario registrado con éxito")
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}