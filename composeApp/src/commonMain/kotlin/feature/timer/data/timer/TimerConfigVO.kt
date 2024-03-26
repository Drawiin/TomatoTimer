package feature.timer.data.timer

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class TimerConfigVO(
    val pomodoroDuration: Duration = 25.minutes,
    val breakDuration: Duration = 5.minutes,
    val longBreakDuration: Duration = 30.minutes,
    val longBreakAfter: Int = 4,
    val autoStartBreaks: Boolean = false,
    val autoStartNextPomodoro: Boolean = false
)