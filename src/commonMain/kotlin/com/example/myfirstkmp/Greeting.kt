package com.example.myfirstkmp

/**
 * Esta clase contiene la lógica de negocio compartida
 * Se puede usar tanto en Android como en iOS
 */
class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "¡Hola desde ${platform.name}! 🎉"
    }

    fun greetWithName(name: String): String {
        return "¡Hola $name desde ${platform.name}! 👋"
    }

    fun getRandomMessage(): String {
        val messages = listOf(
            "¡Kotlin Multiplatform es genial!",
            "¡Código compartido funcionando!",
            "¡Una vez escrito, funciona en todas partes!",
            "¡Bienvenido a KMP!"
        )
        return messages.random()
    }
}
