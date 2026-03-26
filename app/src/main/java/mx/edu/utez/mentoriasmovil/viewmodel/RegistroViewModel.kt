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
    var rol by mutableStateOf("Selecciona tu rol")
    var carrera by mutableStateOf("Selecciona tu carrera")

    var errorNombre by mutableStateOf("")
    var errorApellidos by mutableStateOf("")
    var errorCorreo by mutableStateOf("")
    var errorContrasena by mutableStateOf("")

    var isLoading by mutableStateOf(false)

    fun onRegistrarClick(onSuccess: () -> Unit) {

        errorNombre = ""
        errorApellidos = ""
        errorCorreo = ""
        errorContrasena = ""

        var isValid = true

        if (nombre.isBlank()) {
            errorNombre = "El nombre es obligatorio"
            isValid = false
        }

        if (apellidos.isBlank()) {
            errorApellidos = "Los apellidos son obligatorios"
            isValid = false
        }

        if (correo.isBlank()) {
            errorCorreo = "El correo es obligatorio"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            errorCorreo = "Correo inválido"
            isValid = false
        }

        if (contrasena.isBlank()) {
            errorContrasena = "La contraseña es obligatoria"
            isValid = false
        } else if (contrasena.length < 6) {
            errorContrasena = "Mínimo 6 caracteres"
            isValid = false
        }

        if (!isValid) return

        isLoading = true

        // aquí iría el API después
        onSuccess()

        isLoading = false
    }
}