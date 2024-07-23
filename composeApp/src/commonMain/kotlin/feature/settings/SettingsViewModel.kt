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
    private val preferenceRepository: PreferenceRepository
): ViewModel() {

    private val _preferences = MutableStateFlow(AppPreferences())
    val preferences = _preferences.asStateFlow()

    private var preferenceJob: Job? = null

    init {
        preferenceJob?.cancel()
        preferenceJob = viewModelScope.launch(Dispatchers.IO) {
            preferenceRepository.preferences.collect { preferences ->
                _preferences.update { preferences } }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangePixelNumber -> viewModelScope.launch(Dispatchers.IO) {
                preferenceRepository.setNbColors(event.value)
            }
            is SettingsEvent.ChangeGriX -> viewModelScope.launch(Dispatchers.IO) {
                preferenceRepository.setGridX(event.value)
            }
            is SettingsEvent.ChangeGridY -> viewModelScope.launch(Dispatchers.IO) {
                preferenceRepository.setGridY(event.value)
            }
        }
    }

}