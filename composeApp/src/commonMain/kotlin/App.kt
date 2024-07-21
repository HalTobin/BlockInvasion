import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import ui.MainScreen

@Composable
@Preview
fun App() = KoinContext {
    MainScreen()
}