package com.example.myfirstkmp

import platform.UIKit.UIDevice

/**
 * Implementación específica para iOS
 * Aquí podemos acceder a APIs de iOS
 */
class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

/**
 * Función actual - implementación real para iOS
 */
actual fun getPlatform(): Platform = IOSPlatform()
