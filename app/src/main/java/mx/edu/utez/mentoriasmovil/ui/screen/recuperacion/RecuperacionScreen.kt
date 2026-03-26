package mx.edu.utez.mentoriasmovil.ui.screen.recuperacion

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.mentoriasmovil.R

@Composable
fun RecuperacionScreen(
    email: String = "2********6@g****l.com",
    onBack: () -> Unit,
    onResend: () -> Unit
) {

    var code by remember { mutableStateOf(List(5) { "" }) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFF1A3B7A),
                radius = 300f,
                center = Offset(-50f, -50f)
            )
            drawCircle(
                color = Color(0xFF3D6DCC),
                radius = 80f,
                center = Offset(120f, 120f)
            )
            drawCircle(
                color = Color(0xFF1A3B7A),
                radius = 250f,
                center = Offset(size.width + 100f, size.height / 2)
            )
            drawCircle(
                color = Color(0xFFADC6FF),
                radius = 60f,
                center = Offset(size.width - 100f, size.height - 200f)
            )
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Recuperación de Contraseña",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A3B7A)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Introduce el código que enviamos a",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Text(
                text = email,
                fontSize = 14.sp,
                color = Color(0xFF1A3B7A),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                code.forEachIndexed { index, value ->
                    CodeBox(
                        value = value,
                        onValueChange = {
                            val newCode = code.toMutableList()
                            newCode[index] = it.take(1)
                            code = newCode
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Text("Volver", color = Color.Black)
                }

                Button(
                    onClick = onResend,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A3B7A)
                    )
                ) {
                    Text("Reenviar", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CodeBox(
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(55.dp)
            .background(
                Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 2.dp,
                color = Color(0xFF1A3B7A),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF1A3B7A)
            )
        )
    }
}