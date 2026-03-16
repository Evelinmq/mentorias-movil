package mx.edu.utez.mentoriasmovil.ui.components.admin.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mx.edu.utez.mentoriasmovil.ui.theme.card_grey
import mx.edu.utez.mentoriasmovil.R
import mx.edu.utez.mentoriasmovil.ui.theme.button_grey

@Composable
fun BaseCard(
    onEditClick: () -> Unit,
    onDeleteClick: (() -> Unit)? = null,
    onBlockClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = card_grey)
    ) {

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            // Aquí va el texto (Carrera, Materia y así)
            content()

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                ActionButton(
                    icon = R.drawable.editicon,
                    color = Color.White,
                    tint = Color.Black,
                    onClick = onEditClick
                )

                onBlockClick?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    ActionButton(icon = R.drawable.desactivateicon, color = button_grey, tint = Color.White, onClick = it)
                }

                // Si existe onDelete, mostrar botón negro
                onDeleteClick?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    ActionButton(icon = R.drawable.trashicon, color = Color.Black, tint = Color.White, onClick = it)
                }
            }
        }
    }
}

// Mini componente para no repetir el estilo de los botones cuadrados
@Composable
fun ActionButton(icon: Int, color: Color, tint: Color, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .background(color, RoundedCornerShape(8.dp))
            .border(
                1.dp,
                if(color == Color.White) Color.LightGray else Color.Transparent,
                RoundedCornerShape(8.dp))
    ) {
        Icon(painterResource(id = icon), contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
    }
}