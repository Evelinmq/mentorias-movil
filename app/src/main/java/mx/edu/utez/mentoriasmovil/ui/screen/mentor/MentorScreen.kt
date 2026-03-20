package mx.edu.utez.mentoriasmovil.ui.screen.mentor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.ui.components.AddButton
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader

import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme

import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue

import androidx.compose.ui.graphics.Color


@Composable
fun MentorScreen() {

    var showAddDialog by remember { mutableStateOf(false) }

    //estado del calendario
    val dateEstado = rememberDatePickerState()

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        Spacer(modifier = Modifier.height(34.dp))

        AddButton(onClick = { showAddDialog = true })

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFE8E7E7),
            tonalElevation = 2.dp
        ) {
            DatePicker(
                state = dateEstado,
                title = null,
                headline = null,
                showModeToggle = false
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        val fechaMs = dateEstado.selectedDateMillis
        if (fechaMs != null) {
            Text(text = "Fecha seleccionada: ${java.util.Date(fechaMs)}")
        }
    }

}





@Preview(showBackground = true)
@Composable
fun PreviewMentor() {
    MentoriasMovilTheme {
        Scaffold (
            topBar = { MainHeader (onLogout = {})}
        ) { paddingValues ->
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                color = MaterialTheme.colorScheme.background) {
                MentorScreen()
            }
        }
    }
}