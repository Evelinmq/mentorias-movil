package mx.edu.utez.mentoriasmovil.ui.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.R
import mx.edu.utez.mentoriasmovil.ui.components.AddButton
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.components.admin.card.CarreraCard
import mx.edu.utez.mentoriasmovil.ui.nav.AdminBottomBar
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import mx.edu.utez.mentoriasmovil.ui.components.admin.modal.CarreraDialog


@Composable
fun CarrerasScreen(paddingValues: PaddingValues) {

    // ESTADOS PARA CONTROLAR LOS MODALES
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    // Nombre de la carrera a editar
    var carreraSeleccionada by remember { mutableStateOf("Desarrollo de Software") }

    // --- 2. LÓGICA DE VISIBILIDAD DE LOS DIÁLOGOS ---
    if (showAddDialog) {
        CarreraDialog(
            isEdit = false,
            onDismiss = { showAddDialog = false },
            onConfirm = { nueva ->
                println("Guardando nueva carrera: $nueva")
                showAddDialog = false
            }
        )
    }

    if (showEditDialog) {
        CarreraDialog(
            isEdit = true,
            initialText = carreraSeleccionada,
            onDismiss = { showEditDialog = false },
            onConfirm = { editada ->
                carreraSeleccionada = editada
                showEditDialog = false
            }
        )
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Icono de Notificación
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bellicon),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = Color.Black
            )
        }

        // Botón Agregar
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            AddButton(onClick = { showAddDialog = true })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // DESPÚES SE CONECTARÁ A LA BD-.-.-.-.-.-...-.-...-.-.--
        // AQUÍ
        val listaCarreras = listOf("Desarrollo de Software", "Diseño Digital", "Ciberseguridad")

        listaCarreras.forEach { nombre ->
            CarreraCard (
                nombreCarrera = nombre,
                onEditClick = {
                    // Guarda el nombre para el modal de editar
                    carreraSeleccionada = nombre
                    showEditDialog = true
                }
            )

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FullScreenPreview() {
    MentoriasMovilTheme {
        Scaffold (
            topBar = { MainHeader (onLogout = {}) },
            bottomBar = { AdminBottomBar (currentRoute = "Carrera", onNavigate = {}) }
        ) { padding ->
            CarrerasScreen(padding)
        }
    }
}