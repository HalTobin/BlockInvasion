package feature.game.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.quit
import blockinvasion.composeapp.generated.resources.restart
import blockinvasion.composeapp.generated.resources.resume
import blockinvasion.composeapp.generated.resources.settings
import feature.game.GameEvent
import org.jetbrains.compose.resources.stringResource

@Composable
fun PauseMenu(
    modifier: Modifier = Modifier,
    action: (PauseMenuAction) -> Unit
) = Column(modifier = modifier
    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
    .clickable { action(PauseMenuAction.Resume) }
    .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PauseMenuButton(action = PauseMenuAction.Resume, onClick = { action(it) })
            PauseMenuButton(action = PauseMenuAction.Restart, onClick = { action(it) })
            PauseMenuButton(action = PauseMenuAction.Settings, onClick = { action(it) })
            PauseMenuButton(action = PauseMenuAction.Quit, onClick = { action(it) })
        }
    }
}

@Composable
fun PauseMenuButton(
    action: PauseMenuAction,
    onClick: (PauseMenuAction) -> Unit
) = TextButton(modifier = Modifier.padding(bottom = 16.dp),
    onClick = { onClick(action) }) {
    Text(text = stringResource(when (action) {
        PauseMenuAction.Resume -> Res.string.resume
        PauseMenuAction.Restart -> Res.string.restart
        PauseMenuAction.Settings -> Res.string.settings
        PauseMenuAction.Quit -> Res.string.quit
    }).uppercase(),
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineLarge)
}

enum class PauseMenuAction { Resume, Restart, Settings, Quit }