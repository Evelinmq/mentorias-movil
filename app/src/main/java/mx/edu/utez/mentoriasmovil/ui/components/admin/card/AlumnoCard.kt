package mx.edu.utez.mentoriasmovil.ui.components.admin.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.mentoriasmovil.ui.theme.text_card_grey
import mx.edu.utez.mentoriasmovil.R

@Composable
fun AlumnoCard(
    nombre: String,
    apellidos: String,
    rol: String,
    carrera: String,
    correo: String,
    esPendiente: Boolean,
    onEditClick: () -> Unit = {},
    onDeleteClick: (() -> Unit)? = null,
    onBlockClick: (() -> Unit)? = null,
    onAcceptClick: () -> Unit = {},
    onRejectClick: () -> Unit = {}
) {
    val nombreCompleto = "$nombre $apellidos"

    BaseCard(
        // Si es pendiente, no mandamos los clics de edición/borrado a la base
        // para que esos botones no aparezcan, y viceversa.
        onEditClick = if (!esPendiente) onEditClick else null,
        onDeleteClick = if (!esPendiente) onDeleteClick else null,
        onBlockClick = if (!esPendiente) onBlockClick else null
    ) {
        // Badge (el cuadrito negro de arriba)
        Surface (
            color = Color.Black,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = rol,
                color = Color.White,
                fontSize = 10.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                fontWeight = FontWeight.Bold
            )
        }

        Text(text = nombreCompleto, color = text_card_grey, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = "Carrera: $carrera", color = text_card_grey, fontSize = 14.sp)
        Text(text = correo, color = text_card_grey, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(12.dp))

        if (esPendiente) {
            // VISTA PENDIENTES
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón Aceptar (Negro)
                ActionButton(
                    icon = R.drawable.tickicon,
                    color = Color.Black,
                    tint = Color.White,
                    onClick = onAcceptClick
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Botón Rechazar
                Box(modifier = Modifier
                        .border(1.dp,
                      Color.LightGray,
                      RoundedCornerShape(8.dp))
                ) {
                    ActionButton(
                        icon = R.drawable.crossicon,
                        color = Color.White,
                        tint = Color.Black,
                        onClick = onRejectClick
                    )
                }
            }
        }
    }
}