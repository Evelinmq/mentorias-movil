package mx.edu.utez.mentoriasmovil.ui.screen.aprendiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.mentoriasmovil.ui.components.AprendizHeader
import mx.edu.utez.mentoriasmovil.ui.nav.AprendizBottomBar
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme

val StatusGreen = Color(0xFF4CAF50)
val StatusPending = Color(0xFFFFC107) // Restaurado a AMARILLO
val CardBackground = Color(0xFFF1F3F4)
val PrimaryBlue = Color(0xFF1A3B7A)

@Composable
fun AsesoriasScreen(paddingValues: PaddingValues) {
    var asesoriaSeleccionada by remember { mutableStateOf<AsesoriaData?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    val listaAsesorias = listOf(
        AsesoriaData(
            email = "20243ds148@utez.edu.mx",
            nombre = "Andres Manuel Lopez Obrador",
            fecha = "30/01/2026",
            materia = "Contaduría I",
            ubicacion = "A2 - Docencia II",
            hora = "13:00 - 14:00",
            agendada = true
        ),
        AsesoriaData(
            email = "20243ds145@utez.edu.mx",
            nombre = "Claudia Sheinbaum Pardo",
            fecha = "31/01/2026",
            materia = "Física II",
            ubicacion = "Laboratorio de Cómputo 1",
            hora = "10:00 - 11:30",
            agendada = false
        )
    )

    if (mostrarDialogo && asesoriaSeleccionada != null) {
        AsesoriaDetalleDialog(
            data = asesoriaSeleccionada!!,
            onDismiss = { mostrarDialogo = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            EstadoItem(texto = "Agendada", color = StatusGreen)
            EstadoItem(texto = "Aceptación pendiente", color = StatusPending)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(listaAsesorias) { asesoria ->
                AsesoriaCard(
                    data = asesoria,
                    onClick = {
                        asesoriaSeleccionada = asesoria
                        mostrarDialogo = true
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun AsesoriaDetalleDialog(data: AsesoriaData, onDismiss: () -> Unit) {
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
                DetailItem(
                    label = "Estado",
                    value = if (data.agendada) "Agendada" else "Aceptación pendiente",
                    valueColor = if (data.agendada) StatusGreen else StatusPending
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cerrar", color = Color.White)
            }
        }
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

@Composable
fun AsesoriaCard(
    data: AsesoriaData,
    onClick: () -> Unit,
    indicadorColor: Color = if (data.agendada) StatusGreen else StatusPending, // Color personalizable
    mostrarPunto: Boolean = true // Opción para ocultar el punto
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            // Línea lateral con el color seleccionado
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight()
                    .background(indicadorColor)
            )

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = data.email,
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xFF5F6368)
                    )
                    Column(horizontalAlignment = Alignment.End) {
                        // PUNTO INDICADOR (Condicional)
                        if (mostrarPunto) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(indicadorColor)
                            )
                        }
                        Text(
                            text = data.fecha,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5F6368)
                        )
                    }
                }

                Text(
                    text = data.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Materia:", fontSize = 14.sp, color = Color.Gray)
                Text(
                    text = data.materia,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = data.ubicacion, fontSize = 14.sp, color = Color.DarkGray)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = data.hora, fontSize = 14.sp, color = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
fun EstadoItem(texto: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = texto, fontSize = 12.sp, color = Color.Gray)
    }
}


data class AsesoriaData(
    val email: String,
    val nombre: String,
    val fecha: String,
    val materia: String,
    val ubicacion: String,
    val hora: String,
    val agendada: Boolean
)

@Preview(showSystemUi = true)
@Composable
fun AsesoriasScreenPreview() {
    MentoriasMovilTheme {
        Scaffold(
            topBar = { AprendizHeader(onLogout = {}) },
            bottomBar = { AprendizBottomBar(currentRoute = "Asesorias", onNavigate = {}) }
        ) { padding ->
            AsesoriasScreen(padding)
        }
    }
}
