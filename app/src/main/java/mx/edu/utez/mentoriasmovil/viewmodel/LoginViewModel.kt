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

    var correo by mutableStateOf("")
    var contrasena by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isLoginSuccess by mutableStateOf(false)


    fun onLoginClick() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            if (correo == "user@utez.edu.mx" && contrasena == "12345") {
                isLoginSuccess = true
            } else {
                errorMessage = "Credenciales incorrectas, Prueba: user@utez.edu.mx / 12345)"
            }
            isLoading = false
        }
    }
}
