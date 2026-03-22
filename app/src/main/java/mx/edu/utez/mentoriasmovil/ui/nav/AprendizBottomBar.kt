package mx.edu.utez.mentoriasmovil.ui.nav

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme
import mx.edu.utez.mentoriasmovil.ui.theme.header_blue // El azul oscuro que ya usas

@Composable
fun AprendizBottomBar(currentRoute: String, onNavigate: (String) -> Unit) {
    val items = listOf("Asesorias", "Historial", "Agregar")

    val icons = listOf(
        Icons.Default.School,
        Icons.Default.History,
        Icons.Default.AddCircle
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.size(height = 80.dp, width = 400.dp)
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = currentRoute == item

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item) },
                label = {
                    Text(
                        text = item,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) header_blue else Color(0xFF4A4A4A)
                    )
                },
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item,
                        modifier = Modifier.size(28.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = header_blue,

                    selectedIconColor = Color.White,

                    unselectedIconColor = header_blue,

                    selectedTextColor = header_blue,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AprendizBottomBarPreview() {
    MentoriasMovilTheme {
        AprendizBottomBar(currentRoute = "Asesorias", onNavigate = {})
    }
}