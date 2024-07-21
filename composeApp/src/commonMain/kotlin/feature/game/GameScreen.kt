package feature.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import data.model.Pixel
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

    Column(modifier = Modifier.fillMaxSize()) {

        PlayerControls(
            player = 0,
            playerPixels = state.playersPixels,
            currentPlayer = state.player,
            pixels = state.pixelSet,
            onClick = { pixel, player -> onEvent(GameEvent.PlayerAction(player, pixel)) }
        )
        Divider(modifier = Modifier.fillMaxWidth())

        Canvas(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()) {
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

        Divider(modifier = Modifier.fillMaxWidth())
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
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .border(
            width = (if (currentPlayer == player) 2.dp else 0.dp),
            color = Color.Green
        )
        .padding(vertical = 16.dp),
    horizontalArrangement = Arrangement.SpaceAround
) {

    pixels.forEach { pixel ->
        val isFree = !playerPixels.contains(pixel)
        Box(modifier = Modifier.composed {
            size(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if (isFree) Color(pixel.color) else Color.LightGray)
                .clickable((player == currentPlayer) && isFree) { onClick(pixel, player) }
        })
    }

}