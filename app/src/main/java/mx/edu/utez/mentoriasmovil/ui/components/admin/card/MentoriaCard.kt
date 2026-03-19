package mx.edu.utez.mentoriasmovil.ui.components.admin.card

import androidx.compose.runtime.Composable
import mx.edu.utez.mentoriasmovil.ui.components.admin.subcomponents.DividerLine
import mx.edu.utez.mentoriasmovil.ui.components.admin.subcomponents.InfoRow

@Composable
fun MentoriaCard(
    fecha: String,
    hora: String,
    mentor: String,
    carrera: String,
    materia: String
) {
    BaseCard {
        InfoRow("Fecha:", fecha)
        DividerLine()

        InfoRow("Hora", hora)
        DividerLine()

        InfoRow("Mentor:", mentor)
        DividerLine()

        InfoRow("Carrera:", carrera)
        DividerLine()

        InfoRow("Materia:", materia)
    }
}