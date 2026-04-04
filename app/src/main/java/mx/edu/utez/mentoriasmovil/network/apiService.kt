package mx.edu.utez.mentoriasmovil.network

import mx.edu.utez.mentoriasmovil.model.LoginRequest
import mx.edu.utez.mentoriasmovil.model.LoginResponse
import mx.edu.utez.mentoriasmovil.model.Mentoria
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface apiService {
    @GET("/api/mentorias")
    suspend fun listarMentorias(): List<Mentoria>

    @POST("api/auth/login")
    suspend fun login(@Body loginDT0 : LoginRequest) : LoginResponse
}