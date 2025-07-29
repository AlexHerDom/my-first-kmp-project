package com.example.androidapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstkmp.presentation.UserViewModel as SharedUserViewModel
import com.example.myfirstkmp.presentation.UserUiState
import com.example.myfirstkmp.data.User
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Android ViewModel Adapter - KMP Integration Pattern
 * 
 * MVVM Pattern Refresher:
 * - This class IS the ViewModel in Android MVVM architecture
 * - Survives configuration changes (rotation, etc.)
 * - Provides data to UI (Compose/Views) via StateFlow/LiveData
 * - Handles UI logic, delegates business logic to UseCase/Repository
 * - Dies when Activity/Fragment is destroyed permanently
 * 
 * Android Lifecycle Integration:
 * - extends ViewModel: Android lifecycle management
 * - viewModelScope: CoroutineScope tied to ViewModel lifecycle
 * - Automatically cancelled when ViewModel is cleared
 * 
 * KMP Integration:
 * - Composition over inheritance: usa el ViewModel compartido
 * - Business logic in commonMain, UI adaptation here
 * - Same data, different UI behavior per platform
 */
class AndroidUserViewModel : ViewModel() {
    
    /**
     * Adapter Pattern: Wraps shared ViewModel
     * - sharedViewModel contains the actual business logic
     * - This class only handles Android-specific concerns (lifecycle)
     * - UI gets the same data as iOS/Web, but through Android patterns
     */
    private val sharedViewModel = SharedUserViewModel()
    
    /**
     * StateFlow Exposure - Compose Integration
     * 
     * Pattern: Expose StateFlow directly to Compose
     * - Compose calls collectAsStateWithLifecycle()
     * - Automatic recomposition when state changes
     * - No need for LiveData.observe() boilerplate
     * - Lifecycle-aware collection in Compose
     */
    val uiState: StateFlow<UserUiState> = sharedViewModel.uiState
    val users: StateFlow<List<User>> = sharedViewModel.users
    val selectedUser: StateFlow<User?> = sharedViewModel.selectedUser
    
    /**
     * Android-specific: viewModelScope + Corrutinas
     * 
     * Why viewModelScope:
     * - Tied to ViewModel lifecycle (auto-cancelled on clear)
     * - Main-safe: launches on Main dispatcher by default
     * - Exception handling: crashes are contained
     * - Memory leak prevention: cancels when ViewModel dies
     */
    fun loadUsers() {
        viewModelScope.launch {
            sharedViewModel.loadUsers() // Delegate to shared logic
        }
    }
    
    /**
     * Buscar usuario por ID
     */
    fun findUser(id: Int) {
        viewModelScope.launch {
            sharedViewModel.findUser(id)
        }
    }
    
    /**
     * Crear nuevo usuario
     */
    fun createUser(name: String, email: String) {
        viewModelScope.launch {
            sharedViewModel.createUser(name, email)
        }
    }
    
    /**
     * Limpiar mensajes
     */
    fun clearMessages() = sharedViewModel.clearMessages()
}
