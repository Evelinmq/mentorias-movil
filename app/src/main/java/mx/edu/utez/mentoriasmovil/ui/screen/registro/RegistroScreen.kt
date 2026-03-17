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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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

@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel = RegistroViewModel(),
    onBackToLogin: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = Color(0xFF1A3B7A), radius = 400f, center = Offset(100f, 0f))
            drawCircle(color = Color(0xFFADC6FF), radius = 150f, center = Offset(450f, 200f))
        }

        Column(
            modifier = Modifier//.
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Text(
                "Crear cuenta",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C1F3B)
            )

            Spacer(modifier = Modifier.height(20.dp))

            RegistroField(value = viewModel.nombre, onValueChange = { viewModel.nombre = it }, label = "Nombre(s)")
            RegistroField(value = viewModel.apellidos, onValueChange = { viewModel.apellidos = it }, label = "Apellidos")
            RegistroField(value = viewModel.correo, onValueChange = { viewModel.correo = it }, label = "Email")
            RegistroField(
                value = viewModel.contrasena,
                onValueChange = { viewModel.contrasena = it },
                label = "Password",
                isPassword = true
            )

            OutlinedTextField(
                value = viewModel.rol,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth().clickable {},
                shape = RoundedCornerShape(50),
                label = { Text("Rol") },
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { viewModel.onRegistrarClick { onBackToLogin() } },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A3B7A))
            ) {
                Text("Crear", fontSize = 18.sp, color = Color.White)
            }

            TextButton(onClick = onBackToLogin) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = Color.Gray)
            }
        }
    }
}

@Composable
fun RegistroField(value: String, onValueChange: (String) -> Unit, label: String, isPassword: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = RoundedCornerShape(50),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color(0xFF1A3B7A)
        )
    )
}