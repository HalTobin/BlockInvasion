package feature.game.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.wait_for_your_turn
import data.model.Pixel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.audio.AppSound
import ui.audio.SoundController

@Composable
fun PlayerControls(
    player: Int,
    playerPixels: List<Pixel>,
    currentPlayer: Int,
    pixels: Array<Pixel>,
    onClick: (Pixel, Int) -> Unit
) = Crossfade(modifier = Modifier.height(72.dp),
    targetState = (player == currentPlayer)) { isCurrentPlayer ->
    val soundPlayer = koinInject<SoundController>()
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .then (
                if (player == 2) Modifier.graphicsLayer { rotationZ = 180f }
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround) {
            Text(modifier = Modifier.padding(start = 4.dp),
                text = player.toString(),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold)
            pixels.forEach { pixel ->
                val isFree = !playerPixels.contains(pixel)
                Box(modifier = Modifier.composed {
                    size(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(pixel.color))
                        .clickable(isCurrentPlayer && isFree) {
                            if (isFree) {
                                soundPlayer.playSound(AppSound.ButtonFeedback)
                                onClick(pixel, player)
                            }
                            else soundPlayer.playSound(AppSound.DeniedFeedback)
                        }
                }, contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.composed {
                        size(32.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isFree) Color.Black.copy(alpha = 0.2f) else Color.LightGray)
                            .clickable(isCurrentPlayer && isFree) { onClick(pixel, player) }
                    })
                }
            }
        }
        if (!isCurrentPlayer) Row(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))) {
            Text(modifier = Modifier.fillMaxWidth().offset(y = 6.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(Res.string.wait_for_your_turn)
            )
        }
    }
}