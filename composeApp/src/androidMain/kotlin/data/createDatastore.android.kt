package data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDatastore(context: Context): DataStore<Preferences> =
    createDatastore { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath }