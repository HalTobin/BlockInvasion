package data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import data.value.Language
import data.value.PrefDefault
import data.value.PrefKey
import data.value.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferenceRepositoryImpl(
    private val prefs: DataStore<Preferences>
): PreferenceRepository {
    override suspend fun reset() {
        setNbColors(PrefDefault.NB_COLORS)
        setGridX(PrefDefault.GRID_X)
        setGridY(PrefDefault.GRID_Y)
    }

    override suspend fun setNbColors(value: Int) =
        updateIntPreference(PrefKey.NB_COLORS, value)

    override suspend fun setGridX(value: Int) =
        updateIntPreference(PrefKey.GRID_X, value)

    override suspend fun setGridY(value: Int) =
        updateIntPreference(PrefKey.GRID_Y, value)

    override suspend fun setTheme(value: String) =
        updateStringPreference(PrefKey.THEME, value)

    override suspend fun setLanguage(value: String) =
        updateStringPreference(PrefKey.LANGUAGE, value)

    override suspend fun getPreferences(): AppPreferences =
        prefs.appPreferences.first()

    override val preferences: Flow<AppPreferences>
        get() = prefs.appPreferences

    private suspend fun updateIntPreference(key: String, value: Int) {
        prefs.edit { dataStore ->
            val dataStoreKey = intPreferencesKey(key)
            dataStore[dataStoreKey] = value
        }
    }

    private suspend fun updateStringPreference(key: String, value: String) {
        prefs.edit { dataStore ->
            val dataStoreKey = stringPreferencesKey(key)
            dataStore[dataStoreKey] = value
        }
    }

}

val DataStore<Preferences>.appPreferences get() = this.data.map { dataStore ->
    AppPreferences(
        nbColors = dataStore.getIntPreference(PrefKey.NB_COLORS, PrefDefault.NB_COLORS),
        gridX = dataStore.getIntPreference(PrefKey.GRID_X, PrefDefault.GRID_X),
        gridY = dataStore.getIntPreference(PrefKey.GRID_Y, PrefDefault.GRID_Y),
        theme = dataStore.getStringPreference(PrefKey.THEME, PrefDefault.THEME).toTheme(),
        language = dataStore.getStringPreference(PrefKey.LANGUAGE, PrefDefault.LANGUAGE).toLanguage()
    )
}

fun String.toTheme(): Theme = when (this) {
    Theme.Dark.key -> Theme.Dark
    Theme.Light.key -> Theme.Light
    Theme.DarkOled.key -> Theme.DarkOled
    else -> Theme.Dark
}

fun String.toLanguage(): Language = when (this) {
    Language.English.key -> Language.English
    Language.French.key -> Language.French
    Language.Spanish.key -> Language.Spanish
    Language.Russian.key -> Language.Russian
    else -> Language.English
}

fun Preferences.getIntPreference(key: String, default: Int): Int {
    val dataStoreKey = intPreferencesKey(key)
    return this[dataStoreKey] ?: default
}

fun Preferences.getStringPreference(key: String, default: String): String {
    val dataStoreKey = stringPreferencesKey(key)
    return this[dataStoreKey] ?: default
}

interface PreferenceRepository {
    suspend fun reset()
    suspend fun setGridX(value: Int)
    suspend fun setGridY(value: Int)
    suspend fun setNbColors(value: Int)
    suspend fun setTheme(value: String)
    suspend fun setLanguage(value: String)

    suspend fun getPreferences(): AppPreferences
    val preferences: Flow<AppPreferences>
}

data class AppPreferences(
    val nbColors: Int = PrefDefault.NB_COLORS,
    val gridX: Int = PrefDefault.GRID_X,
    val gridY: Int = PrefDefault.GRID_Y,
    val theme: Theme = Theme.Dark,
    val language: Language = Language.English
)