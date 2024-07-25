package feature.game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import feature.game.component.PauseMenu
import feature.game.component.PauseMenuAction
import feature.game.component.PlayerControls
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.composable.GameMap
import ui.theme.BlockInvasionTheme

@Composable
fun GameScreen(
    goBack: () -> Unit,
    goToSettings: () -> Unit,
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

    Box {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            PlayerControls(
                player = 2,
                playerPixels = state.playersPixels,
                currentPlayer = state.player,
                pixels = state.pixelSet,
                onClick = { pixel, player -> onEvent(GameEvent.PlayerAction(player, pixel)) }
            )

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            val interactionSource = remember { MutableInteractionSource() }
            GameMap(modifier = Modifier.weight(1f).fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { onEvent(GameEvent.PauseGame) },
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
        if (state.pause) PauseMenu(
            action = { action -> when (action) {
                PauseMenuAction.Resume -> onEvent(GameEvent.UnpauseGame)
                PauseMenuAction.Restart -> onEvent(GameEvent.Reset)
                PauseMenuAction.Settings -> goToSettings()
                PauseMenuAction.Quit -> goBack()
            } }
        )
    }

}

@Composable
@Preview
fun GameScreenPreview() = BlockInvasionTheme {
    Surface(color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground) {
        GameScreen(state = GameState(),
            onEvent = {},
            goToSettings = {},
            goBack = {})
    }
}