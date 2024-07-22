package feature.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.Grid
import data.model.Pixel
import data.model.Map
import data.repository.AppPreferences
import data.repository.PreferenceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val preferenceRepository: PreferenceRepository
): ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    private val _preference = MutableStateFlow(AppPreferences())
    val preferences = _preference.asStateFlow()

    private var preferencesJob: Job? = null

    private var currentPlayer = 0

    init {
        listenPreferences()
        _state.update { it.copy(pixelNumber = _preference.value.pixelNumber) }
        when (_state.value.pixelNumber) {
            4 -> _state.update { it.copy(pixelSet = Pixel.COLOR_SET_4.toTypedArray()) }
            5 -> _state.update { it.copy(pixelSet = Pixel.COLOR_SET_5.toTypedArray()) }
            6 -> _state.update { it.copy(pixelSet = Pixel.COLOR_SET_6.toTypedArray()) }
            7 -> _state.update { it.copy(pixelSet = Pixel.COLOR_SET_7.toTypedArray()) }
        }
        val grid = Grid(_preference.value.gridX, _preference.value.gridY)
        _state.update { it.copy(map = Map.generateMap(grid, _state.value.pixelNumber), player = currentPlayer) }
        _state.update { it.copy(playersPixels = _state.value.map.getPlayersPixel()) }
    }

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.PlayerAction -> {
                if (event.player == currentPlayer) {
                    currentPlayer = _state.value.nextPlayer()
                    _state.update { it.copy(map = _state.value.map.changeMap(event.player, event.pixel)) }
                    _state.update { it.copy(playersPixels = _state.value.map.getPlayersPixel(), player = currentPlayer) }
                }
                if (_state.value.map.isGameOver()) {
                    val player0 = _state.value.map.getPlayerScore(0)
                    val player1 = _state.value.map.getPlayerScore(1)
                    _state.update { it.copy(endGame = EndGame(player0, player1)) }
                }
            }
        }
    }

    private fun listenPreferences() {
        preferencesJob?.cancel()
        preferencesJob = viewModelScope.launch {
            preferenceRepository.preferences.collect { _preference.update { it } }
        }
    }

}