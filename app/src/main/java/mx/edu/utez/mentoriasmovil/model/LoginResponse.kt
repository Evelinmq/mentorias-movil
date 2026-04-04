package mx.edu.utez.mentoriasmovil.model

data class LoginResponse(
    val token: String,
    val role: String,
    val nombre: String
)