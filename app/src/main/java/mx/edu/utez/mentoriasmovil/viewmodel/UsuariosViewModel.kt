package mx.edu.utez.mentoriasmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.mentoriasmovil.model.UserResponse

import mx.edu.utez.mentoriasmovil.network.ApiService

class UsuariosViewModel(private val apiService: ApiService) : ViewModel() {

    private val _usuarios = MutableStateFlow<List<UserResponse>>(emptyList())
    val usuarios: StateFlow<List<UserResponse>> = _usuarios

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje


    fun cargarUsuarios(viendoPendientes: Boolean) {
        val nombreEstado = if (viendoPendientes) "Pendiente" else "Activo"

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.listarPorEstado(nombreEstado)
                // ESTA LÍNEA ES PARA DEPURAR:
                println("DEBUG: Cargando $nombreEstado. Cantidad recibida: ${response.size}")

                _usuarios.value = response
            } catch (e: Exception) {
                println("DEBUG: Error cargando usuarios: ${e.message}")
                _mensaje.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun aceptarUsuario(usuarioId: Long) {
        viewModelScope.launch {
            try {
                val payload = mapOf(
                    "id" to usuarioId,
                    "nuevoEstadoId" to 1L // ID 1 según tu imagen es 'Activo'
                )
                val response = apiService.cambiarEstado(payload)

                if (response.isSuccessful) {
                    // Recargamos la lista de pendientes para confirmar que ya no está
                    cargarUsuarios(viendoPendientes = true)
                    _mensaje.value = "Usuario activado correctamente"
                }
            } catch (e: Exception) {
                _mensaje.value = "Error al conectar"
            }
        }
    }


    fun eliminarUsuario(usuarioId: Long, viendoPendientes: Boolean) {
        viewModelScope.launch {
            try {
                apiService.eliminarUsuario(usuarioId)
                cargarUsuarios(viendoPendientes)
                _mensaje.value = "Usuario eliminado"
            } catch (e: Exception) {
                _mensaje.value = "Error al eliminar"
            }
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }


    fun desactivarUsuario(usuarioId: Long, viendoPendientes: Boolean) {
        viewModelScope.launch {
            try {
                val payload = mapOf(
                    "id" to usuarioId,
                    "nuevoEstadoId" to 2L
                )
                apiService.cambiarEstado(payload)

                cargarUsuarios(viendoPendientes)
                _mensaje.value = "Usuario desactivado correctamente"
            } catch (e: Exception) {
                _mensaje.value = "Error al desactivar usuario"
            }
        }
    }


    fun cargarInactivos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.listarPorEstado("Inactivo")
                _usuarios.value = response
            } catch (e: Exception) {
                _mensaje.value = "Error al cargar inactivos"
            } finally {
                _isLoading.value = false
            }
        }
    }

    companion object {
        val Factory: androidx.lifecycle.ViewModelProvider.Factory =
            object : androidx.lifecycle.ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {

                    val api = mx.edu.utez.mentoriasmovil.network.RetrofitClient.apiService
                    return UsuariosViewModel(api) as T
                }
            }
    }


}