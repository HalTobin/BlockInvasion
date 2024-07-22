package feature.settings

import androidx.lifecycle.viewModelScope
import com.chapeaumoineau.pixelinvasion.feature.settings.SettingsEvent
import data.repository.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import ui.BaseViewModel

class SettingsViewModel(
    private val preferencesRepository: PreferenceRepository
): BaseViewModel() {

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

}