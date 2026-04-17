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
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import mx.edu.utez.mentoriasmovil.model.IdWrapper
import mx.edu.utez.mentoriasmovil.model.MentoriaRequest


class MentoriaViewModel : ViewModel() {

    var listarMentorias by mutableStateOf<List<Mentoria>>(emptyList())
    var mentoriasFiltradas by mutableStateOf<List<Mentoria>>(emptyList())
    var isLoading by mutableStateOf(false)
    private var fechaFiltroActual: String? = null

    fun obtenerDatos() {
        viewModelScope.launch {
            isLoading = true
            try {
                val respuesta = RetrofitClient.apiService.obtenerMentorias() 
                listarMentorias = respuesta
                aplicarFiltros()
            } catch (e: Exception) {
                Log.e("API_TEST", "Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    private fun aplicarFiltros() {
        mentoriasFiltradas = if (fechaFiltroActual == null) {
            listarMentorias
        } else {
            listarMentorias.filter { it.fecha == fechaFiltroActual }
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
                val response = RetrofitClient.apiService.crearMentoria(request)
                if (response.isSuccessful) {
                    obtenerMentoriasPorMentor(mentorId)
                }
            } catch (e: Exception) {
                Log.e("API_TEST", "Exception: ${e.message}")
            }
        }
    }

    fun filtrarPorFecha(fecha: String) {
        viewModelScope.launch {
            isLoading = true
            fechaFiltroActual = fecha
            delay(300) 
            aplicarFiltros()
            isLoading = false
        }
    }

    fun obtenerMentoriasPorMentor(mentorId: Long) {
        viewModelScope.launch {
            isLoading = true
            try {
                val mentoriasDeferred = async { RetrofitClient.apiService.obtenerMentoriasPorMentor(mentorId) }
                val conteoDeferred = async { RetrofitClient.apiService.obtenerConteoInscritos() }

                val respuesta = mentoriasDeferred.await()
                val conteoMap = conteoDeferred.await()
                val conteoStringKeys = conteoMap.mapKeys { it.key.toString() }

                listarMentorias = respuesta.map { mentoria ->
                    val inscritos = conteoStringKeys[mentoria.id.toString()]?.toInt() 
                        ?: mentoria.alumnos?.size 
                        ?: 0
                    
                    val temaFinal = if (mentoria.tema.isNullOrBlank()) "Sin tema" else mentoria.tema

                    mentoria.copy(
                        conteoReal = inscritos,
                        tema = temaFinal
                    )
                }
                
                aplicarFiltros()
                Log.d("MentoriaVM", "Datos sincronizados con éxito")
            } catch (e: Exception) {
                Log.e("MentoriaVM", "Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun cambiarEstado(mentoriaId: Long, nuevoEstado: String, mentorId: Long) {
        viewModelScope.launch {
            try {
                val response = if (nuevoEstado == "ACEPTADA") {
                    RetrofitClient.apiService.aceptarMentoria(mentoriaId)
                } else {
                    RetrofitClient.apiService.cancelarMentoria(mentoriaId)
                }

                if (response.isSuccessful) {
                    obtenerMentoriasPorMentor(mentorId)
                    Log.d("API_TEST", "Estado actualizado a $nuevoEstado")
                } else {
                    Log.e("API_TEST", "Error al cambiar estado: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API_TEST", "Exception: ${e.message}")
            }
        }
    }
}
