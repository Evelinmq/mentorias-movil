package mx.edu.utez.mentoriasmovil.ui.components.admin

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.ui.theme.button_grey
import mx.edu.utez.mentoriasmovil.ui.theme.card_grey
import mx.edu.utez.mentoriasmovil.ui.theme.text_card_grey

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(24.dp),
        containerColor = card_grey,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = text_card_grey
            )
        },
        text = {
            Text(text = message, color = Color.Black)
        },
        confirmButton = {
            Button (
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Aceptar", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = button_grey),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cancelar", color = Color.White)
            }
        }
    )
}