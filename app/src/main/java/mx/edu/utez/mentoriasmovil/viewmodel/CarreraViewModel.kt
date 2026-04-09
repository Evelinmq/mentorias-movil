package mx.edu.utez.mentoriasmovil.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.mentoriasmovil.model.Carrera
import mx.edu.utez.mentoriasmovil.network.RetrofitClient

class CarreraViewModel  : ViewModel(){

    var listaCarreras by mutableStateOf<List<Carrera>>(emptyList())
    var isLoading by mutableStateOf(false)

    fun obtenerCarreras() {
        viewModelScope.launch {
            isLoading = true
            try {
                val respuesta = RetrofitClient.apiService.listarCarreras()
                listaCarreras = respuesta
            } catch (e: Exception) {
                android.util.Log.e("API_CRASH", "El error es: ${e.localizedMessage}")
            } finally {
                isLoading = false
            }
        }
    }

    fun agregarCarrera(nombre: String) {
        viewModelScope.launch {
            try {

                val nueva = Carrera(nombre = nombre)
                val response = RetrofitClient.apiService.crearCarrera(nueva)
                if (response.isSuccessful) obtenerCarreras()
            } catch (e: Exception) { Log.e("API", "Error al crear: ${e.message}") }
        }
    }


    fun editarCarrera(id: Long, nombre: String) {
        viewModelScope.launch {
            try {
                val editada = Carrera(id = id, nombre = nombre,)
                val response = RetrofitClient.apiService.actualizarCarrera(id, editada)
                if (response.isSuccessful) obtenerCarreras()
            } catch (e: Exception) { Log.e("API", "Error al editar: ${e.message}") }
        }
    }




}

