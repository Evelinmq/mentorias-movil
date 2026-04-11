package mx.edu.utez.mentoriasmovil.ui.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.components.admin.card.MentoriaCard
import mx.edu.utez.mentoriasmovil.ui.nav.AdminBottomBar
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme
import mx.edu.utez.mentoriasmovil.viewmodel.MentoriaViewModel

@Composable
fun HistorialContent(paddingValues: PaddingValues, viewModel: MentoriaViewModel = viewModel()) {

    var mentor by remember { mutableStateOf("") }
    var materiaBusqueda by remember { mutableStateOf("") }

    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.obtenerDatos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = mentor,
                onValueChange = { mentor = it },
                label = { Text("Buscar mentor") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = materiaBusqueda,
                onValueChange = { materiaBusqueda = it },
                label = { Text("Buscar materia") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedTextField(
                    value = fechaInicio,
                    onValueChange = { fechaInicio = it },
                    label = { Text("Fecha inicio (dd/MM/yyyy)") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = fechaFin,
                    onValueChange = { fechaFin = it },
                    label = { Text("Fecha fin (dd/MM/yyyy)") },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(viewModel.listarMentorias) { mentoria ->
                MentoriaCard(
                    fecha = mentoria.fecha,
                    hora = "${mentoria.horaInicio}-${mentoria.horaFin}",
                    mentor = "Mentor ID: ${mentoria.id}",
                    carrera = "Carrera", // Ajustar si el modelo tiene carrera
                    materia = mentoria.materia ?: "Sin materia"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HistorialPreview() {
    MentoriasMovilTheme {
        Scaffold(
            topBar = { MainHeader(onLogout = {}) },
            bottomBar = {
                AdminBottomBar(
                    currentRoute = "historial",
                    onNavigate = {}
                )
            }
        ) { paddingValues ->
            HistorialContent(paddingValues)
        }
    }
}
