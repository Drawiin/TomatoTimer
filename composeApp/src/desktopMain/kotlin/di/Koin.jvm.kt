package di

actual fun platformProviderFactory(): PlatformProvider {
    return object : PlatformProvider {
        override fun platform(): String {
            return "Desktop v${System.getProperty("java.version")}"
        }
    }
}