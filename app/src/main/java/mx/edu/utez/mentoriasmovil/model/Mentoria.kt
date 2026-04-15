package mx.edu.utez.mentoriasmovil.model

data class Mentoria(
    val id: Long,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val cupo: Int?,
    val espacio: String?,
    val materia: String?,
    val mentor: String?,
    val email: String?,
    val estado: String? = "PENDIENTE",
    val tema: String? = "Sin tema",
    val alumnos: List<Any>? = null
)
