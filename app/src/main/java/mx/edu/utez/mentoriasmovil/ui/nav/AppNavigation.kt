import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
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
                    println("Login exitoso")
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
    }
}