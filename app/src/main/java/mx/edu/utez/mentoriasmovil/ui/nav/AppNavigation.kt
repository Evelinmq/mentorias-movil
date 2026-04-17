package mx.edu.utez.mentoriasmovil.ui.nav

import mx.edu.utez.mentoriasmovil.viewmodel.LoginViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.mentoriasmovil.ui.screen.admin.AdminScreenContainer
import mx.edu.utez.mentoriasmovil.ui.screen.aprendiz.AprendizScreenContainer
import mx.edu.utez.mentoriasmovil.ui.screen.login.LoginScreen
import mx.edu.utez.mentoriasmovil.ui.screen.mentor.MentorScreen
import mx.edu.utez.mentoriasmovil.ui.screen.recuperacion.CambiarContrasenaScreen
import mx.edu.utez.mentoriasmovil.ui.screen.recuperacion.RecuperacionScreen
import mx.edu.utez.mentoriasmovil.ui.screen.registro.RegistroScreen
import mx.edu.utez.mentoriasmovil.viewmodel.RegistroViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // ─── LOGIN ───────────────────────────────────────────────
        composable("login") {
            val loginViewModel: LoginViewModel = viewModel()

            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { rol, id ->
                    val rolFinal = rol.trim().lowercase()

                    val destination = when (rolFinal) {
                        "mentor"                 -> "mentor_home/$id"
                        "aprendiz", "alumno"     -> "aprendiz_asesoria/$id"
                        "admin", "administrador" -> "admin_historial"
                        else -> {
                            println("ROL NO RECONOCIDO: $rolFinal")
                            "login"
                        }
                    }

                    navController.navigate(destination) {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToRegister = { navController.navigate("registro") },
                onNavigateToRecovery = { email -> 
                    navController.navigate("recovery/$email") 
                }
            )
        }

        // ─── REGISTRO ────────────────────────────────────────────
        composable("registro") {
            val registroViewModel: RegistroViewModel = viewModel()

            RegistroScreen(
                viewModel = registroViewModel,
                onBackToLogin = { navController.popBackStack() }
            )
        }

        // ─── RECUPERACIÓN ────────────────────────────────────────
        composable("recovery/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            
            RecuperacionScreen(
                email = email,
                onBack = { navController.popBackStack() },
                onResend = { 
                    // Aquí puedes simular la validación del código y luego navegar
                    navController.navigate("change_password")
                }
            )
        }

        // ─── CAMBIAR CONTRASEÑA ──────────────────────────────────
        composable("change_password") {
            CambiarContrasenaScreen(
                onPasswordChanged = {
                    // Al terminar, volvemos al login limpiando el historial
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ─── MENTOR ──────────────────────────────────────────────
        composable("mentor_home/{mentorId}") { backStackEntry ->
            val mentorId = backStackEntry.arguments
                ?.getString("mentorId")?.toLong() ?: 0L

            MentorScreen(
                navController = navController,
                mentorId = mentorId
            )
        }

        // ─── APRENDIZ ────────────────────────────────────────────
        composable("aprendiz_asesoria/{aprendizId}") { backStackEntry ->
            val aprendizId = backStackEntry.arguments
                ?.getString("aprendizId")?.toLong() ?: 0L

            AprendizScreenContainer(
                currentScreen = "aprendiz_asesoria",
                navController = navController,
                aprendizId = aprendizId
            )
        }

        composable("aprendiz_historial/{aprendizId}") { backStackEntry ->
            val aprendizId = backStackEntry.arguments
                ?.getString("aprendizId")?.toLong() ?: 0L

            AprendizScreenContainer(
                currentScreen = "aprendiz_historial",
                navController = navController,
                aprendizId = aprendizId
            )
        }

        composable("aprendiz_agregar/{aprendizId}") { backStackEntry ->
            val aprendizId = backStackEntry.arguments
                ?.getString("aprendizId")?.toLong() ?: 0L

            AprendizScreenContainer(
                currentScreen = "aprendiz_agregar",
                navController = navController,
                aprendizId = aprendizId
            )
        }

        // ─── ADMIN ───────────────────────────────────────────────
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
