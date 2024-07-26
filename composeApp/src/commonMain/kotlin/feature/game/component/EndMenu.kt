package feature.game.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Games
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.pixels
import blockinvasion.composeapp.generated.resources.quit
import blockinvasion.composeapp.generated.resources.restart
import blockinvasion.composeapp.generated.resources.you_lose
import blockinvasion.composeapp.generated.resources.you_win
import blockinvasion.composeapp.generated.resources.your_score
import feature.game.EndGame
import org.jetbrains.compose.resources.stringResource
import ui.composable.Direction
import ui.composable.GameButton

@Composable
fun EndMenu(
    endGame: EndGame,
    quit: () -> Unit,
    restart: () -> Unit
) = Dialog(
    properties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false,
        usePlatformDefaultWidth = false),
    onDismissRequest = { }) {
    var goToGame by remember { mutableStateOf(false) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
        PlayerScore(modifier = Modifier
            .weight(1f)
            .graphicsLayer { rotationZ = 180f },
            won = endGame.isPlayer2Winner,
            pixelCount = endGame.player2Score,
            ratio = endGame.player2Ratio)
        PlayerScore(modifier = Modifier.weight(1f),
            won = endGame.isPlayer1Winner,
            pixelCount = endGame.player1Score,
            ratio = endGame.player1Ratio)
        Row(modifier = Modifier.padding(horizontal = 4.dp)) {
            GameButton(modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                text = stringResource(Res.string.quit),
                onClick = quit,
                direction = Direction.Left,
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                goToGame = goToGame)
            GameButton(modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                text = stringResource(Res.string.restart),
                onClick = {
                    goToGame = true
                    restart()
                },
                direction = Direction.Right,
                icon = Icons.Default.Games,
                goToGame = goToGame)
        }
        Spacer(Modifier.height(40.dp))
    }
}

@Composable
fun PlayerScore(
    modifier: Modifier,
    won: Boolean,
    pixelCount: Int,
    ratio: Float
) = Column(modifier.padding(32.dp),
    horizontalAlignment = Alignment.CenterHorizontally) {
    Text(modifier = Modifier.padding(top = 64.dp, bottom = 32.dp),
        text = stringResource(
        if (won) Res.string.you_win
        else Res.string.you_lose),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.SemiBold)
    Text(modifier = Modifier.padding(bottom = 16.dp),
        text = stringResource(Res.string.your_score),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold)
    Text(text = "${(ratio*100).toInt()}%",
        fontWeight = FontWeight.ExtraBold,
        style = MaterialTheme.typography.displayMedium,
        color = getColorByScore(ratio))
    Text(text = stringResource(Res.string.pixels, pixelCount).uppercase(),
        fontWeight = FontWeight.ExtraBold,
        style = MaterialTheme.typography.headlineSmall,
        color = getColorByScore(ratio))
}

fun getColorByScore(score: Float): Color {
    val red = Color(0xFFFF0000) // Red
    val orange = Color(0xFFFFA500) // Orange
    val green = Color(0xFF00FF00) // Green

    return when {
        score <= 0.5f -> lerp(red, orange, score * 2)
        else -> lerp(orange, green, (score - 0.5f) * 2)
    }
}