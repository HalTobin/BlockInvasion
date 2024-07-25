package feature.game

import data.model.Grid
import data.model.Pixel
import data.model.Map

data class GameState(
    val player: Int = 1,
    val map: Map = Map(),
    val grid: Grid = Grid(27, 46),
    val nbColors: Int = 6,
    val playersPixels: List<Pixel> = emptyList(),
    val endGame: EndGame? = null,
    val pixelSet: Array<Pixel> = Pixel.COLOR_SET_6.toTypedArray(),
    val pause: Boolean = false
) {
    fun nextPlayer(): Int = if (player == 1) 2 else 1

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as GameState

        if (pause != other.pause) return false
        if (player != other.player) return false
        if (map != other.map) return false
        if (grid != other.grid) return false
        if (nbColors != other.nbColors) return false
        if (playersPixels != other.playersPixels) return false
        if (endGame != other.endGame) return false
        if (!pixelSet.contentEquals(other.pixelSet)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = player
        result = 31 * result + pause.hashCode()
        result = 31 * result + map.hashCode()
        result = 31 * result + grid.hashCode()
        result = 31 * result + nbColors
        result = 31 * result + playersPixels.hashCode()
        result = 31 * result + (endGame?.hashCode() ?: 0)
        result = 31 * result + pixelSet.contentHashCode()
        return result
    }
}

data class EndGame(
    val player0Score: Int,
    val player1Score: Int
)