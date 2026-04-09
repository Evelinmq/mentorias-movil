package mx.edu.utez.mentoriasmovil.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.mentoriasmovil.model.Materia
import mx.edu.utez.mentoriasmovil.network.RetrofitClient

class MateriaViewModel : ViewModel() {
    var listaMaterias by mutableStateOf<List<Materia>>(emptyList())
    var isLoading by mutableStateOf(false)

    fun obtenerMaterias() {
        viewModelScope.launch {
            isLoading = true
            try {
                val respuesta = RetrofitClient.apiService.listarMaterias()
                listaMaterias = respuesta
            } catch (e: Exception) {
                android.util.Log.e("API_CRASH", "El error es: ${e.localizedMessage}")
            } finally {
                isLoading = false
            }
        }
    }

    fun agregarMateria(nombre: String, cuatrimestre: Int) {
        viewModelScope.launch {
            try {

                val nueva = Materia(nombre = nombre, cuatrimestre = cuatrimestre, carreraId = 1)
                val response = RetrofitClient.apiService.crearMateria(nueva)
                if (response.isSuccessful) obtenerMaterias()
            } catch (e: Exception) { Log.e("API", "Error al crear: ${e.message}") }
        }
    }


    fun editarMateria(id: Long, nombre: String, cuatrimestre: Int) {
        viewModelScope.launch {
            try {
                val editada = Materia(id = id, nombre = nombre, cuatrimestre = cuatrimestre, carreraId = 1)
                val response = RetrofitClient.apiService.actualizarMateria(id, editada)
                if (response.isSuccessful) obtenerMaterias()
            } catch (e: Exception) { Log.e("API", "Error al editar: ${e.message}") }
        }
    }


    fun eliminarMateria(id: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.eliminarMateria(id)
                if (response.isSuccessful) obtenerMaterias()
            } catch (e: Exception) { Log.e("API", "Error al eliminar: ${e.message}") }
        }
    }
}
