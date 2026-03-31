package mx.edu.utez.mentoriasmovil.ui.screen.aprendiz

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.nav.AprendizBottomBar

@Composable
fun AprendizScreenContainer(
    currentScreen: String,
    navController: NavController
) {
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
                    navController.navigate(route) {
                        popUpTo("aprendiz_asesoria")
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { paddingValues ->

        when (currentScreen) {
            "aprendiz_asesoria" -> AsesoriaScreen(paddingValues)
            "aprendiz_historial" -> HistorialScreen(paddingValues)
            "aprendiz_agregar" -> AgregarScreen(paddingValues)
        }
    }
}