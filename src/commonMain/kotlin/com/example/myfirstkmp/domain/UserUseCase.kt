package com.example.myfirstkmp.domain

import com.example.myfirstkmp.data.ApiResponse
import com.example.myfirstkmp.data.User
import com.example.myfirstkmp.data.UserRepository

/**
 * Domain Layer - Business Logic
 * 
 * KMP Best Practice: Toda la lógica de negocio va aquí.
 * 
 * Corrutinas Refresher:
 * - suspend fun: Can be paused/resumed (non-blocking)
 * - Result<T>: Kotlin's alternative to try-catch for functional error handling
 * - suspend functions must be called from coroutine scope or other suspend fun
 * - Cross-platform: Works on Android, iOS, Web, Desktop
 */
class UserUseCase(
    private val repository: UserRepository
) {
    
    /**
     * suspend function - Corrutinas Fundamental
     * 
     * - Executes asynchronously without blocking threads
     * - Can call other suspend functions (repository.getUsers())
     * - Returns Result<T> for functional error handling
     * - Same behavior on all platforms (Android, iOS, etc.)
     */
    suspend fun getValidatedUsers(): Result<List<User>> {
        return try {
            val response = repository.getUsers()
            
            if (response.success && response.data != null) {
                // Aplicar lógica de negocio
                val validUsers = response.data.filter { user ->
                    user.name.isNotBlank() && 
                    user.email.contains("@")
                }
                
                Result.success(validUsers)
            } else {
                Result.failure(Exception(response.message ?: "Error desconocido"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Buscar usuario por ID con validación
     */
    suspend fun findUserById(id: Int): Result<User> {
        return try {
            if (id <= 0) {
                return Result.failure(Exception("ID debe ser mayor a 0"))
            }
            
            val response = repository.getUserById(id)
            
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Usuario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Crear usuario con validaciones
     */
    suspend fun createValidatedUser(name: String, email: String): Result<User> {
        return try {
            // Validaciones de negocio
            when {
                name.isBlank() -> Result.failure(Exception("El nombre es obligatorio"))
                email.isBlank() -> Result.failure(Exception("El email es obligatorio"))
                !email.contains("@") -> Result.failure(Exception("Email inválido"))
                else -> {
                    val user = User(0, name.trim(), email.trim())
                    val response = repository.createUser(user)
                    
                    if (response.success && response.data != null) {
                        Result.success(response.data)
                    } else {
                        Result.failure(Exception(response.message ?: "Error al crear usuario"))
                    }
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
