package mx.edu.utez.mentoriasmovil.ui.screen.mentor

import android.R.attr.onClick
import android.util.Log
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.ui.components.AddButton
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader

import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme

import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.utez.mentoriasmovil.model.Edificio
import mx.edu.utez.mentoriasmovil.model.Espacio
import mx.edu.utez.mentoriasmovil.model.Materia
import mx.edu.utez.mentoriasmovil.viewmodel.EdificioViewModel
import mx.edu.utez.mentoriasmovil.viewmodel.EspacioViewModel
import mx.edu.utez.mentoriasmovil.viewmodel.MateriaViewModel
import mx.edu.utez.mentoriasmovil.viewmodel.MentoriaViewModel


@Composable
fun MentorScreen(navController: NavController) {

    var showDialog by remember { mutableStateOf(false) }
    val dateEstado = rememberDatePickerState()

    // ✅ ViewModel ARRIBA
    val mentoriaViewModel: MentoriaViewModel = viewModel()

    // ✅ CARGA INICIAL
    LaunchedEffect(Unit) {
        mentoriaViewModel.obtenerDatos()
    }

    val fechaMs = dateEstado.selectedDateMillis

    // ✅ FILTRO POR FECHA
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

            // BOTÓN AGREGAR
            AddButton(onClick = { showDialog = true })

            if (showDialog) {
                AgregarMentoriaDialog(
                    fechaSeleccionada = dateEstado.selectedDateMillis,
                    onDismiss = { showDialog = false },
                    onGuardar = {}
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // CALENDARIO
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFE8E7E7),
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

            // ✅ LISTA DE MENTORÍAS
            val mentorias = mentoriaViewModel.mentoriasFiltradas

            mentorias.forEach { mentoria ->

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    tonalElevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {

                        Text(text = "Materia: ${mentoria.materia}")
                        Text(text = "Hora: ${mentoria.horaInicio} - ${mentoria.horaFin}")
                        Text(text = "Cupo: ${mentoria.cupo}")

                    }
                }
            }

            // FECHA SELECCIONADA
            if (fechaMs != null) {
                val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                val fechaFormateada = sdf.format(java.util.Date(fechaMs))

                Text(text = "Fecha seleccionada: $fechaFormateada")
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
//edificio backend
    val viewModel: EdificioViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.obtenerEdificios()
    }
    var edificioSeleccionado by remember { mutableStateOf<Edificio?>(null) }
    var expanded by remember { mutableStateOf(false) }

    //aulas backend
    val espacioViewModel: EspacioViewModel = viewModel()

    LaunchedEffect(Unit) {
        espacioViewModel.obtenerEspacios()
    }

    var aulaSeleccionada by remember { mutableStateOf<Espacio?>(null) }
    var expandedAula by remember { mutableStateOf(false) }
//materia y cuatris backend

    val materiaViewModel: MateriaViewModel = viewModel()
    LaunchedEffect(Unit) {
        materiaViewModel.obtenerMaterias()
    }
    var materiaSeleccionada by remember { mutableStateOf<Materia?>(null) }
    var expandedMateria by remember { mutableStateOf(false) }

    // ------------------ STATES ------------------
    var horaInicio by remember { mutableStateOf("") }
    var horaFin by remember { mutableStateOf("") }
    var cuatrimestre by remember { mutableStateOf("") }
    var materia by remember { mutableStateOf("") }
    var aula by remember { mutableStateOf("") }
    var edificio by remember { mutableStateOf("") }

    // TimePickers
    var showTimePickerInicio by remember { mutableStateOf(false) }
    var showTimePickerFin by remember { mutableStateOf(false) }

    // ERRORES
    var errorFecha by remember { mutableStateOf("") }
    var errorHoraInicio by remember { mutableStateOf("") }
    var errorHoraFin by remember { mutableStateOf("") }
    var errorCuatrimestre by remember { mutableStateOf("") }
    var errorMateria by remember { mutableStateOf("") }
    var errorAula by remember { mutableStateOf("") }
    var errorEdificio by remember { mutableStateOf("") }

    // ------------------ FECHA ------------------
    val fechaFormateada = remember(fechaSeleccionada) {
        fechaSeleccionada?.let {
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            sdf.format(java.util.Date(it))
        } ?: ""
    }

    // ------------------ UI ------------------
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {},
        title = { Text("Agregar mentoría") },
        text = {

            Column {

                // -------- FECHA --------
                OutlinedTextField(
                    value = fechaFormateada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Fecha") },
                    isError = errorFecha.isNotEmpty(),
                    supportingText = {
                        if (errorFecha.isNotEmpty()) Text(errorFecha)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // -------- HORA INICIO --------
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
                    isError = errorHoraInicio.isNotEmpty(),
                    supportingText = {
                        if (errorHoraInicio.isNotEmpty()) Text(errorHoraInicio)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // -------- HORA FIN --------
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
                    isError = errorHoraFin.isNotEmpty(),
                    supportingText = {
                        if (errorHoraFin.isNotEmpty()) Text(errorHoraFin)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // -------- CUATRIMESTRE --------
                OutlinedTextField(
                    value = materiaSeleccionada?.cuatrimestre?.toString() ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Cuatrimestre") },
                    modifier = Modifier.fillMaxWidth()
                )

                // -------- MATERIA --------
                ExposedDropdownMenuBox(
                    expanded = expandedMateria,
                    onExpandedChange = { expandedMateria = !expandedMateria }
                ) {

                    OutlinedTextField(
                        value = materiaSeleccionada?.nombre ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Materia") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMateria)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedMateria,
                        onDismissRequest = { expandedMateria = false }
                    ) {
                        materiaViewModel.listaMaterias.forEach { materia ->

                            DropdownMenuItem(
                                text = { Text(materia.nombre ?: "") },
                                onClick = {
                                    materiaSeleccionada = materia
                                    expandedMateria = false
                                }
                            )
                        }
                    }
                }

                // -------- AULA --------
                ExposedDropdownMenuBox(
                    expanded = expandedAula,
                    onExpandedChange = { expandedAula = !expandedAula }
                ) {

                    OutlinedTextField(
                        value = aulaSeleccionada?.nombre ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Aula") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAula)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedAula,
                        onDismissRequest = { expandedAula = false }
                    ) {

                        espacioViewModel.espacios.forEach { espacio ->
                            DropdownMenuItem(
                                text = { Text(espacio.nombre) },
                                onClick = {
                                    aulaSeleccionada = espacio
                                    expandedAula = false
                                }
                            )
                        }
                    }
                }

                // -------- EDIFICIO --------
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {

                    OutlinedTextField(
                        value = edificioSeleccionado?.nombre ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Edificio") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        viewModel.edificios.forEach { edificio ->
                            DropdownMenuItem(
                                text = { Text(edificio.nombre) },
                                onClick = {
                                    edificioSeleccionado = edificio
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // -------- BOTONES --------
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            Log.d("TEST_CLICK", "BOTON FUNCIONA")

                            var isValid = true

                            if (fechaFormateada.isBlank()) {
                                errorFecha = "Selecciona una fecha"
                                isValid = false
                            }

                            if (horaInicio.isBlank()) {
                                errorHoraInicio = "Campo obligatorio"
                                isValid = false
                            }

                            if (horaFin.isBlank()) {
                                errorHoraFin = "Campo obligatorio"
                                isValid = false
                            }

                            if (horaInicio.isNotBlank() && horaFin.isNotBlank()) {
                                if (horaFin <= horaInicio) {
                                    errorHoraFin = "Debe ser mayor a hora inicio"
                                    isValid = false
                                }
                            }

                            if (materiaSeleccionada == null) {
                                errorMateria = "Selecciona una materia"
                                isValid = false
                            }

                            if (aulaSeleccionada == null) {
                                errorAula = "Selecciona un aula"
                                isValid = false
                            }

                            if (edificioSeleccionado == null) {
                                errorEdificio = "Selecciona un edificio"
                                isValid = false
                            }

                            if (!isValid) return@Button

                            mentoriaViewModel.crearMentoria(
                                fecha = fechaFormateada,
                                horaInicio = horaInicio,
                                horaFin = horaFin,
                                cuatrimestre =  materiaSeleccionada?.cuatrimestre ?: 0,
                                cupo = 10,
                                materiaId = materiaSeleccionada?.id ?: 0,
                                espacioId = aulaSeleccionada?.id ?: 0,
                                mentorId = 1
                            )

                            onDismiss()
                        }
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    )

    // ------------------ TIME PICKER INICIO ------------------
    if (showTimePickerInicio) {

        val timeState = rememberTimePickerState()

        AlertDialog(
            onDismissRequest = { showTimePickerInicio = false },
            confirmButton = {
                TextButton(onClick = {
                    horaInicio = String.format("%02d:%02d", timeState.hour, timeState.minute)
                    showTimePickerInicio = false
                    errorHoraInicio = ""
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePickerInicio = false }) {
                    Text("Cancelar")
                }
            },
            text = {
                TimePicker(state = timeState)
            }
        )
    }

    // ------------------ TIME PICKER FIN ------------------
    if (showTimePickerFin) {

        val timeState = rememberTimePickerState()

        AlertDialog(
            onDismissRequest = { showTimePickerFin = false },
            confirmButton = {
                TextButton(onClick = {
                    horaFin = String.format("%02d:%02d", timeState.hour, timeState.minute)
                    showTimePickerFin = false
                    errorHoraFin = ""
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePickerFin = false }) {
                    Text("Cancelar")
                }
            },
            text = {
                TimePicker(state = timeState)
            }
        )
    }
}