package com.example.myfirstkmp

import platform.UIKit.UIDevice

/**
 * iOS-specific implementation
 * 
 * Acceso a APIs nativas de iOS mediante Kotlin/Native interop.
 * platform.UIKit.* es el binding automático de UIKit APIs.
 */
class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

/**
 * actual implementation para iOS
 * 
 * Resuelve el expect/actual pattern para la plataforma iOS.
 * Solo se incluye en builds de iOS.
 */
actual fun getPlatform(): Platform = IOSPlatform()
