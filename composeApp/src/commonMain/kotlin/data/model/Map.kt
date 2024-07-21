package data.model

data class Map(
    val map: Array<Array<Pixel>> = emptyArray(),
) {

    fun isGameOver(): Boolean =
        map.flatten().distinct().size == 2

    fun getPlayersPixel(): List<Pixel> {
        val indexesPlayer0 = getPlayerIndexes(0)
        val indexesPlayer1 = getPlayerIndexes(1)
        val pixel0 = map[indexesPlayer0.rowIndex][indexesPlayer0.colIndex]
        val pixel1 = map[indexesPlayer1.rowIndex][indexesPlayer1.colIndex]
        return listOf(pixel0, pixel1)
    }

    fun getPlayerScore(player: Int): Int {
        val indexes = getPlayerIndexes(player)
        val playerPixel = map[indexes.rowIndex][indexes.colIndex]
        return map.flatten().count { playerPixel == it }
    }

    fun changeMap(player: Int, pixel: Pixel): Map {
        val newMap = map.copyOf()

        val indexes = getPlayerIndexes(player)

        val originalPixel = map[indexes.rowIndex][indexes.colIndex]

        // Change the value of the starting cell
        newMap[indexes.rowIndex][indexes.colIndex] = pixel

        // Update adjacent cells with the same value
        updateAdjacentCells(newMap, indexes.rowIndex, indexes.colIndex, pixel, originalPixel)

        //Log.i("MAP", "Map has been updated!")

        return Map(newMap)
    }

    private fun updateAdjacentCells(newMap: Array<Array<Pixel>>, row: Int, col: Int, newPixel: Pixel, originalPixel: Pixel) {
        // Define the indices of adjacent cells
        val adjacentIndices = listOf(
            Pair(row - 1, col),
            Pair(row + 1, col),
            Pair(row, col - 1),
            Pair(row, col + 1)
        )

        // Update adjacent cells with the same value and belonging to the same player
        for ((adjRow, adjCol) in adjacentIndices) {
            if (isValidIndex(adjRow, adjCol) && map[adjRow][adjCol] == originalPixel) {
                newMap[adjRow][adjCol] = newPixel
                // Recursively update adjacent cells of the same color and belonging to the same player
                updateAdjacentCells(newMap, adjRow, adjCol, newPixel, originalPixel)
            }
        }
    }

    private fun isValidIndex(row: Int, col: Int): Boolean {
        return row in 0 until map.size && col in 0 until map[0].size
    }

    private fun getPlayerIndexes(player: Int): MapIndexes {
        val rowIndex: Int
        val colIndex: Int
        // Determine the player's position
        if (player == 1) {
            rowIndex = 0
            colIndex = map[0].size - 1
        } else {
            rowIndex = map.size - 1
            colIndex = 0
        }

        return MapIndexes(rowIndex = rowIndex, colIndex = colIndex)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Map

        return map.contentDeepEquals(other.map)
    }

    override fun hashCode(): Int {
        return map.contentDeepHashCode()
    }

    companion object {
        fun generateMap(grid: Grid, pixelNumber: Int): Map {
            val newMap = Array(grid.x) { Array(grid.y) { Pixel.getRandomPixel(pixelNumber) } }
            return Map(newMap)
        }

    }

}

data class MapIndexes(
    val rowIndex: Int,
    val colIndex: Int
)