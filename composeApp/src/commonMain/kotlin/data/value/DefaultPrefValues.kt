package data.value

import androidx.compose.ui.graphics.Color
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.theme_dark
import blockinvasion.composeapp.generated.resources.theme_light
import org.jetbrains.compose.resources.StringResource

object PrefKey {
    const val NB_COLORS = "nb_colors"
    const val GRID_X = "grid_x"
    const val GRID_Y = "grid_y"
    const val THEME = "theme"
    const val LANGUAGE = "language"
}

object PrefDefault {
    const val NB_COLORS = 6
    const val GRID_X = 27
    const val GRID_Y = 46

    const val THEME_DARK = "dark"
    const val THEME_LIGHT = "dark"
    const val THEME = THEME_DARK

    const val LANGUAGE_EN = "en"
    const val LANGUAGE_FR = "fr"
    const val LANGUAGE_RU = "ru"
    const val LANGUAGE_ES = "es"
    const val LANGUAGE = LANGUAGE_EN
}

enum class Theme(val color: Color, val key: String, val title: StringResource) {
    Dark(color = Color.DarkGray, key = PrefDefault.THEME_DARK, title = Res.string.theme_dark),
    Light(color = Color.White, key = PrefDefault.THEME_LIGHT, title = Res.string.theme_light);
    companion object {
        val list = listOf(Dark, Light)
    }
}

enum class Language(val key: String, val title: String) {
    English(key = PrefDefault.LANGUAGE_EN, title = "English"),
    French(key = PrefDefault.LANGUAGE_FR, title = "Français"),
    Spanish(key = PrefDefault.LANGUAGE_ES, title = "Español"),
    Russian(key = PrefDefault.LANGUAGE_RU, title = "Русский");
    companion object {
        val list = listOf(English, French)
    }
}