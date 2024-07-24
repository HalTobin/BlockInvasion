package feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.Grid
import data.model.Map
import data.repository.AppPreferences
import data.repository.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val preferenceRepository: PreferenceRepository
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private var _preferences = AppPreferences()

    init {
        viewModelScope.launch(Dispatchers.IO) { _preferences = preferenceRepository.getPreferences() }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SendScreenSize -> {
                val grid = Grid(event.x / 32, event.y / 32)
                _state.update { it.copy(map = Map.generateMap(grid, _preferences.nbColors)) }
            }
        }
    }

}