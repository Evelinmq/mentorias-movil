package mx.edu.utez.mentoriasmovil.ui.nav

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import mx.edu.utez.mentoriasmovil.R
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme
import mx.edu.utez.mentoriasmovil.ui.theme.header_blue

@Composable
fun AdminBottomBar(currentRoute: String, onNavigate: (String) -> Unit) {
    //Opciones
    val items = listOf("Historial", "Alumnos", "Materias", "Carrera")
    val icons = listOf(
        R.drawable.historialicon,
        R.drawable.usersicon,
        R.drawable.notebookicon,
        R.drawable.careericon
    )

    NavigationBar (
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = currentRoute == item

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item) },
                label = { Text(text = item, fontSize = 10.sp) },
                icon = {
                    Icon(
                        painter = painterResource(id = icons[index]),
                        contentDescription = item,
                        modifier = Modifier.size(24.dp),
                        // Cambiar color al seleccionarse
                        tint = if (isSelected) Color.White else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = header_blue,
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    MentoriasMovilTheme {
        AdminBottomBar(currentRoute = "Carrera", onNavigate = {})
    }
}