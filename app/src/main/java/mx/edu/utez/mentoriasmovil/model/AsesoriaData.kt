package mx.edu.utez.mentoriasmovil.model

data class AsesoriaData(
    val id: Long = 0,
    val email: String,
    val nombre: String,
    val fecha: String,
    val materia: String,
    val ubicacion: String,
    val hora: String,
    var agendada: Boolean = false
)
