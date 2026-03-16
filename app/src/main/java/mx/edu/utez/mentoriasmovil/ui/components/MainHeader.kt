package mx.edu.utez.mentoriasmovil.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.R
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme
import mx.edu.utez.mentoriasmovil.ui.theme.header_blue

@Composable
fun MainHeader(onLogout: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(header_blue)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.logo_white),
                contentDescription = "Logo Aplicación",
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text("SISTEMA DE MENTORÍAS", color = Color.White, fontWeight = FontWeight.Bold)
        }
        IconButton(onClick = onLogout) {
            Icon(
                painter = painterResource(id = R.drawable.logouticon),
                contentDescription = "Cerrar Sesión",
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    MentoriasMovilTheme {
        MainHeader(onLogout = {})
    }
}