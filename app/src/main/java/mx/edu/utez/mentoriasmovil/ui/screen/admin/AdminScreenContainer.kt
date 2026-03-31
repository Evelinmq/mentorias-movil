package mx.edu.utez.mentoriasmovil.ui.screen.admin

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.nav.AdminBottomBar
import mx.edu.utez.mentoriasmovil.ui.screen.admin.AlumnosScreen
import mx.edu.utez.mentoriasmovil.ui.screen.admin.MateriasScreen
import mx.edu.utez.mentoriasmovil.ui.screen.admin.CarrerasScreen

@Composable
fun AdminScreenContainer(
    currentScreen: String,
    navController: NavController
) {
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
            "alumnos" -> AlumnosScreen(paddingValues)
            "materias" -> MateriasScreen(paddingValues)
            "carreras" -> CarrerasScreen(paddingValues)
        }
    }
}