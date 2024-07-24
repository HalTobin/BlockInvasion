package ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import data.model.Map

@Composable
fun GameMap(
    modifier: Modifier,
    map: Map
) {
    var componentHeight by remember { mutableStateOf(0.dp) }
    var componentWidth by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current
    Box(modifier = modifier
        .onGloballyPositioned {
            componentHeight = with(density) { it.size.height.toDp() }
            componentWidth = with(density) { it.size.width.toDp() } },
        contentAlignment = Alignment.Center) {
        if (map.map.isNotEmpty()) {
            val xCellDp = componentWidth / map.map.size
            val yCellDp = componentHeight / map.map[0].size
            val maxCellDp = min(xCellDp, yCellDp)
            Canvas(modifier = Modifier
                .width(maxCellDp*map.map.size)
                .height(maxCellDp*map.map[0].size)
            ) {
                val xMax = size.width / map.map.size
                val yMax = size.height / map.map[0].size
                val cellSize = kotlin.math.min(xMax, yMax)
                map.map.forEachIndexed { indexX, row ->
                    row.forEachIndexed { indexY, cell ->
                        drawRect(
                            topLeft = Offset(x = indexX * cellSize, y = indexY * cellSize),
                            color = Color(cell.color),
                            size = Size(cellSize, cellSize)
                        )
                    }
                }
            }
        }
    }
}