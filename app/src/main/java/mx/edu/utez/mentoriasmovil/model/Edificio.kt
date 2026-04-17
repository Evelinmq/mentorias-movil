package mx.edu.utez.mentoriasmovil.model

data class Edificio(
    val id: Long,
    val nombre: String,
    val espacios: List<Espacio>? = null
)
