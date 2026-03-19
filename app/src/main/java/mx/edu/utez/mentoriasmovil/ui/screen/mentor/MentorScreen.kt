package mx.edu.utez.mentoriasmovil.ui.screen.mentor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.ui.components.AddButton
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.nav.AdminBottomBar
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme
import mx.edu.utez.mentoriasmovil.viewmodel.MentorViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun MentorScreen(viewModel: Unit) {

    var showAddDialog by remember { mutableStateOf(false)}

    Spacer(modifier = Modifier.height(34.dp))

    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        AddButton (onClick = { showAddDialog = true })
    }




}

@Preview(showBackground = true)
@Composable
fun PreviewMentor() {
    MentoriasMovilTheme {
        Scaffold (
            topBar = { MainHeader (onLogout = {})}
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                MentorScreen(viewModel = Unit)
            }
        }
    }
}