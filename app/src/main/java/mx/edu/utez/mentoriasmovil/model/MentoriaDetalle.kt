package mx.edu.utez.mentoriasmovil.model

data class MentoriaDetalle(
    val id: Long,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String,
    val cupo: Int?,
    val espacio: EspacioDetalle?,
    val mentor: MentorDetalle?,
    val materia: MateriaDetalle?,
    val estado: EstadoDetalle?,
    val alumnos: List<Any>? = null
)

data class EspacioDetalle(val id: Long?, val nombre: String?)
data class MateriaDetalle(val id: Long?, val nombre: String?)
data class EstadoDetalle(val id: Long?, val nombre: String?)
data class MentorDetalle(
    val id: Long?,
    val nombre: String?,
    val apellidoP: String?,
    val correo: String?
)