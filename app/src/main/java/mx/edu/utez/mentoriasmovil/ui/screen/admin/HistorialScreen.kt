package mx.edu.utez.mentoriasmovil.ui.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.components.admin.bar.SearchBar
import mx.edu.utez.mentoriasmovil.ui.components.admin.card.MentoriaCard
import mx.edu.utez.mentoriasmovil.ui.nav.AdminBottomBar
import mx.edu.utez.mentoriasmovil.ui.screen.aprendiz.HistorialScreen
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme

@Composable
fun HistorialContent(paddingValues: PaddingValues) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White)
    ) {

        SearchBar()

        LazyColumn {
            item {
                MentoriaCard(
                    fecha = "30/01/2026",
                    hora = "13:00 - 14:00",
                    mentor = "Andres Manuel Lopez Obrador",
                    carrera = "Desarrollo de Software",
                    materia = "Programación I"
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HistorialPreview() {
    MentoriasMovilTheme {

        Scaffold(
            topBar = { MainHeader(onLogout = {}) },
            bottomBar = {
                AdminBottomBar(
                    currentRoute = "historial",
                    onNavigate = {}
                )
            }
        ) { paddingValues ->

            HistorialContent(paddingValues)

        }
    }
}