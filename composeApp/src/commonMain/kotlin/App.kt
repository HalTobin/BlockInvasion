import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope
import ui.MainScreen
import ui.MainViewModel
import ui.theme.BlockInvasionTheme

@Composable
@Preview
fun App() = KoinContext {
    val mainViewModel = koinViewModel<MainViewModel>()
    val preferences by mainViewModel.preferences.collectAsState()

    BlockInvasionTheme(theme = preferences.theme) {
        MainScreen(preferences = preferences)
    }
}

@Composable
inline fun <reified T: ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel { scope.get<T>() }
}