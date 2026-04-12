package mx.edu.utez.mentoriasmovil.ui.screen.aprendiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

val HistorialStatusGreen = Color(0xFF22C55E)

@Composable
fun HistorialScreen(
    paddingValues: PaddingValues,
    viewModel: AprendizViewModel = viewModel()
) {
    // Estado para el diálogo de detalles
    var asesoriaSeleccionada by remember { mutableStateOf<AsesoriaData?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    // Usamos la lista de agendadas del ViewModel
    val listaHistorial = viewModel.listaHistorial

    // Mostrar el diálogo si se selecciona un ítem
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(HistorialStatusGreen)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Asesoría agendada",
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }

        if (listaHistorial.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aún no has agendado asesorías.",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(listaHistorial) { item ->
                    AsesoriaCard(
                        data = item,
                        onClick = {
                            asesoriaSeleccionada = item
                            mostrarDialogo = true
                        },
                        indicadorColor = HistorialStatusGreen
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HistorialScreenPreview() {
    MentoriasMovilTheme {
        Scaffold(
            topBar = { AprendizHeader(onLogout = {}) },
            bottomBar = { AprendizBottomBar(currentRoute = "Historial", onNavigate = {}) }
        ) { padding ->
            HistorialScreen(padding)
        }
    }
}
