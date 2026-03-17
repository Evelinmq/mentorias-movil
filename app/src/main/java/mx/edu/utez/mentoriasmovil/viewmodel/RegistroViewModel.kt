package mx.edu.utez.mentoriasmovil.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegistroViewModel : ViewModel() {
    var nombre by mutableStateOf("")
    var apellidos by mutableStateOf("")
    var correo by mutableStateOf("")
    var contrasena by mutableStateOf("")
    var rol by mutableStateOf("Selecciona tu rol") // Mentor o Alumno
    var carrera by mutableStateOf("Selecciona tu carrera")

    var isLoading by mutableStateOf(false)

    fun onRegistrarClick(onSuccess: () -> Unit) {
        if (nombre.isNotBlank() && correo.contains("@")) {
            isLoading = true
            onSuccess()//.
            isLoading = false
        }
    }
}