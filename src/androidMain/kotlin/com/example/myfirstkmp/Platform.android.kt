package com.example.myfirstkmp

import android.os.Build

/**
 * Implementación específica para Android
 * Aquí podemos acceder a APIs de Android
 */
class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

/**
 * Función actual - implementación real para Android
 */
actual fun getPlatform(): Platform = AndroidPlatform()
