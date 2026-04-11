package mx.edu.utez.mentoriasmovil.ui.components.aprendiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.mentoriasmovil.model.AsesoriaData

private val PrimaryBlue = Color(0xFF1A3B7A)
private val StatusGreen = Color(0xFF4CAF50)
private val StatusPending = Color(0xFFFFC107)

@Composable
fun AsesoriaDetalleDialog(
    data: AsesoriaData,
    onDismiss: () -> Unit,
    onConfirm: (() -> Unit)? = null,
    confirmText: String = "Cerrar"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(24.dp),
        containerColor = Color.White,
        title = {
            Text(
                text = "Detalles de la Asesoría",
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                DetailItem(label = "Mentor", value = data.nombre)
                DetailItem(label = "Email", value = data.email)
                DetailItem(label = "Materia", value = data.materia)
                DetailItem(label = "Fecha", value = data.fecha)
                DetailItem(label = "Hora", value = data.hora)
                DetailItem(label = "Ubicación", value = data.ubicacion)
                if (data.agendada) {
                    DetailItem(label = "Estado", value = "Agendada", valueColor = StatusGreen)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm ?: onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (onConfirm != null) "Agendar" else "Cerrar", color = Color.White)
            }
        },
        dismissButton = if (onConfirm != null) {
            {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar", color = Color.Gray)
                }
            }
        } else null
    )
}

@Composable
fun DetailItem(label: String, value: String, valueColor: Color = Color.Black) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = valueColor
        )
    }
}
