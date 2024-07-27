package feature.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.back_home_description
import blockinvasion.composeapp.generated.resources.game_rules
import blockinvasion.composeapp.generated.resources.language
import blockinvasion.composeapp.generated.resources.nb_colors
import blockinvasion.composeapp.generated.resources.nb_columns
import blockinvasion.composeapp.generated.resources.nb_rows
import blockinvasion.composeapp.generated.resources.section_interface
import blockinvasion.composeapp.generated.resources.settings
import blockinvasion.composeapp.generated.resources.sound_on
import blockinvasion.composeapp.generated.resources.theme
import data.repository.AppPreferences
import data.value.Language
import data.value.Theme
import feature.settings.components.SelectionFromListDialog
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsScreen(
    goBack: () -> Unit,
    preferences: AppPreferences,
    onEvent: (SettingsEvent) -> Unit
) = Column {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = goBack) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.back_home_description))
        }
        Text(modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(Res.string.settings))
    }

    SectionTitle(stringResource(Res.string.game_rules))
    IntEntry(title = stringResource(Res.string.nb_colors),
        min = 4, max = 7,
        value = preferences.nbColors,
        onChange = { onEvent(SettingsEvent.ChangePixelNumber(it)) })
    IntEntry(title = stringResource(Res.string.nb_rows),
        min = 20, max = 30,
        value = preferences.gridX,
        onChange = { onEvent(SettingsEvent.ChangeGriX(it)) })
    IntEntry(title = stringResource(Res.string.nb_columns),
        min = 35, max = 50,
        value = preferences.gridY,
        onChange = { onEvent(SettingsEvent.ChangeGridY(it)) })

    SectionTitle(stringResource(Res.string.section_interface))
    BooleanEntry(title = stringResource(Res.string.sound_on),
        value = preferences.sound,
        onChange = { onEvent(SettingsEvent.ChangeSoundOn(it)) })

    ListEntry(title = stringResource(Res.string.theme),
        currentKey = preferences.theme.key,
        currentText = stringResource(preferences.theme.title),
        items = Theme.list.map { Triple(
            it.key,
            stringResource(it.title)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(it.color)
                    .border(width = 2.dp, color = Color.Black, shape = CircleShape)) } },
        onChange = { onEvent(SettingsEvent.ChangeTheme(it)) })

    ListEntry(title = stringResource(Res.string.language),
        currentKey = preferences.language.key,
        currentText = preferences.language.title,
        items = Language.list.map { Triple(
            it.key,
            it.title) {
                Image(modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.Black, shape = CircleShape),
                    painter = painterResource(it.flag),
                    contentDescription = it.title)
        } },
        onChange = { onEvent(SettingsEvent.ChangeLanguage(it)) })
}

@Composable
fun SectionTitle(title: String) = Text(text = title,
    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
    style = MaterialTheme.typography.headlineSmall,
    fontWeight = FontWeight.SemiBold,
    color = MaterialTheme.colorScheme.primary)

@Composable
fun BooleanEntry(
    title: String,
    value: Boolean,
    onChange: (Boolean) -> Unit
) = Row(
    modifier = Modifier.fillMaxWidth().height(48.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Text(text = title,
        modifier = Modifier.padding(horizontal = 16.dp).weight(1f))
    Switch(modifier = Modifier.padding(end = 16.dp),
        checked = value,
        onCheckedChange = { onChange(it) }
    )
}

@Composable
fun IntEntry(
    title: String,
    min: Int,
    max: Int,
    value: Int,
    onChange: (Int) -> Unit
) = Row(
    modifier = Modifier.fillMaxWidth().height(48.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Text(text = title,
        modifier = Modifier.padding(horizontal = 16.dp).weight(1f))
    IconButton(onClick = { if (value > min) onChange(value - 1) }) {
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
    }
    Text(text = value.toString(),
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold)
    IconButton(onClick = { if (value < max) onChange(value + 1) }) {
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
    }
}

@Composable
fun ListEntry(
    title: String,
    items: List<Triple<String, String, (@Composable () -> Unit)?>>, // Pair <key, title>
    currentKey: String,
    currentText: String,
    onChange: (String) -> Unit
) {
    var dialogState by remember { mutableStateOf(false) }

    if (dialogState) SelectionFromListDialog(
        title = title,
        items = items,
        currentKey = currentKey,
        onSelect = {
            onChange(it)
            dialogState = false },
        onDismiss = { dialogState = false }
    )

    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable { dialogState = true }
            .padding(horizontal = 16.dp)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title,
            modifier = Modifier.weight(1f))
        Text(text = currentText,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold)
    }
}