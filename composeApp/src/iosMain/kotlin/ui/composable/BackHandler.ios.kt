package ui.composable

import androidx.compose.runtime.Composable

// NavBackHandler has no effect on iOS
@Composable
actual fun NavBackHandler(enabled: Boolean, onBack: () -> Unit) {}