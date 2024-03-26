package feature.timer.ui

import cafe.adriel.voyager.core.model.screenModelScope
import core.arch.InteractionModel
import feature.timer.data.timer.TimerRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class TimerModel(
    private val repository: TimerRepository
) : InteractionModel<TimerState, TimerAction, TimerEffect>(TimerState()) {
    private var elapsedTime = Duration.ZERO
    private var timerJob: Job? = null

    override fun onAction(action: TimerAction) {
        screenModelScope.launch {
            when (action) {
                TimerAction.SettingsTap -> {
                    mutableEffects.emit(TimerEffect.OpenSettings)
                }

                TimerAction.Pause -> onPause()
                TimerAction.Skip -> TODO()
                TimerAction.Start -> onStart()
            }
        }
    }

    private fun onStart() {
        println("${"=".repeat(20)}Timer on star${"=".repeat(20)}")
        timerJob?.cancel()
        mutableState.update { it.copy(state = TimerState.State.RUNNING) }
        timerJob = screenModelScope.launch {
            val config = repository.getTimerConfig()
            val remainingTime = config.pomodoroDuration.minus(elapsedTime).inWholeSeconds.toInt()
            repeat(remainingTime) {
                elapsedTime = elapsedTime.plus(it.seconds)
                config.pomodoroDuration.minus(elapsedTime).toComponents { minutes, seconds, _ ->
                    mutableState.value = mutableState.value.copy(
                        time = "${
                            minutes.toString().padStart(2, '0')
                        }:${seconds.toString().padStart(2, '0')}"
                    )
                }
                delay(1000)
            }
        }
    }

    private fun onPause() {
        timerJob?.cancel()
        mutableState.update { it.copy(state = TimerState.State.PAUSE) }
    }
}

data class TimerState(
    val time: String = "00:00",
    val state: State = State.PAUSE,
    val mode: Mode = Mode.WORK
) {
    enum class State {
        PAUSE,
        RUNNING
    }

    enum class Mode {
        WORK,
        BREAK
    }
}

sealed interface TimerAction {
    data object SettingsTap : TimerAction
    data object Start : TimerAction
    data object Pause : TimerAction
    data object Skip : TimerAction
}

sealed interface TimerEffect {
    data object OpenSettings : TimerEffect
}