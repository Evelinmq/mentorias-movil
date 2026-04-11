package mx.edu.utez.mentoriasmovil.model

// Estos modelos sirven para la inscripción (agendar)
data class UsuarioId(val id: Long)
data class MentoriaId(val id: Long)

data class InscripcionRequest(
    val usuario: UsuarioId,
    val mentoria: MentoriaId
)
