package mx.edu.utez.mentoriasmovil.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.mentoriasmovil.model.*
import mx.edu.utez.mentoriasmovil.network.RetrofitClient

class AprendizViewModel(private val usuarioId: Long) : ViewModel() {

    var listaAsesoriasAgendadas by mutableStateOf<List<AsesoriaData>>(emptyList())
    var listaAsesoriasDisponibles by mutableStateOf<List<AsesoriaData>>(emptyList())
    var listaHistorial by mutableStateOf<List<AsesoriaData>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var mensajeExito by mutableStateOf<String?>(null)


    init {
        cargarTodo()
    }

    fun cargarTodo() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val agendadasResponse = RetrofitClient.apiService
                    .obtenerMisMentorias(usuarioId)
                listaAsesoriasAgendadas = agendadasResponse
                    .map { it.toAsesoriaData(true)}

                val historialResponse = RetrofitClient.apiService
                    .obtenerMiHistorial(usuarioId)
                listaHistorial = historialResponse
                    .map { it.toAsesoriaData(false) }

                val disponiblesResponse = RetrofitClient.apiService.obtenerMentorias()
                listaAsesoriasDisponibles = disponiblesResponse
                    .filter { mentoria ->
                        !listaAsesoriasAgendadas.any { it.id == mentoria.id }
                    }
                    .map { it.toAsesoriaData(false) }

                Log.d("AprendizVM",
                    "Agendadas: ${listaAsesoriasAgendadas.size} | " +
                            "Historial: ${listaHistorial.size} | " +
                            "Disponibles: ${listaAsesoriasDisponibles.size}")

            } catch (e: Exception) {
                Log.e("AprendizVM", "Error: ${e.message}")
                errorMessage = "Error al conectar con el servidor"
            } finally {
                isLoading = false
            }
        }
    }

    fun agendarAsesoria(asesoria: AsesoriaData) {
        viewModelScope.launch {
            isLoading = true
            try {
                val request = InscripcionRequest(
                    usuario = UsuarioId(usuarioId),
                    mentoria = MentoriaId(asesoria.id)
                )
                val response = RetrofitClient.apiService.agendarMentoria(
                    request,
                    tema = "Apoyo en ${asesoria.materia}"
                )
                if (response.isSuccessful) {
                    Log.d("AprendizVM", "Inscripción exitosa")
                    mensajeExito = "¡Asesoría agendada correctamente!"
                } else {
                    errorMessage = "Error al agendar: ${response.message()}"
                    Log.e("AprendizVM", response.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                errorMessage = "Error de red al agendar"
                Log.e("AprendizVM", "Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    companion object {
        fun factory(usuarioId: Long): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AprendizViewModel(usuarioId) as T
                }
            }
    }
}

fun Mentoria.toAsesoriaData(agendada: Boolean): AsesoriaData {
    return AsesoriaData(
        id = this.id,
        email = this.email ?: "sin_correo@utez.edu.mx",
        nombre = this.mentor ?: "Mentor sin nombre",
        fecha = this.fecha,
        materia = this.materia ?: "Sin materia",
        ubicacion = this.espacio ?: "Sin ubicación",
        hora = "${this.horaInicio} - ${this.horaFin}",
        agendada = agendada
    )
}

fun MentoriaDetalle.toAsesoriaData(agendada: Boolean): AsesoriaData {
    return AsesoriaData(
        id = this.id,
        email = this.mentor?.correo ?: "sin_correo@utez.edu.mx",
        nombre = "${this.mentor?.nombre ?: ""} ${this.mentor?.apellidoP ?: ""}".trim()
            .ifBlank { "Mentor sin nombre" },
        fecha = this.fecha,
        materia = this.materia?.nombre ?: "Sin materia",
        ubicacion = this.espacio?.nombre ?: "Sin ubicación",
        hora = "${this.horaInicio} - ${this.horaFin}",
        agendada = agendada,
        estado = this.estado?.nombre ?: "Pendiente"
    )
}