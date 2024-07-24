package feature.game

import data.model.Pixel

sealed class GameEvent {
    data object PauseGame: GameEvent()
    data object UnpauseGame: GameEvent()
    data object Reset: GameEvent()
    data class PlayerAction(val player: Int, val pixel: Pixel): GameEvent()
}