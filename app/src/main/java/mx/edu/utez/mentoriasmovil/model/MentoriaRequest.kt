package mx.edu.utez.mentoriasmovil.model

class MentoriaRequest(
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val cuatrimestre: Int,
    val cupo: Int,
    val espacio: IdWrapper,
    val estado: IdWrapper,
    val mentor: IdWrapper,
    val materia: IdWrapper
)

data class IdWrapper(
    val id: Long?
)