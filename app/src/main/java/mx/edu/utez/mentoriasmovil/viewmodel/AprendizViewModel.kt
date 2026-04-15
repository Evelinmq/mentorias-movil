package mx.edu.utez.mentoriasmovil.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
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
    var filtroMateria by mutableStateOf<String?>(null)

    val listaFiltrada: List<AsesoriaData>
        get() = if (filtroMateria == null) {
            listaAsesoriasDisponibles
        } else {
            listaAsesoriasDisponibles.filter {
                it.materia.equals(filtroMateria, ignoreCase = true)
            }
        }

    val materiasDisponibles: List<String>
        get() = listaAsesoriasDisponibles
            .map { it.materia }
            .distinct()
            .sorted()

    fun filtrarPorMateria(materia: String?) {
        filtroMateria = materia
    }

    init {
        cargarTodo()
    }

    fun cargarTodo() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                // Ejecutamos las llamadas en paralelo para mayor velocidad
                val agendadasDeferred = async { RetrofitClient.apiService.obtenerMisMentorias(usuarioId) }
                val historialDeferred = async { RetrofitClient.apiService.obtenerMiHistorial(usuarioId) }
                val disponiblesDeferred = async { RetrofitClient.apiService.obtenerMentorias() }
                val conteoDeferred = async { RetrofitClient.apiService.obtenerConteoInscritos() }

                // Esperamos los resultados
                val agendadasResponse = agendadasDeferred.await()
                val historialResponse = historialDeferred.await()
                val disponiblesResponse = disponiblesDeferred.await()
                val conteo = conteoDeferred.await()

                // Procesamos las respuestas
                listaAsesoriasAgendadas = agendadasResponse.map { it.toAsesoriaData(true) }
                listaHistorial = historialResponse.map { it.toAsesoriaData(false) }

                listaAsesoriasDisponibles = disponiblesResponse
                    .filter { mentoria ->
                        val noAgendada = !listaAsesoriasAgendadas.any { it.id == mentoria.id }
                        val inscritos = conteo[mentoria.id]?.toInt() ?: 0
                        val cupoTotal = mentoria.cupo ?: 0
                        val tieneCupo = inscritos < cupoTotal
                        noAgendada && tieneCupo
                    }
                    .map { mentoria ->
                        mentoria.toAsesoriaData(false).copy(
                            cupo = conteo[mentoria.id]?.toInt() ?: 0,
                            cupoTotal = mentoria.cupo ?: 0
                        )
                    }

                Log.d("AprendizVM", "Carga paralela exitosa: " +
                        "${listaAsesoriasAgendadas.size} agendadas, " +
                        "${listaAsesoriasDisponibles.size} disponibles")

            } catch (e: Exception) {
                Log.e("AprendizVM", "Error en cargarTodo: ${e.message}", e)
                errorMessage = "Error al conectar con el servidor"
            } finally {
                isLoading = false
            }
        }
    }

    fun agendarAsesoria(asesoria: AsesoriaData, tema: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val request = InscripcionRequest(
                    usuario = UsuarioId(usuarioId),
                    mentoria = MentoriaId(asesoria.id)
                )
                val response = RetrofitClient.apiService.agendarMentoria(
                    request,
                    tema = tema
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
        agendada = agendada,
        cupo = 0,
        cupoTotal = this.cupo ?: 0
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
        estado = this.estado?.nombre ?: "Pendiente",
        cupo = this.alumnos?.size ?: 0,
        cupoTotal = this.cupo ?: 0
    )
}
