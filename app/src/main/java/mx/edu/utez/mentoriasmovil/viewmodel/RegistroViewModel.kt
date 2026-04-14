package mx.edu.utez.mentoriasmovil.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.mentoriasmovil.model.Carrera
import mx.edu.utez.mentoriasmovil.network.RetrofitClient

class RegistroViewModel : ViewModel() {

    // --- Campos del Formulario ---
    var nombre by mutableStateOf("")
    var apellidoPaterno by mutableStateOf("")
    var apellidoMaterno by mutableStateOf("")
    var correo by mutableStateOf("")
    var contrasena by mutableStateOf("")
    var rol by mutableStateOf("")

    // Guardamos el objeto Carrera completo o su ID para el registro final
    var carreraSeleccionada by mutableStateOf<Carrera?>(null)

    // --- Estados de la API ---
    var listaCarreras by mutableStateOf<List<Carrera>>(emptyList())
    var isLoading by mutableStateOf(false)
    var mensajeErrorBackend by mutableStateOf("")

    // --- Errores de Validación Visual ---
    var errorNombre by mutableStateOf("")
    var errorApellidoPaterno by mutableStateOf("")
    var errorApellidoMaterno by mutableStateOf("")
    var errorCorreo by mutableStateOf("")
    var errorContrasena by mutableStateOf("")
    var errorRol by mutableStateOf("")
    var errorCarrera by mutableStateOf("")

    init {
        cargarCarreras()
    }

    private fun cargarCarreras() {
        viewModelScope.launch {
            try {
                // Reemplaza 'RetrofitClient.api' por cómo accedas a tu interfaz API
                val respuesta = RetrofitClient.apiService.listarCarreras()
                listaCarreras = respuesta
            } catch (e: Exception) {
                mensajeErrorBackend = "Error al conectar con el servidor"
            }
        }
    }

    fun onRegistrarClick(onSuccess: () -> Unit) {
        // Reiniciar estados de error
        errorNombre = ""; errorApellidoPaterno = ""; errorApellidoMaterno = ""
        errorCorreo = ""; errorContrasena = ""; errorRol = ""; errorCarrera = ""

        var isValid = true

        if (nombre.isBlank()) { errorNombre = "Obligatorio"; isValid = false }
        if (apellidoPaterno.isBlank()) { errorApellidoPaterno = "Obligatorio"; isValid = false }
        if (apellidoMaterno.isBlank()) { errorApellidoMaterno = "Obligatorio"; isValid = false }
        if (rol.isBlank()) { errorRol = "Selecciona un rol"; isValid = false }
        if (carreraSeleccionada == null) { errorCarrera = "Selecciona una carrera"; isValid = false }

        if (correo.isBlank()) {
            errorCorreo = "Obligatorio"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            errorCorreo = "Email inválido"
            isValid = false
        }

        if (contrasena.length < 6) {
            errorContrasena = "Mínimo 6 caracteres"
            isValid = false
        }

        if (!isValid) return

        isLoading = true

        viewModelScope.launch {
            try {
                // Aquí iría tu @POST de registro
                // val registro = RetrofitClient.api.registrarUsuario(...)
                onSuccess()
            } catch (e: Exception) {
                mensajeErrorBackend = "Error al registrar: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}