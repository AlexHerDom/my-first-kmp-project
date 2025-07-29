package com.example.myfirstkmp.data

/**
 * Shared Data Models - KMP Best Practice
 * 
 * data class - Kotlin Feature Refresher:
 * - Auto-generates: equals(), hashCode(), toString(), copy()
 * - Immutable by default (val properties)
 * - Perfect for DTOs, API responses, UI state
 * - Structural equality (== compares content, not reference)
 * 
 * KMP: Estas clases se compilan para todas las plataformas.
 */
data class User(
    val id: Int,
    val name: String,
    val email: String
) // Compiler generates: copy(), componentN(), equals(), hashCode(), toString()

/**
 * Generic data class - Type safety across platforms
 */
data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,         // Nullable generic type
    val message: String?
)

/**
 * Repository Interface - KMP Architecture Pattern
 * 
 * Define el contrato de datos. Permite diferentes implementaciones:
 * - Local: Room (Android), Core Data (iOS), SQLite
 * - Remote: Ktor HTTP client (todas las plataformas)
 * - Mock: Para testing
 */
interface UserRepository {
    suspend fun getUsers(): ApiResponse<List<User>>
    suspend fun getUserById(id: Int): ApiResponse<User>
    suspend fun createUser(user: User): ApiResponse<User>
}

/**
 * Concrete Repository Implementation
 * 
 * Esta implementación usa datos mock, pero en producción usarías:
 * - Ktor client para HTTP requests
 * - SharedPreferences/UserDefaults para caché
 * - Database para persistencia local
 */
class UserRepositoryImpl : UserRepository {
    
    override suspend fun getUsers(): ApiResponse<List<User>> {
        return try {
            // Aquí iría tu llamada HTTP real
            // Usando Ktor, Retrofit, etc.
            
            // Por ahora, datos de ejemplo:
            val users = listOf(
                User(1, "Ana García", "ana@email.com"),
                User(2, "Carlos López", "carlos@email.com"),
                User(3, "María Rodríguez", "maria@email.com")
            )
            
            ApiResponse(
                success = true,
                data = users,
                message = null
            )
        } catch (e: Exception) {
            ApiResponse(
                success = false,
                data = null,
                message = "Error: ${e.message}"
            )
        }
    }
    
    override suspend fun getUserById(id: Int): ApiResponse<User> {
        return try {
            // Simulamos una llamada HTTP
            val user = User(id, "Usuario $id", "user$id@email.com")
            
            ApiResponse(
                success = true,
                data = user,
                message = null
            )
        } catch (e: Exception) {
            ApiResponse(
                success = false,
                data = null,
                message = "Error: ${e.message}"
            )
        }
    }
    
    override suspend fun createUser(user: User): ApiResponse<User> {
        return try {
            // Aquí harías POST a tu API
            
            ApiResponse(
                success = true,
                data = user.copy(id = 999), // ID asignado por el servidor
                message = "Usuario creado exitosamente"
            )
        } catch (e: Exception) {
            ApiResponse(
                success = false,
                data = null,
                message = "Error: ${e.message}"
            )
        }
    }
}
