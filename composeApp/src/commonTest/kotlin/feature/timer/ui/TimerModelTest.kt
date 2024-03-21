package feature.timer.ui

import core.test.runInteractionTest
import feature.timer.data.timer.TimerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TimerModelTest {
    private lateinit var model: TimerModel
    private val testDispatcher = StandardTestDispatcher()


    @BeforeTest
    fun setup() {
        model = TimerModel(TimerRepository(120))
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should start at initial state`() = runTest {
        assertEquals("00:00", model.state.value.time)
        assertEquals(0, model.state.value.timeInMillis)
    }

    @Test
    fun `should open settings when user taps settings`() = runInteractionTest(model) {
        model.onAction(TimerAction.SettingsTap)

        testScope.advanceUntilIdle()

        assertEquals(TimerEffect.OpenSettings, effects.last())
    }
}

