package ui.composable

import androidx.compose.runtime.Composable

@Composable
expect fun NavBackHandler(enabled: Boolean = true, onBack: ()-> Unit)