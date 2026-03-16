package mx.edu.utez.mentoriasmovil.ui.components.admin.card

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.mentoriasmovil.ui.theme.button_grey
import mx.edu.utez.mentoriasmovil.ui.theme.text_card_grey

@Composable
fun MateriaCard(
    carrera: String,
    materia: String,
    cuatrimestre: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    BaseCard(
        onEditClick = onEditClick,
        onDeleteClick = onDeleteClick
    ) {
        Text(text = "Carrera:", color = Color.Gray, fontSize = 12.sp)
        Text(text = carrera, color = Color(0xFF1A3567), fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Materia: $materia", color = Color(0xFF1A3567), fontSize = 15.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Cuatrimestre: $cuatrimestre", color = Color(0xFF1A3567), fontSize = 15.sp)
    }
}