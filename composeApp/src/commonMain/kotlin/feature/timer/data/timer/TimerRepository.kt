package feature.timer.data.timer

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TimerRepository(
    private val config: Int,
) {
    private val timer = MutableStateFlow(0)
    fun subscribe(): Flow<Int> = timer
    suspend fun start() {
        repeat(config) {
            delay(1000)
            timer.update { it + 1 }
        }
    }
}