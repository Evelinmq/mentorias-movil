package mx.edu.utez.mentoriasmovil.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.mentoriasmovil.model.Edificio
import mx.edu.utez.mentoriasmovil.network.RetrofitClient
import androidx.compose.runtime.getValue

class EdificioViewModel : ViewModel() {

    var edificios by mutableStateOf<List<Edificio>>(emptyList())

    fun obtenerEdificios() {
        viewModelScope.launch {
            try {
                edificios = RetrofitClient.apiService.listarEdificios()
                println("Edificios: ${edificios.size}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}