package mx.edu.utez.mentoriasmovil.ui.screen.admin

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.nav.AdminBottomBar
import mx.edu.utez.mentoriasmovil.ui.screen.admin.AlumnosScreen
import mx.edu.utez.mentoriasmovil.ui.screen.admin.MateriasScreen
import mx.edu.utez.mentoriasmovil.ui.screen.admin.CarrerasScreen
import mx.edu.utez.mentoriasmovil.viewmodel.UsuariosViewModel

@Composable
fun AdminScreenContainer(
    currentScreen: String,
    navController: NavController
) {

    val usuariosViewModel: UsuariosViewModel = viewModel(factory = UsuariosViewModel.Factory)

    var viendoPendientes by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MainHeader(onLogout = {
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            })
        },
        bottomBar = {
            AdminBottomBar(
                currentRoute = currentScreen,
                onNavigate = { route ->
                    viendoPendientes = false
                    navController.navigate(route) {
                        popUpTo("admin_historial")
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { paddingValues ->

        when (currentScreen) {
            "historial" -> HistorialContent(paddingValues)
            "alumnos" -> AlumnosScreen(
                paddingValues = paddingValues,
                viendoPendientes = viendoPendientes,
                onToggleVista = { viendoPendientes = !viendoPendientes },
                viewModel = usuariosViewModel
            )
            "materias" -> MateriasScreen(paddingValues)
            "carreras" -> CarrerasScreen(paddingValues)
        }
    }
}