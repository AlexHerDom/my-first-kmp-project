package com.example.myfirstkmp

/**
 * KMP Pattern: expect/actual
 * 
 * Esta interfaz define el contrato común, pero cada plataforma 
 * tendrá su propia implementación con datos específicos.
 */
interface Platform {
    val name: String
}

/**
 * expect function - KMP core concept
 * 
 * Declara que esperamos una implementación de esta función en cada plataforma.
 * El compilador de KMP se encarga de resolver cuál implementación usar.
 * 
 * Android → Platform.android.kt (actual fun)
 * iOS → Platform.ios.kt (actual fun)
 */
expect fun getPlatform(): Platform
