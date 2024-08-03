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
import blockinvasion.composeapp.generated.resources.about
import blockinvasion.composeapp.generated.resources.about_credits
import blockinvasion.composeapp.generated.resources.about_version
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
import feature.settings.components.BooleanEntry
import feature.settings.components.InfoEntry
import feature.settings.components.IntEntry
import feature.settings.components.ListEntry
import feature.settings.components.SectionTitle
import feature.settings.components.SelectionFromListDialog
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.audio.AppSound
import ui.audio.SoundController

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

    SectionTitle(stringResource(Res.string.about))
    InfoEntry(title = stringResource(Res.string.about_version), value = "v1.0.0")
    InfoEntry(title = stringResource(Res.string.about_credits), value = "MoineauFactory")

}