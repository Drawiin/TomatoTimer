package feature.timer.data.timer

class TimerRepository {
    suspend fun getTimerConfig(): TimerConfigVO {
        return TimerConfigVO()
    }
}