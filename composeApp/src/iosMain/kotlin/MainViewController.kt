import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import data.createDatastore
import di.KoinInitializer

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
    }
) { App() }