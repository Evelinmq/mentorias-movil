package mx.edu.utez.mentoriasmovil.model

import com.google.gson.annotations.SerializedName

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
    val estado: EstadoMentoria?,
    val tema: String? = "Sin tema",
    val alumnos: List<Any>? = null,
    // Campo extra para la UI de la App
    var conteoReal: Int = 0
)

data class EstadoMentoria(
    val id: Long,
    val nombre: String?
)
