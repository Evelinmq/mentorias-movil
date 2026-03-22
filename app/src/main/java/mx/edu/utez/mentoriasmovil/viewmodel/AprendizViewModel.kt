package mx.edu.utez.mentoriasmovil.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.edu.utez.mentoriasmovil.ui.screen.aprendiz.AsesoriaData

class AprendizViewModel : ViewModel() {

    var listaAsesoriasAgendadas by mutableStateOf(listOf(
        AsesoriaData(
            email = "20243ds148@utez.edu.mx",
            nombre = "Andres Manuel Lopez Obrador",
            fecha = "30/01/2026",
            materia = "Contaduría I",
            ubicacion = "A2 - Docencia II",
            hora = "13:00 - 14:00",
            agendada = true
        ),
        AsesoriaData(
            email = "20243ds145@utez.edu.mx",
            nombre = "Claudia Sheinbaum Pardo",
            fecha = "31/01/2026",
            materia = "Física II",
            ubicacion = "Laboratorio de Cómputo 1",
            hora = "10:00 - 11:30",
            agendada = false
        )
    ))

    var listaAsesoriasDisponibles by mutableStateOf(listOf(
        AsesoriaData(
            email = "20243ds148@utez.edu.mx",
            nombre = "Gustavo Diaz Peña",
            fecha = "30/01/2026",
            materia = "Matematica aplicada",
            ubicacion = "A2 - Docencia II",
            hora = "08:00 - 10:00",
            agendada = false
        ),
        AsesoriaData(
            email = "20243ds148@utez.edu.mx",
            nombre = "Gustavo Diaz Peña",
            fecha = "30/01/2026",
            materia = "Matematica aplicada",
            ubicacion = "A12 - Docencia V",
            hora = "13:00 - 14:00",
            agendada = false
        )
    ))

    var listaHistorial by mutableStateOf(listOf(
        AsesoriaData(
            email = "20243ds153@utez.edu.mx",
            nombre = "John Marston Gonzales",
            fecha = "13/12/2025",
            materia = "Inglés IV",
            ubicacion = "A6 - Docencia I",
            hora = "13:00 - 14:00",
            agendada = true
        )
    ))

    fun agendarAsesoria(asesoria: AsesoriaData) {
        listaAsesoriasAgendadas = listaAsesoriasAgendadas + asesoria.copy(agendada = false)
        listaAsesoriasDisponibles = listaAsesoriasDisponibles.filter { it != asesoria }
    }
}
