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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.mentoriasmovil.model.AsesoriaData
import mx.edu.utez.mentoriasmovil.ui.components.AprendizHeader
import mx.edu.utez.mentoriasmovil.ui.components.aprendiz.AsesoriaDetalleDialog
import mx.edu.utez.mentoriasmovil.ui.nav.AprendizBottomBar
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme
import mx.edu.utez.mentoriasmovil.viewmodel.AprendizViewModel

val StatusGreen = Color(0xFF4CAF50)
val StatusPending = Color(0xFFFFC107)
val CardBackground = Color(0xFFF1F3F4)
val PrimaryBlue = Color(0xFF1A3B7A)

@Composable
fun AsesoriaScreen(
    paddingValues: PaddingValues,
    viewModel: AprendizViewModel = viewModel()
) {
    var asesoriaSeleccionada by remember { mutableStateOf<AsesoriaData?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    val listaAsesorias = viewModel.listaAsesoriasAgendadas
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage

    if (mostrarDialogo && asesoriaSeleccionada != null) {
        AsesoriaDetalleDialog(
            data = asesoriaSeleccionada!!,
            onDismiss = { mostrarDialogo = false },
            onConfirm = null, // No hay botón de confirmar porque ya están agendadas
            confirmText = "Cerrar"
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

        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = PrimaryBlue
                )
            } else if (error != null) {
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else if (listaAsesorias.isEmpty()) {
                Text(
                    text = "Aún no tienes asesorías programadas.\nVe a la sección 'Agregar' para buscar una.",
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            } else {
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
    }
}

@Composable
fun AsesoriaCard(
    data: AsesoriaData,
    onClick: () -> Unit,
    indicadorColor: Color = StatusGreen,
    mostrarPunto: Boolean = true
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
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
