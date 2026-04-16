package mx.edu.utez.mentoriasmovil.model

class RegistroDTO (
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val email: String,
    val password: String,
    val rolesIds: List<Long>,
    val carreraId: Long
)