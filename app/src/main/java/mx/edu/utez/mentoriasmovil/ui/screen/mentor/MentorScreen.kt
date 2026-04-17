package mx.edu.utez.mentoriasmovil.ui.screen.mentor

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.utez.mentoriasmovil.model.Edificio
import mx.edu.utez.mentoriasmovil.model.Espacio
import mx.edu.utez.mentoriasmovil.model.Materia
import mx.edu.utez.mentoriasmovil.model.Mentoria
import mx.edu.utez.mentoriasmovil.ui.components.AddButton
import mx.edu.utez.mentoriasmovil.ui.components.ConfirmDialog
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
    var mentoriaParaCancelar by remember { mutableStateOf<Mentoria?>(null) }
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

    if (mentoriaParaCancelar != null) {
        ConfirmDialog(
            title = "Confirmar Cancelación",
            message = "¿Estás seguro de que deseas cancelar esta asesoría? Esta acción no se puede deshacer.",
            onDismiss = { mentoriaParaCancelar = null },
            onConfirm = {
                mentoriaParaCancelar?.let {
                    mentoriaViewModel.cambiarEstado(it.id, "CANCELADA", mentorId)
                }
                mentoriaParaCancelar = null
            }
        )
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AddButton(onClick = { showDialog = true })
                
                // --- PUNTITOS DE ESTADO (INDICADORES) ---
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusIndicator(color = Color(0xFF29D62F), text = "Agendada")
                    Spacer(modifier = Modifier.width(12.dp))
                    StatusIndicator(color = Color(0xFF1A237E), text = "Cancelada")
                }
            }

            if (showDialog) {
                AgregarMentoriaDialog(
                    fechaSeleccionada = dateEstado.selectedDateMillis,
                    onDismiss = { showDialog = false },
                    onGuardar = { mentoriaViewModel.obtenerMentoriasPorMentor(mentorId) },
                    mentorId = mentorId
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
            val isLoading = mentoriaViewModel.isLoading

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFF1A3B7A)
                    )
                } else if (mentorias.isEmpty()) {
                    Text(
                        text = "No hay mentorías programadas para este día.",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(mentorias) { mentoria ->
                            val estadoNombre = mentoria.estado?.nombre?.uppercase() ?: ""
                            val estadoEnum = when {
                                estadoNombre.contains("ACEPTADA") || estadoNombre.contains("ACEPTADO") -> cardEstado.ACEPTADA
                                estadoNombre.contains("CANCELADA") || estadoNombre.contains("CANCELADO") -> cardEstado.CANCELADA
                                estadoNombre.contains("PENDIENTE") -> cardEstado.PENDIENTE
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
                                cantidadActualAprendices = mentoria.conteoReal,
                                maxAprendices = mentoria.cupo ?: 0,
                                Aceptada = {
                                    mentoriaViewModel.cambiarEstado(mentoria.id, "ACEPTADA", mentorId)
                                },
                                Cancelada = {
                                    mentoriaParaCancelar = mentoria
                                }
                            )
                        }
                    }
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

@Composable
fun StatusIndicator(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarMentoriaDialog(
    fechaSeleccionada: Long?,
    onDismiss: () -> Unit,
    onGuardar: () -> Unit,
    mentorId: Long
) {
    val mentoriaViewModel: MentoriaViewModel = viewModel()
    val edificioViewModel: EdificioViewModel = viewModel()
    val materiaViewModel: MateriaViewModel = viewModel()

    LaunchedEffect(Unit) {
        edificioViewModel.obtenerEdificios()
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

    var errorMsj by remember { mutableStateOf<String?>(null) }

    val aulasFiltradas = edificioSeleccionado?.espacios ?: emptyList()

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
                if (errorMsj != null) {
                    Text(errorMsj!!, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                }

                OutlinedTextField(
                    value = fechaFormateada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Fecha") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = horaInicio,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Inicio") },
                        trailingIcon = {
                            IconButton(onClick = { showTimePickerInicio = true }) {
                                Icon(Icons.Default.AccessTime, contentDescription = null)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = horaFin,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Fin") },
                        trailingIcon = {
                            IconButton(onClick = { showTimePickerFin = true }) {
                                Icon(Icons.Default.AccessTime, contentDescription = null)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

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
                    expanded = expandedEdificio,
                    onExpandedChange = { expandedEdificio = !expandedEdificio }
                ) {
                    OutlinedTextField(
                        value = edificioSeleccionado?.nombre ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Docencia (Edificio)") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEdificio) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedEdificio,
                        onDismissRequest = { expandedEdificio = false }
                    ) {
                        edificioViewModel.edificios.forEach { ed ->
                            DropdownMenuItem(
                                text = { Text(ed.nombre) },
                                onClick = {
                                    edificioSeleccionado = ed
                                    aulaSeleccionada = null
                                    expandedEdificio = false
                                }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = expandedAula,
                    onExpandedChange = { 
                        if (edificioSeleccionado != null) expandedAula = !expandedAula 
                    }
                ) {
                    OutlinedTextField(
                        value = aulaSeleccionada?.nombre ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Aula") },
                        placeholder = { 
                            Text(
                                when {
                                    edificioSeleccionado == null -> "Selecciona docencia primero"
                                    aulasFiltradas.isEmpty() -> "No hay aulas disponibles"
                                    else -> "Selecciona aula"
                                }
                            ) 
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAula) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        enabled = edificioSeleccionado != null && aulasFiltradas.isNotEmpty()
                    )
                    
                    if (aulasFiltradas.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = expandedAula,
                            onDismissRequest = { expandedAula = false }
                        ) {
                            aulasFiltradas.forEach { esp ->
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
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancelar") }
                    Button(onClick = {
                        if (horaInicio.isNotBlank() && horaFin.isNotBlank() && horaFin <= horaInicio) {
                            errorMsj = "La hora de fin debe ser posterior a la de inicio"
                            return@Button
                        }

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
                                mentorId = mentorId
                            )
                            onGuardar()
                            onDismiss()
                        } else {
                            errorMsj = "Por favor completa todos los campos"
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
                    errorMsj = null
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
                    errorMsj = null
                }) { Text("Aceptar") }
            },
            text = { TimePicker(state = timeState) }
        )
    }
}
