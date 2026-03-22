package mx.edu.utez.mentoriasmovil.ui.screen.aprendiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.ui.components.AprendizHeader
import mx.edu.utez.mentoriasmovil.ui.components.aprendiz.AprendizSearchBar
import mx.edu.utez.mentoriasmovil.ui.components.aprendiz.card.AgregarAsesoriaCard
import mx.edu.utez.mentoriasmovil.ui.nav.AprendizBottomBar
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme

@Composable
fun AgregarScreen(paddingValues: PaddingValues) {
    var asesoriaSeleccionada by remember { mutableStateOf<AsesoriaData?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    val mentoriasDisponibles = listOf(
        AsesoriaData(
            email = "20243ds148@utez.edu.mx",
            nombre = "Gustavo Diaz Peña",
            fecha = "30/01/2026",
            materia = "Matematica aplicada",
            ubicacion = "A2 - Docencia II",
            hora = "08:00 - 10:00",
            agendada = false
        ),
        AsesoriaData(
            email = "20243ds148@utez.edu.mx",
            nombre = "Gustavo Diaz Peña",
            fecha = "30/01/2026",
            materia = "Matematica aplicada",
            ubicacion = "A12 - Docencia V",
            hora = "13:00 - 14:00",
            agendada = false
        )
    )

    // Mostrar el diálogo si hay una asesoría seleccionada
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
        AprendizSearchBar(
            value = "Materia",
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(mentoriasDisponibles) { item ->
                // USAMOS EL NUEVO COMPONENTE NEGRO SIN PUNTO
                AgregarAsesoriaCard(
                    data = item,
                    onClick = {
                        asesoriaSeleccionada = item
                        mostrarDialogo = true
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AgregarScreenPreview() {
    MentoriasMovilTheme {
        Scaffold(
            topBar = { AprendizHeader(onLogout = {}) },
            bottomBar = {
                AprendizBottomBar(currentRoute = "Agregar", onNavigate = {})
            }
        ) { padding ->
            AgregarScreen(padding)
        }
    }
}
