package mx.edu.utez.mentoriasmovil.ui.components.admin.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.ui.theme.header_blue
import mx.edu.utez.mentoriasmovil.R

@Composable
fun SearchBar(
    placeholder: String = "Buscar por mentor",
    onSearchClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .background(Color(0xFF1A3567), RoundedCornerShape(50.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = placeholder,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            IconButton (onClick = onSearchClick) {
                Icon(
                    painter = painterResource(id = R.drawable.searchicon),
                    contentDescription = "Buscar",
                    tint = Color.White
                )
            }
        }
    }
}