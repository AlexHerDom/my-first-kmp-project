package com.example.myfirstkmp

/**
 * Interfaz que define qué información específica 
 * necesitamos de cada plataforma
 */
interface Platform {
    val name: String
}

/**
 * Función expect - debe ser implementada por cada plataforma
 * Android e iOS tendrán su propia versión
 */
expect fun getPlatform(): Platform
