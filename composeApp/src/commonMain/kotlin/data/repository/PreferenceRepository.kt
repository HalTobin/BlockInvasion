package data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import data.value.PrefDefault
import data.value.PrefKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferenceRepositoryImpl(
    private val prefs: DataStore<Preferences>
): PreferenceRepository {
    override suspend fun reset() {
        setPixelNumber(PrefDefault.PIXEL_NUMBER)
        setGridX(PrefDefault.GRID_X)
        setGridY(PrefDefault.GRID_Y)
    }

    override suspend fun setPixelNumber(value: Int) =
        updateIntPreference(PrefKey.PIXEL_NUMBER, value)

    override suspend fun setGridX(value: Int) =
        updateIntPreference(PrefKey.GRID_X, value)

    override suspend fun setGridY(value: Int) =
        updateIntPreference(PrefKey.GRID_Y, value)

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

}

val DataStore<Preferences>.appPreferences get() = this.data.map { dataStore ->
    AppPreferences(
        pixelNumber = dataStore.getIntPreference(PrefKey.PIXEL_NUMBER, PrefDefault.PIXEL_NUMBER),
        gridX = dataStore.getIntPreference(PrefKey.GRID_X, PrefDefault.GRID_X),
        gridY = dataStore.getIntPreference(PrefKey.GRID_Y, PrefDefault.GRID_Y)
    )
}

fun Preferences.getIntPreference(key: String, default: Int): Int {
    val dataStoreKey = intPreferencesKey(key)
    return this[dataStoreKey] ?: default
}

interface PreferenceRepository {
    suspend fun reset()
    suspend fun setGridX(value: Int)
    suspend fun setGridY(value: Int)
    suspend fun setPixelNumber(value: Int)

    suspend fun getPreferences(): AppPreferences
    val preferences: Flow<AppPreferences>
}

data class AppPreferences(
    val pixelNumber: Int = PrefDefault.PIXEL_NUMBER,
    val gridX: Int = PrefDefault.GRID_X,
    val gridY: Int = PrefDefault.GRID_Y
)