package feature.settings

import androidx.lifecycle.ViewModel
import com.chapeaumoineau.pixelinvasion.feature.settings.SettingsEvent
import com.chapeaumoineau.pixelinvasion.feature.settings.SettingsState
import data.repository.PreferenceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    private val preferencesRepository: PreferenceRepository
): ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        refreshSettings()
    }

    private fun refreshSettings() {
        _state.update { it.copy(
            pixelNumber = preferencesRepository.pixelNumber,
            gridX = preferencesRepository.gridX,
            gridY = preferencesRepository.gridY
        ) }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangePixelNumber -> preferencesRepository.pixelNumber = event.value
            is SettingsEvent.ChangeGriX -> preferencesRepository.gridX = event.value
            is SettingsEvent.ChangeGridY -> preferencesRepository.gridY = event.value
        }
        refreshSettings()
    }

}