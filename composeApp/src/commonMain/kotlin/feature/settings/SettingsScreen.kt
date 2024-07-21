package feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chapeaumoineau.pixelinvasion.feature.settings.SettingsEvent
import com.chapeaumoineau.pixelinvasion.feature.settings.SettingsState

@Composable
fun SettingsScreen(
    goBack: () -> Unit,
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit
) {
    
    Column {
        IntEntry(
            title = "Number of colors",
            min = 4,
            max = 7,
            value = state.pixelNumber,
            onChange = { onEvent(SettingsEvent.ChangePixelNumber(it)) }
        )
        IntEntry(
            title = "Number of rows",
            min = 20,
            max = 30,
            value = state.gridX,
            onChange = { onEvent(SettingsEvent.ChangeGriX(it)) }
        )
        IntEntry(
            title = "Number of columns",
            min = 35,
            max = 50,
            value = state.gridY,
            onChange = { onEvent(SettingsEvent.ChangeGridY(it)) }
        )
    }
    
}

@Composable
fun IntEntry(
    title: String,
    min: Int,
    max: Int,
    value: Int,
    onChange: (Int) -> Unit
) = Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .weight(1f),
        text = title
    )
    IconButton(onClick = { if (value > min) onChange(value - 1) }) {
        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
    }
    Text(text = value.toString())
    IconButton(onClick = { if (value < max) onChange(value + 1) }) {
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
    }
}