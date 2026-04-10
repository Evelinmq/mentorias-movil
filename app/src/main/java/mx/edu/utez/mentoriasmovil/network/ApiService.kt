package mx.edu.utez.mentoriasmovil.network

import mx.edu.utez.mentoriasmovil.model.Carrera
import mx.edu.utez.mentoriasmovil.model.Edificio
import mx.edu.utez.mentoriasmovil.model.Espacio
import mx.edu.utez.mentoriasmovil.model.LoginRequest
import mx.edu.utez.mentoriasmovil.model.LoginResponse
import mx.edu.utez.mentoriasmovil.model.Materia
import mx.edu.utez.mentoriasmovil.model.Mentoria
import mx.edu.utez.mentoriasmovil.model.MentoriaRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("api/mentorias")
    suspend fun listarMentorias(): List<Mentoria>

    @POST("api/auth/login")
    suspend fun login(@Body loginDT0: LoginRequest): Response<LoginResponse>

    @GET("api/materias")
    suspend fun listarMaterias(): List<Materia>

    @POST("api/materias")
    suspend fun crearMateria(@Body materia: Materia): Response<Materia>

    @PUT("api/materias/{id}")
    suspend fun actualizarMateria(@Path("id") id: Long, @Body materia: Materia): Response<Materia>

    @DELETE("api/materias/{id}")
    suspend fun eliminarMateria(@Path("id") id: Long): Response<Unit>


    @GET("api/carreras")
    suspend fun listarCarreras(): List<Carrera>

    @POST("api/carreras")
    suspend fun crearCarrera(@Body carrera: Carrera): Response<Carrera>

    @PUT("api/carreras/{id}")
    suspend fun actualizarCarrera(@Path("id") id: Long, @Body carrera: Carrera): Response<Carrera>

    @DELETE("api/carreras/{id}")
    suspend fun eliminarCarrera(@Path("id") id: Long): Response<Unit>

    @GET("api/edificios")
    suspend fun listarEdificios(): List<Edificio>

    @GET("api/espacios")
    suspend fun getEspacios(): List<Espacio>

    @POST("api/mentorias")
    suspend fun crearMentoria(
        @Body mentoria: MentoriaRequest
    ): Response<Unit>

    @GET("api/mentorias/movil")
    suspend fun obtenerMentorias(): List<Mentoria>

    @GET("api/mentorias/movil/mentor/{id}")
    suspend fun obtenerMentoriasPorMentor(@Path("id") mentorId: Long): List<Mentoria>
}