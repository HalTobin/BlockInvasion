package feature.game.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit
) = TextButton(modifier = Modifier.padding(bottom = 16.dp),
    onClick = onClick) {
    Text(text = text.uppercase(),
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineLarge) }