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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.ui.components.AddButton
import mx.edu.utez.mentoriasmovil.ui.components.admin.card.MateriaCard
import mx.edu.utez.mentoriasmovil.R
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.components.ConfirmDialog
import mx.edu.utez.mentoriasmovil.ui.components.admin.modal.MateriaDialog
import mx.edu.utez.mentoriasmovil.ui.nav.AdminBottomBar
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun MateriasScreen(paddingValues: PaddingValues) {
    // Estados para los modales
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    var showDeleteConfirm by remember { mutableStateOf(false) }
    var itemAEliminar by remember { mutableStateOf("") }

    // Estado para saber qué materia se está editando
    var materiaSeleccionada by remember { mutableStateOf("") }

    // --- DIÁLOGOS ---
    if (showAddDialog) {
        MateriaDialog(
            isEdit = false,
            onDismiss = { showAddDialog = false },
            onConfirm = { c, m, cu ->
                showAddDialog = false
            }
        )
    }

    if (showEditDialog) {
        MateriaDialog(
            isEdit = true,
            initialMateria = materiaSeleccionada,
            onDismiss = { showEditDialog = false },
            onConfirm = { c, m, cu ->
                showEditDialog = false
            }
        )
    }

    if (showDeleteConfirm) {
        ConfirmDialog(
            title = "Eliminar Materia",
            message = "¿Eliminar la materia '$itemAEliminar'? Esta acción no se puede deshacer.",
            onDismiss = { showDeleteConfirm = false },
            onConfirm = {
                println("Eliminando $itemAEliminar...")
                showDeleteConfirm = false
            }
        )
    }

    val listaMaterias = listOf(
        Triple("Desarrollo de Software", "Programación I", "5"),
        Triple("Desarrollo de Software", "Base de Datos", "4")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bellicon),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                AddButton(onClick = { showAddDialog = true })
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(listaMaterias) { (carrera, materia, cuatri) ->
            MateriaCard(
                carrera = carrera,
                materia = materia,
                cuatrimestre = cuatri,
                onEditClick = {
                    materiaSeleccionada = materia
                    showEditDialog = true
                },
                onDeleteClick = {
                    itemAEliminar = materia
                    showDeleteConfirm = true
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Preview(showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun MateriasPreview() {
    MentoriasMovilTheme {
        Scaffold (
            topBar = { MainHeader (onLogout = {}) },
            bottomBar = { AdminBottomBar (currentRoute = "Materias", onNavigate = {}) }
        ) { padding ->
            MateriasScreen(padding)
        }

    }
}