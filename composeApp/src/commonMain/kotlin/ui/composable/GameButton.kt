package ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun GameButton(
    modifier: Modifier = Modifier,
    text: String,
    goToGame: Boolean,
    direction: Direction,
    icon: ImageVector,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = !goToGame,
        exit = slideOutHorizontally(
            targetOffsetX = { when (direction) {
                Direction.Left -> -it
                Direction.Right -> it
            } }) + fadeOut()
    ) {
        Button(modifier = Modifier.fillMaxWidth().height(64.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = onClick) {
            Icon(modifier = Modifier.size(32.dp),
                imageVector = icon,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = null)
            Text(modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                text = text.uppercase())
        }
    }
}

enum class Direction { Left, Right }