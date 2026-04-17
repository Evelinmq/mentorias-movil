package mx.edu.utez.mentoriasmovil.ui.screen.recuperacion

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.mentoriasmovil.R

@Composable
fun CambiarContrasenaScreen(
    onPasswordChanged: () -> Unit,
    onBack: () -> Unit
) {
    var nuevaContrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    
    var errorNueva by remember { mutableStateOf("") }
    var errorConfirmar by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
    ) {
        // Fondo con círculos (Estilo similar a Login/Recuperación)
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = Color(0xFF1A3B7A), radius = 350f, center = Offset(size.width, 0f))
            drawCircle(color = Color(0xFFADC6FF), radius = 150f, center = Offset(100f, size.height - 100f))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Nueva Contraseña",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A3B7A)
            )

            Text(
                text = "Crea una contraseña segura para tu cuenta",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Input Nueva Contraseña
            OutlinedTextField(
                value = nuevaContrasena,
                onValueChange = { 
                    nuevaContrasena = it
                    errorNueva = "" 
                },
                label = { Text("Nueva contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                isError = errorNueva.isNotEmpty(),
                supportingText = { if (errorNueva.isNotEmpty()) Text(errorNueva, color = Color.Red) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Confirmar Contraseña
            OutlinedTextField(
                value = confirmarContrasena,
                onValueChange = { 
                    confirmarContrasena = it
                    errorConfirmar = ""
                },
                label = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                isError = errorConfirmar.isNotEmpty(),
                supportingText = { if (errorConfirmar.isNotEmpty()) Text(errorConfirmar, color = Color.Red) }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    // Validaciones
                    var isValid = true
                    if (nuevaContrasena.length < 6) {
                        errorNueva = "La contraseña debe tener al menos 6 caracteres"
                        isValid = false
                    }
                    if (nuevaContrasena != confirmarContrasena) {
                        errorConfirmar = "Las contraseñas no coinciden"
                        isValid = false
                    }

                    if (isValid) {
                        onPasswordChanged()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A3B7A))
            ) {
                Text("Actualizar Contraseña", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            TextButton(onClick = onBack) {
                Text("Cancelar", color = Color.Gray)
            }
        }
    }
}
