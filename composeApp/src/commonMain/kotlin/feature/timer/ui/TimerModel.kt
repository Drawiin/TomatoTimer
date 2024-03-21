package feature.timer.ui

import cafe.adriel.voyager.core.model.screenModelScope
import core.arch.InteractionModel
import feature.timer.data.timer.TimerRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimerModel(
    private val repository: TimerRepository
) : InteractionModel<TimerState, TimerAction, TimerEffect>(TimerState("00:00", 0)) {
    override fun onAction(action: TimerAction) {
        screenModelScope.launch {
            when (action) {
                TimerAction.SettingsTap -> {
                    mutableEffects.emit(TimerEffect.OpenSettings)
                }
            }
        }
    }

    fun doSomethingWithLoading() {
        screenModelScope.launch {
            mutableState.update {
                it.copy(time = "0", timeInMillis = 0)
            }
            mutableState.update {
                it.copy(time = "1", timeInMillis = 1)
            }
            mutableState.update {
                it.copy(time = "2", timeInMillis = 2)
            }
        }

    }
}

data class TimerState(
    val time: String,
    val timeInMillis: Long
)

sealed interface TimerAction {
    data object SettingsTap : TimerAction
}

sealed interface TimerEffect {
    data object OpenSettings : TimerEffect
}