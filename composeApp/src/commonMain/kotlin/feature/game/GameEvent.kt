package feature.game

import data.model.Pixel

sealed class GameEvent {
    data class PlayerAction(val player: Int, val pixel: Pixel): GameEvent()
}