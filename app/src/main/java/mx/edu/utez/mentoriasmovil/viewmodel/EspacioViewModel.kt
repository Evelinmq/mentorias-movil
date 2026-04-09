package mx.edu.utez.mentoriasmovil.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.mentoriasmovil.model.Espacio
import mx.edu.utez.mentoriasmovil.network.RetrofitClient
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue



class EspacioViewModel : ViewModel() {

    var espacios by mutableStateOf<List<Espacio>>(emptyList())

    fun obtenerEspacios() {
        viewModelScope.launch {
            try {
                espacios = RetrofitClient.apiService.getEspacios()
                android.util.Log.d("API_TEST", "Espacios: ${espacios.size}")
            } catch (e: Exception) {
                android.util.Log.e("API_TEST", "Error: ${e.message}")
            }
        }
    }
}