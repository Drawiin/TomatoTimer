package di

import platform.UIKit.UIDevice

actual fun platformProviderFactory(): PlatformProvider {
    return object : PlatformProvider {
        override fun platform(): String {
            return UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
        }
    }
}