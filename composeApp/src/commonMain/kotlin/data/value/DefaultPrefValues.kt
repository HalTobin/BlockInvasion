package data.value

import androidx.compose.ui.graphics.Color
import blockinvasion.composeapp.generated.resources.Res
import blockinvasion.composeapp.generated.resources.flag_en
import blockinvasion.composeapp.generated.resources.flag_es
import blockinvasion.composeapp.generated.resources.flag_fr
import blockinvasion.composeapp.generated.resources.flag_ru
import blockinvasion.composeapp.generated.resources.theme_dark
import blockinvasion.composeapp.generated.resources.theme_light
import org.jetbrains.compose.resources.DrawableResource
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

enum class Language(val key: String, val title: String, val flag: DrawableResource) {
    English(key = PrefDefault.LANGUAGE_EN, title = "English", flag = Res.drawable.flag_en),
    French(key = PrefDefault.LANGUAGE_FR, title = "Français", flag = Res.drawable.flag_fr),
    Spanish(key = PrefDefault.LANGUAGE_ES, title = "Español", flag = Res.drawable.flag_es),
    Russian(key = PrefDefault.LANGUAGE_RU, title = "Русский", flag = Res.drawable.flag_ru);
    companion object {
        val list = listOf(English, French, Spanish, Russian)
    }
}