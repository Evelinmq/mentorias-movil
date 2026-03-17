package mx.edu.utez.mentoriasmovil.ui.components.admin.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.mentoriasmovil.R
import mx.edu.utez.mentoriasmovil.ui.theme.text_card_grey

@Composable
fun CarreraCard(
    nombreCarrera: String,
    onEditClick: () -> Unit
) {

    BaseCard(
        onEditClick = onEditClick
    ) {
        Text(
            text = "Carrera:",
            color = text_card_grey,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = nombreCarrera,
            color = text_card_grey,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}