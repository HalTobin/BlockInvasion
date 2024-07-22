package feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.pixelinvasion.feature.settings.SettingsEvent
import data.repository.AppPreferences
import data.repository.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferencesRepository: PreferenceRepository
): ViewModel() {

    private val _preference = MutableStateFlow(AppPreferences())
    val preferences = _preference.asStateFlow()

    private var preferencesJob: Job? = null

    init { listenPreferences() }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangePixelNumber -> viewModelScope.launch(Dispatchers.IO) {
                preferencesRepository.setPixelNumber(event.value)
            }
            is SettingsEvent.ChangeGriX -> viewModelScope.launch(Dispatchers.IO) {
                preferencesRepository.setGridX(event.value)
            }
            is SettingsEvent.ChangeGridY -> viewModelScope.launch(Dispatchers.IO) {
                preferencesRepository.setGridY(event.value)
            }
        }
    }

    private fun listenPreferences() {
        preferencesJob?.cancel()
        preferencesJob = viewModelScope.launch {
            preferencesRepository.preferences.collect { _preference.update { it } }
        }
    }

}