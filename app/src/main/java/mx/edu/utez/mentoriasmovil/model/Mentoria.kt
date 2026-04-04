package mx.edu.utez.mentoriasmovil.model

data class Mentoria(
    val id: Long,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val cuatrimestre: Int,
    val cupo: Int
)
