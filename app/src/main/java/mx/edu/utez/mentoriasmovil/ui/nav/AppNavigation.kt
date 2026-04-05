import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import mx.edu.utez.mentoriasmovil.ui.screen.admin.AdminScreenContainer
import mx.edu.utez.mentoriasmovil.ui.screen.aprendiz.AprendizScreenContainer
import mx.edu.utez.mentoriasmovil.ui.screen.aprendiz.AsesoriaScreen
import mx.edu.utez.mentoriasmovil.ui.screen.login.LoginScreen
import mx.edu.utez.mentoriasmovil.ui.screen.mentor.MentorScreen
import mx.edu.utez.mentoriasmovil.ui.screen.recuperacion.RecuperacionScreen
import mx.edu.utez.mentoriasmovil.ui.screen.registro.RegistroScreen
import mx.edu.utez.mentoriasmovil.viewmodel.LoginViewModel
import mx.edu.utez.mentoriasmovil.viewmodel.RegistroViewModel


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            val loginViewModel: LoginViewModel = viewModel()

            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { rol ->
                    val destination = when (rol) {
                        "mentor" -> "mentor_home"
                        "aprendiz" -> "aprendiz_asesoria"
                        "admin" -> "admin_historial"
                        else -> "login"
                    }

                    navController.navigate(destination) {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToRegister = { navController.navigate("registro") },
                onNavigateToRecovery = { navController.navigate("recovery") }
            )
        }

        composable("registro") {
            val registroViewModel: RegistroViewModel = viewModel()

            RegistroScreen(
                viewModel = registroViewModel,
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable("recovery") {
            RecuperacionScreen(
                onBack = { navController.popBackStack() },
                onResend = { println("Reenviar código") }
            )
        }
        composable("mentor_home") {
            MentorScreen(navController)
        }


        //navegacion dentro de admin
        composable("admin_historial") {
            AdminScreenContainer("historial", navController)
        }

        composable("admin_alumnos") {
            AdminScreenContainer("alumnos", navController)
        }

        composable("admin_materias") {
            AdminScreenContainer("materias", navController)
        }
        composable("admin_carreras") {
            AdminScreenContainer("carreras", navController)
        }

        //navegacion de aprendiz
        composable("aprendiz_asesoria") {
            AprendizScreenContainer("aprendiz_asesoria", navController)
        }

        composable("aprendiz_historial") {
            AprendizScreenContainer("aprendiz_historial", navController)
        }

        composable("aprendiz_agregar") {
            AprendizScreenContainer("aprendiz_agregar", navController)
        }
    }
}