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
import kotlin.onFailure
import kotlin.onSuccess
import kotlin.text.isBlank

class LoginViewModel() : ViewModel() {

    var errorCorreo by mutableStateOf("")
    var errorContrasena by mutableStateOf("")
    var correo by mutableStateOf("")
    var password by mutableStateOf("")

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

            if (password.isBlank()) {
                errorContrasena = "La contraseña es obligatoria"
                isValid = false
            }

            if (!isValid) return@launch

            isLoading = true

            try {

               val datosLogin = LoginRequest(correo = correo, password = password)

                val response = RetrofitClient.apiService.login(datosLogin)

                if (response.isSuccessful) {
                    val respuesta = response.body()
                    userRole = respuesta?.rol ?: ""
                    isLoginSuccess = true
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorMessage = try {
                        val json = org.json.JSONObject(errorBody ?: "")
                        json.getString("message")
                    } catch (e: Exception) {
                        "Error en login"
                    }
                }
            } finally {
                isLoading = false
            }
        }
    }
}
