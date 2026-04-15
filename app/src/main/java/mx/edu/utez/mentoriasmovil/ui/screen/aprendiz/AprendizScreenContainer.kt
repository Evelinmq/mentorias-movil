package mx.edu.utez.mentoriasmovil.ui.screen.aprendiz

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.nav.AprendizBottomBar
import mx.edu.utez.mentoriasmovil.viewmodel.AprendizViewModel

@Composable
fun AprendizScreenContainer(
    currentScreen: String,
    navController: NavController,
    aprendizId: Long = 0L
) {
    val viewModel: AprendizViewModel = viewModel(
        factory = AprendizViewModel.factory(aprendizId)
    )

    Scaffold(
        topBar = {
            MainHeader(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        },
        bottomBar = {
            AprendizBottomBar(
                currentRoute = currentScreen,
                onNavigate = { route ->
                    navController.navigate("$route/$aprendizId") {
                        popUpTo("aprendiz_asesoria/$aprendizId")
                        launchSingleTop = true
                    }
                    viewModel.cargarTodo()
                }
            )
        }
    ) { paddingValues ->
        when (currentScreen) {
            "aprendiz_asesoria"  -> AsesoriaScreen(paddingValues, viewModel)
            "aprendiz_historial" -> HistorialScreen(paddingValues, viewModel)
            "aprendiz_agregar"   -> AgregarScreen(
                paddingValues = paddingValues,
                viewModel = viewModel,
                onAgendadoExitoso = {
                    navController.navigate("aprendiz_asesoria/$aprendizId") {
                        popUpTo("aprendiz_asesoria/$aprendizId")
                        launchSingleTop = true
                    }
                    // También actualizamos al agendar con éxito
                    viewModel.cargarTodo()
                }
            )
        }
    }
}
