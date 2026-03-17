package mx.edu.utez.mentoriasmovil.ui.components.admin.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.mentoriasmovil.ui.theme.text_card_grey
import mx.edu.utez.mentoriasmovil.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import mx.edu.utez.mentoriasmovil.ui.theme.card_grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumnoDialog(
    isEdit: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Map<String, String>) -> Unit
) {
    // Estados para cada campo
    var correo by remember { mutableStateOf("") }
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }

    // Estados para los dropdowns
    var expCarrera by remember { mutableStateOf(false) }
    var expRol by remember { mutableStateOf(false) }

    Dialog (onDismissRequest = onDismiss) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = card_grey)
        ) {
            Column (
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isEdit) "Editar usuario" else "Agregar usuario",
                    fontWeight = FontWeight.Bold,
                    color = text_card_grey,
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // correo
                CustomInput(value = correo, onValueChange = { correo = it }, label = "Correo")
                Spacer(modifier = Modifier.height(12.dp))

                // Nombre(s)
                CustomInput(value = nombres, onValueChange = { nombres = it }, label = "Nombre(s) del alumno")
                Spacer(modifier = Modifier.height(12.dp))

                // apellidos
                CustomInput(value = apellidos, onValueChange = { apellidos = it }, label = "Apellidos del alumno")
                Spacer(modifier = Modifier.height(12.dp))

                // contraseña
                CustomInput(value = contrasena, onValueChange = { contrasena = it }, label = "Contraseña")
                Spacer(modifier = Modifier.height(12.dp))

                // 5. Carrera (Desplegable)
                ExposedDropdownMenuBox (expanded = expCarrera, onExpandedChange = { expCarrera = !expCarrera }) {
                    OutlinedTextField(
                        value = carrera, onValueChange = {}, readOnly = true,
                        placeholder = { Text("Carrera") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expCarrera) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                    )
                    ExposedDropdownMenu(expanded = expCarrera, onDismissRequest = { expCarrera = false }) {
                        listOf("Software", "Diseño", "Redes").forEach { option ->
                            DropdownMenuItem(text = { Text(option) }, onClick = { carrera = option; expCarrera = false })
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                // Rol
                ExposedDropdownMenuBox(expanded = expRol, onExpandedChange = { expRol = !expRol }) {
                    OutlinedTextField(
                        value = rol, onValueChange = {}, readOnly = true,
                        placeholder = { Text("Rol") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expRol) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                    )
                    ExposedDropdownMenu(expanded = expRol, onDismissRequest = { expRol = false }) {
                        listOf("Mentor", "Alumno", "Administrador").forEach { option ->
                            DropdownMenuItem(text = { Text(option) }, onClick = { rol = option; expRol = false })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Botones
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button (onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = text_card_grey)) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            onConfirm(mapOf("correo" to correo, "nombres" to nombres, "apellidos" to apellidos))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}