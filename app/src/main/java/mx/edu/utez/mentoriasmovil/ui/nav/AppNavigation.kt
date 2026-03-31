import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import mx.edu.utez.mentoriasmovil.ui.screen.admin.AdminScreenContainer
import mx.edu.utez.mentoriasmovil.ui.screen.login.LoginScreen
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
                onLoginSuccess = {
                    when (loginViewModel.userRole) {
                        "mentor" -> navController.navigate("mentor_home"){
                            popUpTo("login") { inclusive = true }
                        }
                        "aprendiz" -> navController.navigate("aprendiz_home"){
                            popUpTo("login") { inclusive = true }
                        }
                        "admin" -> navController.navigate("admin_historial"){
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("registro")
                },
                onNavigateToRecovery = {
                    navController.navigate("recovery")
                }
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
            Text("Pantalla Mentor")
        }

        composable("aprendiz_home") {
            Text("Pantalla Aprendiz")
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
    }
}