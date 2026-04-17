package mx.edu.utez.mentoriasmovil.ui.components.mentor

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue

enum class cardEstado(val color: Color) {
    CANCELADA(Color(0xFF1A237E)), // Azul institucional (del header)
    ACEPTADA(Color(0xFF29D62F)),  // Verde brillante como el de la imagen
    PENDIENTE(Color(0xFFD62974)),
    SIN_ALUMNOS(Color(0xFF9DB4E3)),
}

@Composable
fun cardMentor(
    correo: String,
    fecha: String,
    nombre: String,
    materia: String,
    tema: String,
    sitio: String,
    tiempo: String,
    estado: cardEstado,
    cantidadActualAprendices: Int? = null,
    maxAprendices: Int? = null,
    Aceptada: () -> Unit = {},
    Cancelada: () -> Unit = {}
) {
    val animaciónEstadoCard by animateColorAsState(targetValue = estado.color, label = "color")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(Color(0xFFEFEFF0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(6.dp)
                    .background(animaciónEstadoCard)
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = correo,
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color.Gray
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = fecha, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(animaciónEstadoCard, CircleShape)
                        )
                    }
                }

                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), thickness = 0.5.dp, color = Color.LightGray)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Row {
                        Text("Materia: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(materia, fontSize = 14.sp)
                    }
                    if (cantidadActualAprendices != null && maxAprendices != null) {
                        Row {
                            Text("Alumnos: ", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text("$cantidadActualAprendices/$maxAprendices", fontSize = 12.sp)
                        }
                    }
                }

                Row(modifier = Modifier.padding(top = 4.dp)) {
                    Text("Tema: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(tema, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconDetail(icon = Icons.Default.LocationOn, text = sitio)
                    Spacer(modifier = Modifier.width(16.dp))
                    IconDetail(icon = Icons.Default.Schedule, text = tiempo)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = Cancelada,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD5D7DA)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Text("Cancelar", fontSize = 12.sp, color = Color.DarkGray)
                    }

                    if (estado == cardEstado.PENDIENTE) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = Aceptada,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        ) {
                            Text("Aceptar", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IconDetail(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 12.sp, color = Color.DarkGray)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardMentor() {
    cardMentor(
        correo = "20243ds148@utez.edu.mx",
        fecha = "11/01/2026",
        nombre = "Kimberly Guadalupe Loaiza Martínez",
        materia = "Estructuras de programación",
        tema = "Ciclos y estructuras de control",
        sitio = "A2 - Docencia II",
        tiempo = "13:00 - 14:00",
        estado = cardEstado.SIN_ALUMNOS
    )
}
