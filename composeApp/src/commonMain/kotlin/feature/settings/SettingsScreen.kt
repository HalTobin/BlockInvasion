package feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chapeaumoineau.pixelinvasion.feature.settings.SettingsEvent
import data.repository.AppPreferences

@Composable
fun SettingsScreen(
    goBack: () -> Unit,
    preferences: AppPreferences,
    onEvent: (SettingsEvent) -> Unit
) {
    
    Column {
        IntEntry(
            title = "Number of colors",
            min = 4,
            max = 7,
            value = preferences.pixelNumber,
            onChange = { onEvent(SettingsEvent.ChangePixelNumber(it)) }
        )
        IntEntry(
            title = "Number of rows",
            min = 20,
            max = 30,
            value = preferences.gridX,
            onChange = { onEvent(SettingsEvent.ChangeGriX(it)) }
        )
        IntEntry(
            title = "Number of columns",
            min = 35,
            max = 50,
            value = preferences.gridY,
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
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
    }
    Text(text = value.toString())
    IconButton(onClick = { if (value < max) onChange(value + 1) }) {
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
    }
}