package mx.edu.utez.mentoriasmovil.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
// Importa tus pantallas
import mx.edu.utez.mentoriasmovil.ui.screen.login.LoginScreen
import mx.edu.utez.mentoriasmovil.ui.screen.registro.RegistroScreen
// Importa tus ViewModels y Repositorios
import mx.edu.utez.mentoriasmovil.viewmodel.LoginViewModel
import mx.edu.utez.mentoriasmovil.viewmodel.RegistroViewModel
import mx.edu.utez.mentoriasmovil.viewmodel.AprendizViewModel
import mx.edu.utez.mentoriasmovil.viewmodel.MentorViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
}