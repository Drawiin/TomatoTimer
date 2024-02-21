package di

import android.os.Build

actual fun platformProviderFactory(): PlatformProvider {
    return object : PlatformProvider {
        override fun platform(): String {
            return "Android ${Build.VERSION.SDK_INT}"
        }
    }
}