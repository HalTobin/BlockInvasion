import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.MainScreen
import ui.MainViewModel
import ui.theme.BlockInvasionTheme

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() = KoinContext {
    val mainViewModel = koinViewModel<MainViewModel>()
    val preferences by mainViewModel.preferences.collectAsState()

    BlockInvasionTheme(theme = preferences.theme) {
        MainScreen(preferences = preferences)
    }
}