package mx.edu.utez.mentoriasmovil.network

import mx.edu.utez.mentoriasmovil.model.Mentoria
import retrofit2.http.GET

interface apiService {
    @GET("/api/mentorias")
    suspend fun listarMentorias(): List<Mentoria>
}