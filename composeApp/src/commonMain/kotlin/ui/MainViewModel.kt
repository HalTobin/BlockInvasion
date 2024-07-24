package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.AppPreferences
import data.repository.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    preferenceRepository: PreferenceRepository
): ViewModel() {

    private var preferencesJob: Job? = null

    private val _preferences = MutableStateFlow(AppPreferences())
    val preferences = _preferences.asStateFlow()

    init {
        preferencesJob?.cancel()
        preferencesJob = viewModelScope.launch(Dispatchers.IO) {
            preferenceRepository.preferences.collect { preferences ->
                _preferences.update { preferences }
            }
        }
    }

}