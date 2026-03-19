package mx.edu.utez.mentoriasmovil.ui.components.admin.subcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import mx.edu.utez.mentoriasmovil.ui.theme.text_card_grey

@Composable
fun InfoRow(label: String, value: String) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = text_card_grey, fontWeight = FontWeight.Bold)
        Text(text = value, color = text_card_grey)
    }
}

