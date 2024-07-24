package feature.game

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.wait_for_your_turn
import data.model.Pixel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.composable.GameMap
import ui.theme.BlockInvasionTheme

@Composable
fun GameScreen(
    goBack: () -> Unit,
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {

    if (state.endGame != null) Dialog(onDismissRequest = { }) {
        Surface {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Winner is: Player ${if (state.endGame.player0Score < state.endGame.player1Score) 1 else 0}\n" +
                        "Player 0: ${state.endGame.player0Score} pixels\n" +
                        "Player 1: ${state.endGame.player1Score} pixels"
                )
                Button(onClick = goBack) {
                    Text(text = "Leave")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        PlayerControls(
            player = 0,
            playerPixels = state.playersPixels,
            currentPlayer = state.player,
            pixels = state.pixelSet,
            onClick = { pixel, player -> onEvent(GameEvent.PlayerAction(player, pixel)) }
        )

        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        GameMap(modifier = Modifier.weight(1f).fillMaxWidth(),
            map = state.map)

        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        PlayerControls(
            player = 1,
            playerPixels = state.playersPixels,
            currentPlayer = state.player,
            pixels = state.pixelSet,
            onClick = { pixel, player -> onEvent(GameEvent.PlayerAction(player, pixel)) }
        )

    }

}

@Composable
fun PlayerControls(
    player: Int,
    playerPixels: List<Pixel>,
    currentPlayer: Int,
    pixels: Array<Pixel>,
    onClick: (Pixel, Int) -> Unit
) = Crossfade(modifier = Modifier.height(72.dp),
    targetState = (player == currentPlayer)) { isCurrentPlayer ->
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .then (
                if (player == 0) Modifier.graphicsLayer { rotationZ = 180f }
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround) {
            pixels.forEach { pixel ->
                val isFree = !playerPixels.contains(pixel)
                Box(modifier = Modifier.composed {
                    size(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (isFree) Color(pixel.color) else Color.LightGray)
                        .clickable(isCurrentPlayer && isFree) { onClick(pixel, player) }
                })
            }
        }
        if (!isCurrentPlayer) Row(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))) {
            Text(modifier = Modifier.fillMaxWidth().offset(y = 6.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(Res.string.wait_for_your_turn))
        }
    }
}

@Composable
@Preview
fun GameScreenPreview() = BlockInvasionTheme {
    Surface(color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground) {
        GameScreen(state = GameState(),
            onEvent = {},
            goBack = {})
    }
}