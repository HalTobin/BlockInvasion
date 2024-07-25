package feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.app_name
import blockinvasion.composeapp.generated.resources.new_game
import blockinvasion.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.Screen
import ui.composable.GameMap
import ui.theme.BlockInvasionTheme

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    goTo: (Screen) -> Unit
) {
    val screenSize = remember { mutableStateOf(Pair(-1, -1)) }
    Layout(
        measurePolicy = { measurables, constraints ->
            // Use the max width and height from the constraints
            val width = constraints.maxWidth
            val height = constraints.maxHeight

            screenSize.value = Pair(width, height)
            onEvent(HomeEvent.SendScreenSize(width, height))
            println("Width: $width, height: $height")

            // Measure and place children composables
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            layout(width, height) {
                var yPosition = 0
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = yPosition)
                    yPosition += placeable.height
                }
            }
        },
        content = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    GameMap(modifier = Modifier.fillMaxSize(),
                        map = state.map)
                    Box(Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f)))
                    Text(modifier = Modifier.padding(top = 32.dp).align(Alignment.TopCenter),
                        text = stringResource(Res.string.app_name).uppercase(),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.ExtraBold)
                    Column(modifier = Modifier.padding(horizontal = 64.dp)
                        .widthIn(max = 384.dp)) {
                        HomeScreenButton(
                            text = stringResource(Res.string.new_game),
                            icon = Icons.Default.Games,
                            onClick = { goTo(Screen.Game) })
                        Spacer(modifier = Modifier.height(24.dp))
                        HomeScreenButton(
                            text = stringResource(Res.string.settings),
                            icon = Icons.Default.Settings,
                            onClick = { goTo(Screen.Settings) })
                    }
                }
            }
        }
    )
}

@Composable
fun HomeScreenButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) = Button(modifier = Modifier.fillMaxWidth().height(48.dp),
    shape = RoundedCornerShape(4.dp),
    onClick = onClick) {
    Icon(imageVector = icon,
        tint = MaterialTheme.colorScheme.onPrimary,
        contentDescription = null)
    Text(modifier = Modifier.weight(1f),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        text = text.uppercase())
}

@Composable
@Preview
fun HomeScreenPreview() = BlockInvasionTheme {
    HomeScreen(state = HomeState(), onEvent = {}, goTo = {}) }