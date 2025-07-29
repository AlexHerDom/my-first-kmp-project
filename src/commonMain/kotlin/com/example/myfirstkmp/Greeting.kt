package com.example.myfirstkmp

import com.example.myfirstkmp.data.UserRepositoryImpl
import com.example.myfirstkmp.domain.UserUseCase

/**
 * Demo Class - KMP Fundamentals
 * 
 * Esta clase demuestra conceptos básicos de KMP:
 * - expect/actual pattern (getPlatform)
 * - Shared business logic (UserUseCase integration)  
 * - Cross-platform execution
 * 
 * Extension: Ahora también integra servicios de datos.
 */
class Greeting {
    private val platform: Platform = getPlatform() // expect/actual resolution
    
    // Integration example: Using shared business logic
    private val userUseCase = UserUseCase(UserRepositoryImpl())

    // Basic platform detection demo
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
    
    // Service integration demo - shows how to use shared business logic
    suspend fun getUsersMessage(): String {
        return try {
            val result = userUseCase.getValidatedUsers()
            
            result.fold(
                onSuccess = { users ->
                    "📋 Usuarios cargados: ${users.size}\n" +
                    users.take(3).joinToString("\n") { "• ${it.name}" }
                },
                onFailure = { error ->
                    "❌ Error: ${error.message}"
                }
            )
        } catch (e: Exception) {
            "❌ Error inesperado: ${e.message}"
        }
    }
    
    suspend fun findUser(id: Int): String {
        return try {
            val result = userUseCase.findUserById(id)
            
            result.fold(
                onSuccess = { user ->
                    "👤 Usuario encontrado:\n" +
                    "• Nombre: ${user.name}\n" +
                    "• Email: ${user.email}"
                },
                onFailure = { error ->
                    "❌ ${error.message}"
                }
            )
        } catch (e: Exception) {
            "❌ Error: ${e.message}"
        }
    }
}
