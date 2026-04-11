package mx.edu.utez.mentoriasmovil.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.mentoriasmovil.model.*
import mx.edu.utez.mentoriasmovil.network.RetrofitClient

class AprendizViewModel : ViewModel() {

    var listaAsesoriasAgendadas by mutableStateOf<List<AsesoriaData>>(emptyList())
    var listaAsesoriasDisponibles by mutableStateOf<List<AsesoriaData>>(emptyList())
    
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    // Por ahora simulamos que el usuario logueado es el ID 1
    // Más adelante este vendrá del Login real
    private val usuarioLogueadoId = 1L 

    init {
        cargarTodo()
    }

    fun cargarTodo() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                // 1. Cargamos las agendadas por el alumno (REUTILIZANDO API WEB)
                val agendadasResponse = RetrofitClient.apiService.obtenerMisMentorias(usuarioLogueadoId)
                listaAsesoriasAgendadas = agendadasResponse.map { it.toAsesoriaData(true) }

                // 2. Cargamos las disponibles
                val disponiblesResponse = RetrofitClient.apiService.obtenerMentorias()
                
                // Filtramos las que ya están agendadas para que no salgan doble
                listaAsesoriasDisponibles = disponiblesResponse
                    .filter { mentoria -> !listaAsesoriasAgendadas.any { it.id == mentoria.id } }
                    .map { it.toAsesoriaData(false) }

                Log.d("AprendizViewModel", "Carga completa: ${listaAsesoriasAgendadas.size} agendadas, ${listaAsesoriasDisponibles.size} disponibles")
            } catch (e: Exception) {
                Log.e("AprendizViewModel", "Error al cargar datos", e)
                errorMessage = "Error al sincronizar con el servidor"
            } finally {
                isLoading = false
            }
        }
    }

    fun agendarAsesoria(asesoria: AsesoriaData) {
        viewModelScope.launch {
            try {
                isLoading = true
                
                // Creamos el objeto de inscripción como lo pide el MentoriaUsuarioController
                val request = InscripcionRequest(
                    usuario = UsuarioId(usuarioLogueadoId),
                    mentoria = MentoriaId(asesoria.id)
                )

                val response = RetrofitClient.apiService.agendarMentoria(request, tema = "Apoyo en ${asesoria.materia}")
                
                if (response.isSuccessful) {
                    Log.d("AprendizViewModel", "¡Inscripción exitosa en el servidor!")
                    // Recargamos todo para estar seguros de que la UI refleja la DB
                    cargarTodo()
                } else {
                    errorMessage = "Error al agendar: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error de red al agendar"
            } finally {
                isLoading = false
            }
        }
    }
}

// Función de extensión para convertir el modelo de la API al de la UI
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
