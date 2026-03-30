package mx.edu.utez.mentoriasmovil.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.onFailure
import kotlin.onSuccess
import kotlin.text.isBlank

class LoginViewModel() : ViewModel() {

    var errorCorreo by mutableStateOf("")
    var errorContrasena by mutableStateOf("")
    var correo by mutableStateOf("")
    var contrasena by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isLoginSuccess by mutableStateOf(false)
    var userRole by mutableStateOf("")


    fun onLoginClick() {
        viewModelScope.launch {

            errorCorreo = ""
            errorContrasena = ""
            errorMessage = null

            var isValid = true

            if (correo.isBlank()) {
                errorCorreo = "El correo es obligatorio"
                isValid = false
            }

            if (contrasena.isBlank()) {
                errorContrasena = "La contraseña es obligatoria"
                isValid = false
            }

            if (!isValid) return@launch

            isLoading = true

            try {

                when {
                    correo == "mentor@utez.edu.mx" && contrasena == "12345" -> {
                        userRole = "mentor"
                        isLoginSuccess = true
                    }

                    correo == "aprendiz@utez.edu.mx" && contrasena == "12345" -> {
                        userRole = "aprendiz"
                        isLoginSuccess = true
                    }

                    correo == "admin@utez.edu.mx" && contrasena == "12345" -> {
                        userRole = "admin"
                        isLoginSuccess = true
                    }

                    else -> {
                        errorMessage = "Credenciales incorrectas"
                    }
                }

            } finally {
                isLoading = false
            }
        }
    }
}
