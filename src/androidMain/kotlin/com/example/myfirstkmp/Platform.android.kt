package com.example.myfirstkmp

import android.os.Build

/**
 * Android-specific implementation
 * 
 * Aquí puedes acceder a cualquier API de Android (Build, Context, etc.)
 * Esta clase solo existe cuando compilas para Android.
 */
class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

/**
 * actual implementation para Android
 * 
 * Esta función resuelve el "expect" declarado en commonMain.
 * Solo se incluye en builds de Android.
 */
actual fun getPlatform(): Platform = AndroidPlatform()
