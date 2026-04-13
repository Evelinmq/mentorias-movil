package mx.edu.utez.mentoriasmovil.ui.screen.aprendiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.mentoriasmovil.model.AsesoriaData
import mx.edu.utez.mentoriasmovil.ui.components.aprendiz.AprendizSearchBar
import mx.edu.utez.mentoriasmovil.ui.components.aprendiz.AsesoriaDetalleDialog
import mx.edu.utez.mentoriasmovil.ui.components.aprendiz.card.AgregarAsesoriaCard
import mx.edu.utez.mentoriasmovil.viewmodel.AprendizViewModel

@Composable
fun AgregarScreen(
    paddingValues: PaddingValues,
    viewModel: AprendizViewModel = viewModel(),
    onAgendadoExitoso: () -> Unit = {}
) {
    var asesoriaSeleccionada by remember { mutableStateOf<AsesoriaData?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage
    val snackbarHostState = remember { SnackbarHostState() }

    // Todas las mentorías — disponibles + ya inscritas
    val todasLasMentorias = viewModel.listaFiltrada +
            viewModel.listaAsesoriasAgendadas.filter { agendada ->
                viewModel.listaFiltrada.none { it.id == agendada.id }
            }

    LaunchedEffect(viewModel.mensajeExito) {
        viewModel.mensajeExito?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.mensajeExito = null
            viewModel.cargarTodo()
            onAgendadoExitoso()
        }
    }

    if (mostrarDialogo && asesoriaSeleccionada != null) {
        AsesoriaDetalleDialog(
            data = asesoriaSeleccionada!!,
            onDismiss = { mostrarDialogo = false },
            onConfirm = { tema ->
                viewModel.agendarAsesoria(asesoriaSeleccionada!!, tema)
                mostrarDialogo = false
            },
            confirmText = "Agendar"
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            AprendizSearchBar(
                value = viewModel.filtroMateria ?: "Materia",
                opciones = viewModel.materiasDisponibles,
                onSeleccion = { viewModel.filtrarPorMateria(it) },
                modifier = Modifier.padding(16.dp)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFF1A3B7A)
                    )
                } else if (error != null) {
                    Text(
                        text = error,
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                } else if (todasLasMentorias.isEmpty()) {
                    Text(
                        text = "No hay asesorías disponibles.",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        color = Color.Gray
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(todasLasMentorias) { item ->
                            AgregarAsesoriaCard(
                                data = item,
                                onClick = {
                                    if (!item.agendada) {
                                        asesoriaSeleccionada = item
                                        mostrarDialogo = true
                                    }
                                },
                                yaInscrito = item.agendada
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}