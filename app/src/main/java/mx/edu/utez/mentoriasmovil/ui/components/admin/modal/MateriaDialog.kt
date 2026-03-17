package mx.edu.utez.mentoriasmovil.ui.components.admin.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import mx.edu.utez.mentoriasmovil.ui.theme.card_grey

@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray
        ),
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MateriaDialog(
    isEdit: Boolean,
    initialMateria: String = "",
    initialCarrera: String = "",
    initialCuatri: String = "",
    listaCarreras: List<String> = listOf("Desarrollo de Software", "Diseño Digital", "Ciberseguridad"),
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    // Estados para los campos
    var materia by remember { mutableStateOf(initialMateria) }
    var carreraSeleccionada by remember { mutableStateOf(initialCarrera) }
    var cuatri by remember { mutableStateOf(initialCuatri) }

    // Estado para controlar si el menú desplegable está abierto
    var expanded by remember { mutableStateOf(false) }

    Dialog (onDismissRequest = onDismiss) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = card_grey)
        ) {
            Column (
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isEdit) "Editar materia" else "Agregar materia",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A3567),
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Campo Materia
                CustomInput(value = materia, onValueChange = { materia = it }, label = "Materia")

                Spacer(modifier = Modifier.height(12.dp))

                // Menú Desplegable para Carrera
                ExposedDropdownMenuBox (
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = carreraSeleccionada,
                        onValueChange = {},
                        readOnly = true, // Evita que el usuario escriba manualmente
                        placeholder = { Text("Carrera") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listaCarreras.forEach { carrera ->
                            DropdownMenuItem(
                                text = { Text(carrera) },
                                onClick = {
                                    carreraSeleccionada = carrera
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Campo Cuatrimestre
                CustomInput(value = cuatri, onValueChange = { cuatri = it }, label = "Cuatrimestre")

                Spacer(modifier = Modifier.height(24.dp))

                // Botones de acción
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button (
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onConfirm(materia, carreraSeleccionada, cuatri) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}