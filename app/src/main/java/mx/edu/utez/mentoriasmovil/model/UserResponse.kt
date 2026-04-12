package mx.edu.utez.mentoriasmovil.model

data class CarreraResponse(
    val id: Long,
    val nombre: String
)

data class RolResponse(
    val id: Long,
    val nombre: String
)
data class UserResponse(
    val id: Long,
    val nombre: String,
    val apellidoP: String,
    val apellidoM: String,
    val correo: String,
    val carrera: CarreraResponse?,
    val roles: List<RolResponse>?,
    val estado: String? // O el objeto si el DTO lo manda completo
)