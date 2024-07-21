package data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlin.random.Random

sealed class Pixel(val color: Int) {
    object Alpha: Pixel(color = Color.Blue.toArgb())
    object Beta: Pixel(color = Color.Red.toArgb())
    object Gamma: Pixel(color = Color.Yellow.toArgb())
    object Delta: Pixel(color = Color.Green.toArgb())
    object Epsilon: Pixel(color = 0xFFFFA500.toInt())
    object Zeta: Pixel(color = Color.Cyan.toArgb())
    object Theta: Pixel(color = Color.White.toArgb())

    companion object {
        val COLOR_SET_4 = listOf(Alpha, Beta, Gamma, Delta)
        val COLOR_SET_5 = listOf(Alpha, Beta, Gamma, Delta, Epsilon)
        val COLOR_SET_6 = listOf(Alpha, Beta, Gamma, Delta, Epsilon, Zeta)
        val COLOR_SET_7 = listOf(Alpha, Beta, Gamma, Delta, Epsilon, Zeta, Theta)

        private fun getFromInt(value: Int): Pixel {
            return when (value) {
                1 -> Alpha
                2 -> Beta
                3 -> Gamma
                4 -> Delta
                5 -> Epsilon
                6 -> Zeta
                7 -> Theta
                else -> { throw IllegalArgumentException() }
            }
        }

        fun getRandomPixel(pixelNumber: Int): Pixel = getFromInt(Random.nextInt(1, pixelNumber))
    }

}