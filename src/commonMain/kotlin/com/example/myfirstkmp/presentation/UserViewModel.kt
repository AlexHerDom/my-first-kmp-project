package com.example.myfirstkmp.presentation

import com.example.myfirstkmp.data.User
import com.example.myfirstkmp.data.UserRepositoryImpl
import com.example.myfirstkmp.domain.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Shared ViewModel - KMP Core Concept
 * 
 * MVVM Pattern Refresher:
 * - Model: Data layer (Repository, UseCase)
 * - View: UI layer (Compose, SwiftUI, React)  
 * - ViewModel: Presentation logic (this class)
 * 
 * StateFlow vs LiveData Refresher:
 * - StateFlow: Multiplatform, coroutine-based, always has value
 * - LiveData: Android-only, lifecycle-aware, can be empty
 * - StateFlow.collectAsState() in Compose ≈ LiveData.observeAsState()
 * - StateFlow works on iOS, Web, Desktop; LiveData doesn't
 * 
 * KMP Integration: Cada plataforma adapta este ViewModel:
 * - Android: AndroidUserViewModel wraps this
 * - iOS: IOSUserViewModel observes this  
 * - Web: Redux store delegates to this
 */
class UserViewModel(
    private val userUseCase: UserUseCase = UserUseCase(UserRepositoryImpl())
) {
    
    /**
     * StateFlow Pattern - Reactive State Management
     * 
     * Private MutableStateFlow + Public StateFlow:
     * - Encapsulation: External classes can't modify state directly
     * - Immutability: UI receives read-only StateFlow
     * - Reactivity: UI recomposes automatically when state changes
     * - Thread-safe: StateFlow handles concurrency
     */
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()
    
    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> = _selectedUser.asStateFlow()

    /**
     * Presentation Logic + Corrutinas Integration
     * 
     * Pattern: suspend function calls UseCase, updates StateFlow
     * - ViewModel doesn't know about Android lifecycle
     * - Platform adapters handle coroutine scope (viewModelScope, etc.)
     */
    suspend fun loadUsers() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        
        val result = userUseCase.getValidatedUsers()
        
        result.fold(
            onSuccess = { userList ->
                _users.value = userList
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "✅ ${userList.size} usuarios cargados"
                )
            },
            onFailure = { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Error desconocido"
                )
            }
        )
    }
    
    /**
     * Buscar usuario por ID
     */
    suspend fun findUser(id: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        
        val result = userUseCase.findUserById(id)
        
        result.fold(
            onSuccess = { user ->
                _selectedUser.value = user
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "✅ Usuario encontrado: ${user.name}"
                )
            },
            onFailure = { error ->
                _selectedUser.value = null
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Usuario no encontrado"
                )
            }
        )
    }
    
    /**
     * Crear nuevo usuario
     */
    suspend fun createUser(name: String, email: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        
        val result = userUseCase.createValidatedUser(name, email)
        
        result.fold(
            onSuccess = { newUser ->
                // Actualizar lista local
                _users.value = _users.value + newUser
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "✅ Usuario ${newUser.name} creado exitosamente"
                )
            },
            onFailure = { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Error al crear usuario"
                )
            }
        )
    }
    
    /**
     * Limpiar mensajes
     */
    fun clearMessages() {
        _uiState.value = _uiState.value.copy(message = null, error = null)
    }
}

/**
 * Estado de la UI compartido
 */
data class UserUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val error: String? = null
)

/**
 * Estados simplificados para UI
 * 
 * Sealed Class Benefits:
 * - Type-safe state management
 * - Exhaustive when() statements
 * - Clear state transitions
 * - Better than boolean flags
 */
sealed class UiState {
    data object Loading : UiState()
    data object Success : UiState()
    data class Error(val message: String) : UiState()
}
