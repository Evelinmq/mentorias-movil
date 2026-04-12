package mx.edu.utez.mentoriasmovil.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.mentoriasmovil.model.LoginRequest
import mx.edu.utez.mentoriasmovil.network.RetrofitClient

class LoginViewModel : ViewModel() {

    var errorCorreo by mutableStateOf("")
    var errorContrasena by mutableStateOf("")
    var correo by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isLoginSuccess by mutableStateOf(false)
    var userRole by mutableStateOf("")
    var userId by mutableStateOf<Long>(0)

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
            if (password.isBlank()) {
                errorContrasena = "La contraseña es obligatoria"
                isValid = false
            }
            if (!isValid) return@launch

            isLoading = true

            try {
                val response = RetrofitClient.apiService.login(
                    LoginRequest(correo = correo, password = password)
                )

                if (response.isSuccessful) {
                    val respuesta = response.body()
                    userRole = respuesta?.rol ?: ""
                    userId = respuesta?.id ?: 0L
                    isLoginSuccess = true
                    Log.d("LOGIN", "Rol: $userRole | ID: $userId")
                } else {
                    errorMessage = "Credenciales incorrectas"
                }

            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
                Log.e("LOGIN", "Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}