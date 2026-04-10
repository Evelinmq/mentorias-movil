package mx.edu.utez.mentoriasmovil.model

data class Mentoria(
    val id: Long,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val cupo: Int?,
    val espacio: String?,
    val materia: String?
)
