package feature.timer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import feature.config.ui.ConfigScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

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
        LaunchedEffect(key) {
            model.effects
                .flowOn(Dispatchers.Main.immediate)
                .collect {
                    when (it) {
                        TimerEffect.OpenSettings -> navigator.push(ConfigScreen())
                    }
                }
        }

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
                            model.onAction(TimerAction.SettingsTap)
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        4.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    when (state.value.state) {
                        TimerState.State.PAUSE -> IconButton(onClick = {
                            model.onAction(TimerAction.Start)
                        }) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Play")
                        }

                        TimerState.State.RUNNING -> IconButton(onClick = {
                            model.onAction(
                                TimerAction.Pause
                            )
                        }) {
                            Icon(Icons.Default.Pause, contentDescription = "Pause")
                        }
                    }
                }
            }
        }
    }

}