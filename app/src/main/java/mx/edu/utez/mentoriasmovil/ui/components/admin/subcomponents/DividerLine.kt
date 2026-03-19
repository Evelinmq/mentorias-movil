package mx.edu.utez.mentoriasmovil.ui.components.admin.subcomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DividerLine() {
    Divider(
        modifier = Modifier.padding(vertical = 6.dp),
        color = Color.LightGray,
        thickness = 1.dp
    )
}