package mx.edu.utez.mentoriasmovil.ui.screen.admin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.setValue
import mx.edu.utez.mentoriasmovil.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import mx.edu.utez.mentoriasmovil.ui.components.AddButton
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.components.ConfirmDialog
import mx.edu.utez.mentoriasmovil.ui.components.admin.modal.AlumnoDialog
import mx.edu.utez.mentoriasmovil.ui.nav.AdminBottomBar
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.ui.platform.LocalContext
import mx.edu.utez.mentoriasmovil.viewmodel.UsuariosViewModel


@Composable
fun AlumnosScreen(
    paddingValues: PaddingValues,
    viendoPendientes: Boolean = false,
    onToggleVista: () -> Unit = {},
    viewModel: UsuariosViewModel
) {

    val listaUsuarios by viewModel.usuarios.collectAsState()
    val cargando by viewModel.isLoading.collectAsState()


    var showConfirmDialog by remember { mutableStateOf(false) }
    var usuarioSeleccionadoId by remember { mutableLongStateOf(0L) }
    var nombreMostrar by remember { mutableStateOf("") }
    var tipoAccion by remember { mutableStateOf("") }
    val context = LocalContext.current
    val mensaje by viewModel.mensaje.collectAsState()


    LaunchedEffect(viendoPendientes) {
        viewModel.cargarUsuarios(viendoPendientes)
    }

    if (showConfirmDialog) {
        ConfirmDialog(
            title = if (tipoAccion == "eliminar") "Eliminar Usuario" else "Desactivar Usuario",
            message = if (tipoAccion == "eliminar")
                "¿Eliminar usuario $nombreMostrar? Esta acción es permanente."
            else "¿Deseas desactivar al usuario $nombreMostrar?",
            onDismiss = { showConfirmDialog = false },
            onConfirm = { if (tipoAccion == "eliminar"){
                viewModel.eliminarUsuario(usuarioSeleccionadoId, viendoPendientes)
            }else{
                viewModel.desactivarUsuario(usuarioSeleccionadoId, viendoPendientes)
            }
                showConfirmDialog = false
            }
        )
    }
    LaunchedEffect(mensaje) {
        mensaje?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.limpiarMensaje()
        }
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
                .padding(top = 8.dp, end = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onToggleVista,
                modifier = Modifier.background(
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

        if (cargando) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Blue)
            }
        } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if (!viendoPendientes) {
                item {
                    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                        AddButton(onClick = { /* ... */ })
                    }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

            items(listaUsuarios) { usuario ->
                AlumnoCard(
                    nombre = usuario.nombre,
                    apellidos = "${usuario.apellidoP} ${usuario.apellidoM}",
                    rol = usuario.roles?.firstOrNull()?.nombre ?: "Sin Rol",
                    carrera = usuario.carrera?.nombre ?: "Sin Carrera",
                    correo = usuario.correo,
                    esPendiente = viendoPendientes,
                    onAcceptClick = {
                        viewModel.aceptarUsuario(usuario.id)
                    },
                    onRejectClick = {
                        usuarioSeleccionadoId = usuario.id
                        nombreMostrar = usuario.nombre
                        tipoAccion = "eliminar" // Esto activará el diálogo y luego viewModel.eliminarUsuario
                        showConfirmDialog = true
                    },
                    onEditClick = { /* Lógica editar */ },
                    onDeleteClick = {
                        usuarioSeleccionadoId = usuario.id
                        nombreMostrar = usuario.nombre
                        tipoAccion = "eliminar"
                        showConfirmDialog = true
                    },
                    onBlockClick = {
                        usuarioSeleccionadoId = usuario.id
                        nombreMostrar = usuario.nombre
                        tipoAccion = "desactivar"
                        showConfirmDialog = true
                    }
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
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