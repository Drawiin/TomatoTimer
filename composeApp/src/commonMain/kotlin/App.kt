import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import core.ui.foundation.TomatoTheme
import di.appModule
import feature.timer.di.timerModule
import feature.timer.ui.TimerScreen
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule, timerModule)
    }) {
        val isDarkTheme = isSystemInDarkTheme()
        var darkTheme by rememberSaveable { mutableStateOf(isDarkTheme) }

        TomatoTheme(darkTheme = darkTheme) {
            Navigator(
                TimerScreen(
                    toggleTheme = { darkTheme = !darkTheme },
                    darkTheme = darkTheme
                )
            ) {
                SlideTransition(it)
            }
        }
    }
}