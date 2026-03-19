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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.ui.components.admin.card.AlumnoCard
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import mx.edu.utez.mentoriasmovil.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import mx.edu.utez.mentoriasmovil.ui.components.AddButton
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.components.admin.ConfirmDialog
import mx.edu.utez.mentoriasmovil.ui.components.admin.modal.AlumnoDialog
import mx.edu.utez.mentoriasmovil.ui.nav.AdminBottomBar
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme


/*
* var viendoPendientes by remember { mutableStateOf(false) }

AlumnosScreen(
    paddingValues = padding,
    viendoPendientes = viendoPendientes,
    onToggleVista = { viendoPendientes = !viendoPendientes }
)
* */
@Composable
fun AlumnosScreen(
    paddingValues: PaddingValues,
    viendoPendientes: Boolean,
    onToggleVista: () -> Unit = {}
) {

    var showConfirmDialog by remember { mutableStateOf(false) }
    var alumnoSeleccionado by remember { mutableStateOf("") }
    var tipoAccion by remember { mutableStateOf("") }

    // --- LÓGICA DE LA ALERTA (Se queda igual) ---
    if (showConfirmDialog) {
        ConfirmDialog(
            title = if (tipoAccion == "eliminar") "Eliminar Usuario" else "Desactivar Usuario",
            message = if (tipoAccion == "eliminar")
                "¿Eliminar usuario $alumnoSeleccionado? Esta acción es permanente."
            else "¿Deseas desactivar al usuario $alumnoSeleccionado?",
            onDismiss = { showConfirmDialog = false },
            onConfirm = { showConfirmDialog = false }
        )
    }

    // COLUMNA PRINCIPAL (Sin scroll aquí para que el header no se vaya)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) // Aplicamos el padding del Scaffold
            .background(Color.White)
    ) {
        // 1. HEADER CON ICONOS (Fijo arriba)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, end = 16.dp, bottom = 8.dp), // Ajustamos padding
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onToggleVista ,
                modifier = Modifier
                    .background(
                        if (viendoPendientes) Color.Black else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.usersicon),
                    contentDescription = "Cambiar vista",
                    tint = if (viendoPendientes) Color.White else Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(id = R.drawable.bellicon),
                contentDescription = "Notificaciones",
                modifier = Modifier.size(28.dp),
                tint = Color.Black
            )
        }

        // 2. CUERPO CON SCROLL (Aquí es donde va el scroll)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Botón Agregar / Lupa
            if (!viendoPendientes) {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    AddButton(onClick = { /* ... */ })
                    // Aquí podrías poner el botón de la lupa si quieres
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LISTA DINÁMICA
            if (viendoPendientes) {
                AlumnoCard(
                    nombre = "Marcos",
                    apellidos = "Ramirez Fernandez",
                    rol = "Mentor",
                    carrera = "Desarrollo de Software",
                    correo = "20243ds144@utez.edu.mx",
                    esPendiente = true,
                    onAcceptClick = { /* ... */ },
                    onRejectClick = { /* ... */ }
                )
            } else {
                AlumnoCard(
                    nombre = "Marcos",
                    apellidos = "Ramirez Pérez",
                    rol = "Aprendiz",
                    carrera = "Desarrollo de Software",
                    correo = "20243ds144@utez.edu.mx",
                    esPendiente = false,
                    onEditClick = { /* ... */ },
                    onDeleteClick = {
                        alumnoSeleccionado = "Marcos Ramirez";
                        tipoAccion = "eliminar";
                        showConfirmDialog = true
                    },
                    onBlockClick = {
                        alumnoSeleccionado = "Marcos Ramirez";
                        tipoAccion = "desactivar";
                        showConfirmDialog = true
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true, name = "Vista Normal")
@Composable
fun AlumnosNormalPreview() {
    MentoriasMovilTheme {
        Scaffold(
            topBar = { MainHeader(onLogout = {}) },
            bottomBar = { AdminBottomBar(currentRoute = "Alumnos", onNavigate = {}) }
        ) { padding ->
            // Forzamos a que no esté en modo pendientes
            AlumnosScreen(
                paddingValues = padding,
                viendoPendientes = false
            )
        }
    }
}

@Preview(showSystemUi = true, name = "Vista Pendientes")
@Composable
fun AlumnosPendientesPreview() {
    MentoriasMovilTheme {
        Scaffold(
            topBar = { MainHeader(onLogout = {}) },
            bottomBar = { AdminBottomBar(currentRoute = "Alumnos", onNavigate = {}) }
        ) { padding ->

            AlumnosScreen(
                paddingValues = padding,
                viendoPendientes = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlumnoDialogPreview() {
    MentoriasMovilTheme {
        AlumnoDialog (
            isEdit = false,
            onDismiss = {},
            onConfirm = {}
        )
    }
}