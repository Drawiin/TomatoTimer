package di

import org.koin.dsl.module

interface PlatformProvider {
    fun platform(): String
}

val appModule = module {
    single<PlatformProvider> { platformProviderFactory() }
}

expect fun platformProviderFactory(): PlatformProvider
