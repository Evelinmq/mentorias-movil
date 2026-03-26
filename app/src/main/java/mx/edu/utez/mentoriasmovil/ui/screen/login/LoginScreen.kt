package mx.edu.utez.mentoriasmovil.ui.screen.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.mentoriasmovil.ui.components.MainHeader
import mx.edu.utez.mentoriasmovil.ui.nav.AdminBottomBar
import mx.edu.utez.mentoriasmovil.ui.screen.admin.MateriasScreen
import mx.edu.utez.mentoriasmovil.ui.theme.MentoriasMovilTheme
import mx.edu.utez.mentoriasmovil.viewmodel.LoginViewModel
import mx.edu.utez.mentoriasmovil.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.geometry.Offset


@Composable
fun LoginScreen (viewModel: LoginViewModel,
onLoginSuccess: () -> Unit,
onNavigateToRegister: () -> Unit,
                 onNavigateToRecovery: () -> Unit
                 ) {

    LaunchedEffect(viewModel.isLoginSuccess) {
        if (viewModel.isLoginSuccess) {
            onLoginSuccess()
        }
    }
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

        Canvas(modifier = Modifier.fillMaxSize()){
            drawCircle(color = Color(0xFF1A3B7A), radius = 400f, center = Offset(100f, 0f))
            drawCircle(color = Color(0xFFADC6FF), radius = 150f, center = Offset(450f, 200f))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo app",
                Modifier.size(size = 180.dp)
            )

            Spacer(modifier =  Modifier.height(24.dp))
            Text(
                "Inicio de Sesión", style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF2C1F3B),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.size(10.dp))

            OutlinedTextField(
                value = viewModel.correo,
                onValueChange = { viewModel.correo = it },
                placeholder = { Text("Tu usuario", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color(0xFF1A3B7A)
                )
            )
            if (viewModel.errorCorreo.isNotEmpty()) {
            Text(
                text = viewModel.errorCorreo,
                color = Color.Red,
                fontSize = 12.sp
            )
        }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.contrasena,
                onValueChange = { viewModel.contrasena = it },
                placeholder = { Text("Tu contraseña", color = Color.Gray) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color(0xFF1A3B7A)
                )
            )
            if (viewModel.errorContrasena.isNotEmpty()) {
                Text(
                    text = viewModel.errorContrasena,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            TextButton(
                onClick = { onNavigateToRecovery() },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("¿Olvidaste tu contraseña?", color = Color.DarkGray, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (viewModel.errorMessage != null) {
                Text(
                    text = viewModel.errorMessage!!,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            if (viewModel.isLoading) {
                CircularProgressIndicator(color = Color(0xFF1A3B7A))
            } else {
                Button(
                    onClick = { viewModel.onLoginClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A3B7A))
                ) {
                    Text("Iniciar", fontSize = 18.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿No tienes cuenta? ", color = Color.Gray)
                Text(
                    text = "Crear cuenta",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }
        }

        }
    }



@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MentoriasMovilTheme {
        LoginScreen(
            viewModel = LoginViewModel(),
            onLoginSuccess = {},
            onNavigateToRegister = {},
            onNavigateToRecovery = {}
        )
        }

    }


