package mx.edu.utez.mentoriasmovil.retrofit

import mx.edu.utez.mentoriasmovil.data.model.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface ApiService {
    @POST("api/auth/login")
    suspend fun loginUsuario(@Body credentials: LoginRequest): Response<LoginResponse> //Antes <void>
    @POST("api/auth/register")
    suspend fun registrarUsuario(@Body user: UsuarioRequest): Response<RegisterResponse> //Antes <void>


    data class LoginResponse(
        val token: String,
        val usuario: String
    )

    data class RegisterResponse(
        val mensaje: String
    )
}