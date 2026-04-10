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
import mx.edu.utez.mentoriasmovil.model.IdWrapper
import mx.edu.utez.mentoriasmovil.model.MentoriaRequest


class MentoriaViewModel : ViewModel() {

    var listarMentorias by mutableStateOf<List<Mentoria>>(emptyList())
    var mentoriasFiltradas by mutableStateOf<List<Mentoria>>(emptyList())

    fun obtenerDatos() {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitClient.apiService.obtenerMentorias() // 🔥 endpoint nuevo
                listarMentorias = respuesta
                mentoriasFiltradas = respuesta

                Log.d("API_TEST", "Datos recibidos: ${respuesta.size}")
            } catch (e: Exception) {
                Log.e("API_TEST", "Error: ${e.message}")
            }
        }
    }

    fun filtrar(materia: String, espacio: String) {
        mentoriasFiltradas = listarMentorias.filter { mentoria ->

            val coincideMateria = materia.isBlank() ||
                    (mentoria.materia?.contains(materia, ignoreCase = true) ?: false)

            val coincideEspacio = espacio.isBlank() ||
                    (mentoria.espacio?.contains(espacio, ignoreCase = true) ?: false)

            coincideMateria && coincideEspacio
        }
    }

    fun crearMentoria(
        fecha: String,
        horaInicio: String,
        horaFin: String,
        cuatrimestre: Int,
        cupo: Int,
        materiaId: Long,
        espacioId: Long,
        mentorId: Long
    ) {
        viewModelScope.launch {
            try {

                val request = MentoriaRequest(
                    fecha = fecha,
                    horaInicio = "$horaInicio:00",
                    horaFin = "$horaFin:00",
                    cuatrimestre = cuatrimestre,
                    cupo = cupo,
                    espacio = IdWrapper(espacioId),
                    estado = IdWrapper(1),
                    mentor = IdWrapper(mentorId),
                    materia = IdWrapper(materiaId)
                )

                Log.d("API_TEST", request.toString())

                val response = RetrofitClient.apiService.crearMentoria(request)

                if (response.isSuccessful) {
                    Log.d("API_TEST", "CREADO CORRECTAMENTE")
                    obtenerDatos()
                } else {
                    Log.e("API_TEST", response.errorBody()?.string() ?: "Error")
                }

            } catch (e: Exception) {
                Log.e("API_TEST", "Exception: ${e.message}")
            }
        }
    }

    fun filtrarPorFecha(fecha: String) {
        mentoriasFiltradas = listarMentorias.filter { mentoria ->
            mentoria.fecha == fecha
        }
    }

    fun obtenerMentoriasPorMentor(mentorId: Long) {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitClient.apiService.obtenerMentoriasPorMentor(mentorId)
                listarMentorias = respuesta
                mentoriasFiltradas = respuesta

                Log.d("API_TEST", "Mentorías del mentor: ${respuesta.size}")
            } catch (e: Exception) {
                Log.e("API_TEST", "Error: ${e.message}")
            }
        }
    }

}