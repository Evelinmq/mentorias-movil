package mx.edu.utez.mentoriasmovil.ui.screen.mentor

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.utez.mentoriasmovil.model.Edificio
import mx.edu.utez.mentoriasmovil.model.Espacio
import mx.edu.utez.mentoriasmovil.model.Materia
import mx.edu.utez.mentoriasmovil.ui.components.AddButton
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.components.mentor.cardEstado
import mx.edu.utez.mentoriasmovil.ui.components.mentor.cardMentor
import mx.edu.utez.mentoriasmovil.viewmodel.EdificioViewModel
import mx.edu.utez.mentoriasmovil.viewmodel.EspacioViewModel
import mx.edu.utez.mentoriasmovil.viewmodel.MateriaViewModel
import mx.edu.utez.mentoriasmovil.viewmodel.MentoriaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MentorScreen(navController: NavController, mentorId: Long) {
    var showDialog by remember { mutableStateOf(false) }
    val dateEstado = rememberDatePickerState()
    val mentoriaViewModel: MentoriaViewModel = viewModel()

    LaunchedEffect(Unit) {
        mentoriaViewModel.obtenerMentoriasPorMentor(mentorId)
    }

    val fechaMs = dateEstado.selectedDateMillis
    LaunchedEffect(fechaMs) {
        fechaMs?.let {
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val fechaFormateada = sdf.format(java.util.Date(it))
            mentoriaViewModel.filtrarPorFecha(fechaFormateada)
        }
    }

    Scaffold(
        topBar = {
            MainHeader(onLogout = {
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AddButton(onClick = { showDialog = true })

            if (showDialog) {
                AgregarMentoriaDialog(
                    fechaSeleccionada = dateEstado.selectedDateMillis,
                    onDismiss = { showDialog = false },
                    onGuardar = { mentoriaViewModel.obtenerMentoriasPorMentor(mentorId) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF5F5F5),
                tonalElevation = 2.dp
            ) {
                DatePicker(
                    state = dateEstado,
                    title = null,
                    headline = null,
                    showModeToggle = false
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val mentorias = mentoriaViewModel.mentoriasFiltradas

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(mentorias) { mentoria ->
                    val estadoEnum = when (mentoria.estado?.uppercase()) {
                        "ACEPTADA" -> cardEstado.ACEPTADA
                        "CANCELADA" -> cardEstado.CANCELADA
                        "PENDIENTE" -> cardEstado.PENDIENTE
                        else -> cardEstado.SIN_ALUMNOS
                    }

                    cardMentor(
                        correo = mentoria.email ?: "sin_correo@utez.edu.mx",
                        fecha = mentoria.fecha,
                        nombre = mentoria.mentor ?: "Sin nombre",
                        materia = mentoria.materia ?: "Sin materia",
                        tema = mentoria.tema ?: "Sin tema",
                        sitio = mentoria.espacio ?: "Sin ubicación",
                        tiempo = "${mentoria.horaInicio} - ${mentoria.horaFin}",
                        estado = estadoEnum,
                        cantidadActualAprendices = mentoria.alumnos?.size ?: 0,
                        maxAprendices = mentoria.cupo ?: 0,
                        Aceptada = {
                            mentoriaViewModel.cambiarEstado(mentoria.id, "ACEPTADA", mentorId)
                        },
                        Cancelada = {
                            mentoriaViewModel.cambiarEstado(mentoria.id, "CANCELADA", mentorId)
                        }
                    )
                }
            }

            if (fechaMs != null) {
                val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                val fechaFormateada = sdf.format(java.util.Date(fechaMs))
                Text(
                    text = "Fecha seleccionada: $fechaFormateada",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarMentoriaDialog(
    fechaSeleccionada: Long?,
    onDismiss: () -> Unit,
    onGuardar: () -> Unit
) {
    val mentoriaViewModel: MentoriaViewModel = viewModel()
    val edificioViewModel: EdificioViewModel = viewModel()
    val espacioViewModel: EspacioViewModel = viewModel()
    val materiaViewModel: MateriaViewModel = viewModel()

    LaunchedEffect(Unit) {
        edificioViewModel.obtenerEdificios()
        espacioViewModel.obtenerEspacios()
        materiaViewModel.obtenerMaterias()
    }

    var edificioSeleccionado by remember { mutableStateOf<Edificio?>(null) }
    var expandedEdificio by remember { mutableStateOf(false) }
    var aulaSeleccionada by remember { mutableStateOf<Espacio?>(null) }
    var expandedAula by remember { mutableStateOf(false) }
    var materiaSeleccionada by remember { mutableStateOf<Materia?>(null) }
    var expandedMateria by remember { mutableStateOf(false) }

    var horaInicio by remember { mutableStateOf("") }
    var horaFin by remember { mutableStateOf("") }
    var showTimePickerInicio by remember { mutableStateOf(false) }
    var showTimePickerFin by remember { mutableStateOf(false) }

    var errorFecha by remember { mutableStateOf("") }
    var errorHoraInicio by remember { mutableStateOf("") }
    var errorHoraFin by remember { mutableStateOf("") }

    val fechaFormateada = remember(fechaSeleccionada) {
        fechaSeleccionada?.let {
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            sdf.format(java.util.Date(it))
        } ?: ""
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {},
        title = { Text("Agregar mentoría") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = fechaFormateada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Fecha") },
                    isError = errorFecha.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = horaInicio,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Hora inicio") },
                    trailingIcon = {
                        IconButton(onClick = { showTimePickerInicio = true }) {
                            Icon(Icons.Default.AccessTime, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = horaFin,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Hora fin") },
                    trailingIcon = {
                        IconButton(onClick = { showTimePickerFin = true }) {
                            Icon(Icons.Default.AccessTime, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = expandedMateria,
                    onExpandedChange = { expandedMateria = !expandedMateria }
                ) {
                    OutlinedTextField(
                        value = materiaSeleccionada?.nombre ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Materia") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMateria) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedMateria,
                        onDismissRequest = { expandedMateria = false }
                    ) {
                        materiaViewModel.listaMaterias.forEach { mat ->
                            DropdownMenuItem(
                                text = { Text(mat.nombre ?: "") },
                                onClick = {
                                    materiaSeleccionada = mat
                                    expandedMateria = false
                                }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = expandedAula,
                    onExpandedChange = { expandedAula = !expandedAula }
                ) {
                    OutlinedTextField(
                        value = aulaSeleccionada?.nombre ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Aula") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAula) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAula,
                        onDismissRequest = { expandedAula = false }
                    ) {
                        espacioViewModel.espacios.forEach { esp ->
                            DropdownMenuItem(
                                text = { Text(esp.nombre) },
                                onClick = {
                                    aulaSeleccionada = esp
                                    expandedAula = false
                                }
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancelar") }
                    Button(onClick = {
                        if (fechaFormateada.isNotBlank() && horaInicio.isNotBlank() && 
                            horaFin.isNotBlank() && materiaSeleccionada != null && 
                            aulaSeleccionada != null) {
                            mentoriaViewModel.crearMentoria(
                                fecha = fechaFormateada,
                                horaInicio = horaInicio,
                                horaFin = horaFin,
                                cuatrimestre = materiaSeleccionada?.cuatrimestre ?: 0,
                                cupo = 5,
                                materiaId = materiaSeleccionada?.id ?: 0,
                                espacioId = aulaSeleccionada?.id ?: 0,
                                mentorId = 1
                            )
                            onGuardar()
                            onDismiss()
                        }
                    }) {
                        Text("Guardar")
                    }
                }
            }
        }
    )

    if (showTimePickerInicio) {
        val timeState = rememberTimePickerState()
        AlertDialog(
            onDismissRequest = { showTimePickerInicio = false },
            confirmButton = {
                TextButton(onClick = {
                    horaInicio = String.format("%02d:%02d", timeState.hour, timeState.minute)
                    showTimePickerInicio = false
                }) { Text("Aceptar") }
            },
            text = { TimePicker(state = timeState) }
        )
    }

    if (showTimePickerFin) {
        val timeState = rememberTimePickerState()
        AlertDialog(
            onDismissRequest = { showTimePickerFin = false },
            confirmButton = {
                TextButton(onClick = {
                    horaFin = String.format("%02d:%02d", timeState.hour, timeState.minute)
                    showTimePickerFin = false
                }) { Text("Aceptar") }
            },
            text = { TimePicker(state = timeState) }
        )
    }
}
