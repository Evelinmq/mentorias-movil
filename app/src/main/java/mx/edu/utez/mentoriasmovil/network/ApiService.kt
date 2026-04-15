package mx.edu.utez.mentoriasmovil.network

import mx.edu.utez.mentoriasmovil.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // --- MENTORÍAS (MOVIL) ---
    @GET("api/mentorias/movil")
    suspend fun obtenerMentorias(): List<Mentoria>

    @GET("api/mentorias/movil/mentor/{id}")
    suspend fun obtenerMentoriasPorMentor(@Path("id") mentorId: Long): List<Mentoria>

    @PUT("api/mentorias/{id}/estado")
    suspend fun actualizarEstadoMentoria(
        @Path("id") id: Long,
        @Query("nuevoEstado") nuevoEstado: String
    ): Response<Unit>

    // --- INSCRIPCIONES ---
    @POST("api/mentorias-usuarios")
    suspend fun agendarMentoria(
        @Body request: InscripcionRequest,
        @Query("tema") tema: String
    ): Response<Unit>

    @GET("api/mentorias-usuarios/usuario/{usuarioId}/detalle")
    suspend fun obtenerMisMentorias(
        @Path("usuarioId") usuarioId: Long
    ): List<MentoriaDetalle>

    @GET("api/mentorias-usuarios/usuario/{usuarioId}/historial")
    suspend fun obtenerMiHistorial(
        @Path("usuarioId") usuarioId: Long
    ): List<MentoriaDetalle>

    // --- AUTH ---
    @POST("api/auth/login")
    suspend fun login(@Body loginDT0: LoginRequest): Response<LoginResponse>

    // --- MATERIAS ---
    @GET("api/materias")
    suspend fun listarMaterias(): List<Materia>

    @POST("api/materias")
    suspend fun crearMateria(@Body materia: Materia): Response<Materia>

    @PUT("api/materias/{id}")
    suspend fun actualizarMateria(@Path("id") id: Long, @Body materia: Materia): Response<Materia>

    @DELETE("api/materias/{id}")
    suspend fun eliminarMateria(@Path("id") id: Long): Response<Unit>

    // --- CARRERAS ---
    @GET("api/carreras")
    suspend fun listarCarreras(): List<Carrera>

    @POST("api/carreras")
    suspend fun crearCarrera(@Body carrera: Carrera): Response<Carrera>

    @PUT("api/carreras/{id}")
    suspend fun actualizarCarrera(@Path("id") id: Long, @Body carrera: Carrera): Response<Carrera>

    @DELETE("api/carreras/{id}")
    suspend fun eliminarCarrera(@Path("id") id: Long): Response<Unit>

    // --- EDIFICIOS Y ESPACIOS ---
    @GET("api/edificios")
    suspend fun listarEdificios(): List<Edificio>

    @GET("api/espacios")
    suspend fun getEspacios(): List<Espacio>

    @POST("api/mentorias")
    suspend fun crearMentoria(@Body mentoria: MentoriaRequest): Response<Unit>


    @GET("api/usuarios/estado/{nombreEstado}")
    suspend fun listarPorEstado(@Path("nombreEstado") estado: String): List<UserResponse>

    @POST("api/usuarios/cambiar-estado")
    suspend fun cambiarEstado(@Body payload: Map<String, Long>): Response<Unit>

    @DELETE("api/usuarios/{id}")
    suspend fun eliminarUsuario(@Path("id") id: Long): Response<Unit>

    @GET("api/mentorias-usuarios/conteo")
    suspend fun obtenerConteoInscritos(): Map<Long, Long>

}
