package mx.edu.utez.mentoriasmovil.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.mentoriasmovil.network.ApiService
import mx.edu.utez.mentoriasmovil.network.RetrofitClient

class RecuperacionViewModel(private val apiService: ApiService) : ViewModel() {

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun verificarCodigo(email: String, codigo: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = apiService.verificarCodigo(mapOf("correo" to email, "codigo" to codigo))
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage = "Código incorrecto o expirado"
                }
            } catch (e: Exception) {
                errorMessage = "Error de conexión"
            } finally {
                isLoading = false
            }
        }
    }

    fun actualizarPassword(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = apiService.actualizarPassword(mapOf("correo" to email, "nuevaPassword" to pass))
                if (response.isSuccessful) onSuccess()
            } catch (e: Exception) {
                errorMessage = "Error al conectar con el servidor"
            } finally {
                isLoading = false
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecuperacionViewModel(RetrofitClient.apiService) as T
            }
        }
    }
}
