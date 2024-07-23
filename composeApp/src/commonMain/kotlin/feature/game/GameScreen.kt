package feature.game

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.window.Dialog
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.wait_for_your_turn
import data.model.Pixel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.BlockInvasionTheme
import kotlin.math.min

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

        //initial height set at 0.dp
        var componentHeight by remember { mutableStateOf(0.dp) }
        var componentWidth by remember { mutableStateOf(0.dp) }

        // get local density from composable
        val density = LocalDensity.current

        Box(modifier = Modifier.weight(1f).fillMaxWidth()
            .onGloballyPositioned {
                componentHeight = with(density) { it.size.height.toDp() }
                componentWidth = with(density) { it.size.width.toDp() } },
            contentAlignment = Alignment.Center) {
            if (state.map.map.isNotEmpty()) {
                val xCellDp = componentWidth / state.map.map.size
                val yCellDp = componentHeight / state.map.map[0].size
                val maxCellDp = min(xCellDp, yCellDp)
                Canvas(modifier = Modifier
                    .width(maxCellDp*state.map.map.size)
                    .height(maxCellDp*state.map.map[0].size)
                ) {
                    val xMax = size.width / state.map.map.size
                    val yMax = size.height / state.map.map[0].size
                    val cellSize = min(xMax, yMax)
                    state.map.map.forEachIndexed { indexX, row ->
                        row.forEachIndexed { indexY, cell ->
                            drawRect(
                                topLeft = Offset(x = indexX * cellSize, y = indexY * cellSize),
                                color = Color(cell.color),
                                size = Size(cellSize, cellSize)
                            )
                        }
                    }
                }
            }
        }

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