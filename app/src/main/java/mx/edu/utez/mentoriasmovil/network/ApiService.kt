package mx.edu.utez.mentoriasmovil.network

import mx.edu.utez.mentoriasmovil.model.LoginRequest
import mx.edu.utez.mentoriasmovil.model.LoginResponse
import mx.edu.utez.mentoriasmovil.model.Materia
import mx.edu.utez.mentoriasmovil.model.Mentoria
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/api/mentorias")
    suspend fun listarMentorias(): List<Mentoria>

    @POST("api/auth/login")
    suspend fun login(@Body loginDT0 : LoginRequest) : Response<LoginResponse>

    @GET("api/materias")
    suspend fun listarMaterias(): List<Materia>

    @POST("api/materias")
    suspend fun crearMateria(@Body materia: Materia): Response<Materia>

    @PUT("api/materias/{id}")
    suspend fun actualizarMateria(@Path("id") id: Long, @Body materia: Materia): Response<Materia>

    @DELETE("api/materias/{id}")
    suspend fun eliminarMateria(@Path("id") id: Long): Response<Unit>


}