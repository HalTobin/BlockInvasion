package feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.new_game
import blockinvasion.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.Screen
import ui.theme.BlockInvasionTheme

@Composable
fun HomeScreen(
    goTo: (Screen) -> Unit
) = Surface(
    color = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.onBackground
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Button(onClick = { goTo(Screen.Game) }) {
                Text(text = stringResource(Res.string.new_game).uppercase())
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { goTo(Screen.Settings) }) {
                Text(text = stringResource(Res.string.settings).uppercase())
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() = BlockInvasionTheme { HomeScreen(goTo = {}) }