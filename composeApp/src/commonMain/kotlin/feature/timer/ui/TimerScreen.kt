package feature.timer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import feature.config.ui.ConfigScreen

@Immutable
data class TimerScreen(
    val toggleTheme: () -> Unit,
    val darkTheme: Boolean
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val model = getScreenModel<TimerModel>()
        val state = model.state.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Timer") },
                    navigationIcon = {
                        IconButton(onClick = toggleTheme) {
                            Icon(
                                if (darkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = "Settings"
                            )
                        }

                    },
                    actions = {
                        IconButton(onClick = {
                            navigator.push(ConfigScreen())
                        }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    })
            }
        ) {

            Column(
                modifier = Modifier.fillMaxSize().padding(it).padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    state.value.time,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }

}