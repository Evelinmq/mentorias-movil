package mx.edu.utez.mentoriasmovil.ui.screen.registro

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.R
import androidx.compose.ui.unit.sp
import mx.edu.utez.mentoriasmovil.viewmodel.RegistroViewModel
import androidx.compose.runtime.setValue

@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel,
    onBackToLogin: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = Color(0xFF1A3B7A), radius = 400f, center = Offset(100f, 0f))
            drawCircle(color = Color(0xFFADC6FF), radius = 150f, center = Offset(450f, 200f))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text("Crear cuenta", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2C1F3B))

            Spacer(modifier = Modifier.height(20.dp))

            RegistroField(value = viewModel.nombre, onValueChange = { viewModel.nombre = it }, label = "Nombre(s)", error = viewModel.errorNombre)

            RegistroField(value = viewModel.apellidoPaterno, onValueChange = { viewModel.apellidoPaterno = it }, label = "Apellido Paterno", error = viewModel.errorApellidoPaterno)

            RegistroField(value = viewModel.apellidoMaterno, onValueChange = { viewModel.apellidoMaterno = it }, label = "Apellido Materno", error = viewModel.errorApellidoMaterno)

            RegistroField(value = viewModel.correo, onValueChange = { viewModel.correo = it }, label = "Email", error = viewModel.errorCorreo)

            RegistroField(value = viewModel.contrasena, onValueChange = { viewModel.contrasena = it }, label = "Password", error = viewModel.errorContrasena, isPassword = true)

            GenericDropdown(label = "Rol", options = listOf("Mentor", "Aprendiz"), selectedOption = viewModel.rol, onOptionSelected = { viewModel.rol = it }, error = viewModel.errorRol)

            GenericDropdown(
                label = "Carrera",
                options = viewModel.listaCarreras.map { it.nombre }, // Pasamos solo los nombres para mostrar
                selectedOption = viewModel.carreraSeleccionada?.nombre ?: "", // Mostramos el nombre seleccionado
                onOptionSelected = { nombreSeleccionado ->
                    // Buscamos el objeto Carrera que coincide con el nombre seleccionado
                    viewModel.carreraSeleccionada = viewModel.listaCarreras.find { it.nombre == nombreSeleccionado }
                },
                error = viewModel.errorCarrera
            )
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { viewModel.onRegistrarClick { onBackToLogin() } },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A3B7A))
            ) {
                if (viewModel.isLoading) CircularProgressIndicator(color = Color.White)
                else Text("Crear", fontSize = 18.sp, color = Color.White)
            }

            TextButton(onClick = onBackToLogin) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun RegistroField(value: String, onValueChange: (String) -> Unit, label: String, error: String = "", isPassword: Boolean = false) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(label, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50),
            isError = error.isNotEmpty(),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = Color(0xFF1A3B7A))
        )
        if (error.isNotEmpty()) {
            Text(text = error, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericDropdown(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit, error: String = "") {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = if (selectedOption.isEmpty()) "Selecciona $label" else selectedOption,
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                shape = RoundedCornerShape(50),
                isError = error.isNotEmpty(),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, focusedBorderColor = Color(0xFF1A3B7A))
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
        if (error.isNotEmpty()) {
            Text(text = error, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp))
        }
    }
}