package ui.composable

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun NavBackHandler(enabled: Boolean, onBack: () -> Unit) {
    BackHandler(enabled = enabled, onBack = onBack)
}