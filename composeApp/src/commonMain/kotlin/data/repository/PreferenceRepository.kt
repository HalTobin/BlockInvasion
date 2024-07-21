package data.repository

import data.value.PrefValues

class PreferenceRepositoryEasy {
    fun reset() {
        gridX = PrefValues.PrefDefault.GRID_X
        gridY = PrefValues.PrefDefault.GRID_Y
        pixelNumber = PrefValues.PrefDefault.PIXEL_NUMBER
    }

    var gridX: Int = PrefValues.PrefDefault.GRID_X

    var gridY: Int = PrefValues.PrefDefault.GRID_Y

    var pixelNumber: Int = PrefValues.PrefDefault.PIXEL_NUMBER
}

class PreferenceRepositoryImpl: PreferenceRepository {
    override fun reset() {
        gridX = PrefValues.PrefDefault.GRID_X
        gridY = PrefValues.PrefDefault.GRID_Y
        pixelNumber = PrefValues.PrefDefault.PIXEL_NUMBER
    }

    override var gridX: Int = PrefValues.PrefDefault.GRID_X

    override var gridY: Int = PrefValues.PrefDefault.GRID_Y

    override var pixelNumber: Int = PrefValues.PrefDefault.PIXEL_NUMBER
}

interface PreferenceRepository {
    fun reset()
    var pixelNumber: Int
    var gridX: Int
    var gridY: Int
}