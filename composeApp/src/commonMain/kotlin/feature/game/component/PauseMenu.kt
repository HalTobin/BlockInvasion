package feature.game.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.quit
import blockinvasion.composeapp.generated.resources.restart
import blockinvasion.composeapp.generated.resources.resume
import blockinvasion.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.stringResource

@Composable
fun PauseMenu(
    action: (PauseMenuAction) -> Unit
) = Dialog(
    properties = DialogProperties(),
    onDismissRequest = { action(PauseMenuAction.Resume) }
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        PauseMenuButton(action = PauseMenuAction.Resume, onClick = { action(it) })
        PauseMenuButton(action = PauseMenuAction.Restart, onClick = { action(it) })
        PauseMenuButton(action = PauseMenuAction.Settings, onClick = { action(it) })
        PauseMenuButton(action = PauseMenuAction.Quit, onClick = { action(it) })
    } }

@Composable
fun PauseMenuButton(
    action: PauseMenuAction,
    onClick: (PauseMenuAction) -> Unit
) = MenuButton(text = stringResource(when (action) {
    PauseMenuAction.Resume -> Res.string.resume
    PauseMenuAction.Restart -> Res.string.restart
    PauseMenuAction.Settings -> Res.string.settings
    PauseMenuAction.Quit -> Res.string.quit
}).uppercase(),
    onClick = { onClick(action) })

enum class PauseMenuAction { Resume, Restart, Settings, Quit }