package feature.timer.di

import feature.timer.data.timer.TimerRepository
import feature.timer.ui.TimerModel
import org.koin.dsl.module

val timerModule = module {
    single { TimerRepository(120) }
    factory { TimerModel(get()) }
}