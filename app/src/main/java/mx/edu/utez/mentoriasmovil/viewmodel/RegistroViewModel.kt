package mx.edu.utez.mentoriasmovil.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegistroViewModel : ViewModel() {

    var nombre by mutableStateOf("")
    var apellidoPaterno by mutableStateOf("")
    var apellidoMaterno by mutableStateOf("")
    var correo by mutableStateOf("")
    var contrasena by mutableStateOf("")
    var rol by mutableStateOf("") // Se inicializa vacío para forzar selección
    var carreraSeleccionada by mutableStateOf("")

    // Lista para el Dropdown de carreras
    var listaCarreras by mutableStateOf<List<String>>(emptyList())

    // Variables de error
    var errorNombre by mutableStateOf("")
    var errorApellidoPaterno by mutableStateOf("")
    var errorApellidoMaterno by mutableStateOf("")
    var errorCorreo by mutableStateOf("")
    var errorContrasena by mutableStateOf("")
    var errorRol by mutableStateOf("") // 👈 Agregado
    var errorCarrera by mutableStateOf("")

    var isLoading by mutableStateOf(false)

    init {
        cargarCarreras()
    }

    private fun cargarCarreras() {
        // Simulación de carga de datos
        listaCarreras = listOf(
            "Ing. Desarrollo de Software",
            "Ing. Redes",
            "Lic. Gastronomía",
            "Ing. Mecatrónica"
        )
    }

    fun onRegistrarClick(onSuccess: () -> Unit) {
        // Reset errores
        errorNombre = ""
        errorApellidoPaterno = ""
        errorApellidoMaterno = ""
        errorCorreo = ""
        errorContrasena = ""
        errorRol = "" // 👈 Reset agregado
        errorCarrera = ""

        var isValid = true

        // Validaciones básicas
        if (nombre.isBlank()) { errorNombre = "Obligatorio"; isValid = false }
        if (apellidoPaterno.isBlank()) { errorApellidoPaterno = "Obligatorio"; isValid = false }
        if (apellidoMaterno.isBlank()) { errorApellidoMaterno = "Obligatorio"; isValid = false }

        // Validación de Correo
        if (correo.isBlank()) {
            errorCorreo = "Obligatorio"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            errorCorreo = "Correo inválido"
            isValid = false
        }

        // Validación de Contraseña
        if (contrasena.isBlank()) {
            errorContrasena = "Obligatorio"
            isValid = false
        } else if (contrasena.length < 6) {
            errorContrasena = "Mínimo 6 caracteres"
            isValid = false
        }

        if (rol.isBlank() || rol == "Selecciona tu rol") {
            errorRol = "Debes elegir un rol"
            isValid = false
        }

        // Validación de Carrera
        if (carreraSeleccionada.isBlank()) {
            errorCarrera = "Debes seleccionar una carrera"
            isValid = false
        }

        if (!isValid) return

        isLoading = true

        // Aquí iría la lógica de tu API (Retrofit)
        // simulamos éxito:
        onSuccess()

        isLoading = false
    }
}