package mx.edu.utez.mentoriasmovil.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.mentoriasmovil.model.Mentoria
import mx.edu.utez.mentoriasmovil.network.RetrofitClient
import android.util.Log
import androidx.lifecycle.ViewModel


class MentoriaViewModel : ViewModel() {

    var listarMentorias by mutableStateOf<List<Mentoria>>(emptyList())
    var mentoriasFiltradas by mutableStateOf<List<Mentoria>>(emptyList())

    fun obtenerDatos() {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitClient.apiService.listarMentorias()
                listarMentorias = respuesta
                mentoriasFiltradas = respuesta // 👈 inicializamos filtradas

                android.util.Log.d("API_TEST", "Datos recibidos: ${respuesta.size}")
            } catch (e: Exception) {
                android.util.Log.e("API_TEST", "Error: ${e.message}")
            }
        }
    }

    fun filtrar(mentor: String, materia: String) {
        mentoriasFiltradas = listarMentorias.filter { mentoria ->

            val coincideMentor = mentor.isBlank() ||
                    mentoria.id.toString().contains(mentor, ignoreCase = true)

            val coincideMateria = materia.isBlank() ||
                    mentoria.cuatrimestre.toString().contains(materia, ignoreCase = true)

            coincideMentor && coincideMateria
        }
    }
}