package com.example.desktop

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.myfirstkmp.data.User
import com.example.myfirstkmp.presentation.UserViewModel
import com.example.myfirstkmp.presentation.UserUiState
import com.example.myfirstkmp.data.UserRepositoryImpl
import com.example.myfirstkmp.domain.UserUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel Adapter para Desktop (similar al de Android)
 */
class DesktopUserViewModel {
    // Mismo ViewModel compartido que en Android/iOS/Web
    private val sharedViewModel = UserViewModel(UserUseCase(UserRepositoryImpl()))
    
    val uiState: StateFlow<UserUiState> = sharedViewModel.uiState
    val users: StateFlow<List<User>> = sharedViewModel.users
    val selectedUser: StateFlow<User?> = sharedViewModel.selectedUser
    
    suspend fun loadUsers() = sharedViewModel.loadUsers()
    suspend fun findUser(id: Int) = sharedViewModel.findUser(id)
    suspend fun createUser(name: String, email: String) = sharedViewModel.createUser(name, email)
    fun clearMessages() = sharedViewModel.clearMessages()
}

/**
 * Pantalla principal para Desktop
 */
@Composable
fun DesktopUserScreen(
    viewModel: DesktopUserViewModel = remember { DesktopUserViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val users by viewModel.users.collectAsState()
    val selectedUser by viewModel.selectedUser.collectAsState()
    val scope = rememberCoroutineScope()
    
    // Cargar usuarios al iniciar
    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }
    
    Row(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Panel izquierdo - Lista de usuarios
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "👥 Gestión de Usuarios - Desktop",
                style = MaterialTheme.typography.headlineMedium
            )
            
            // Botones de acción
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { 
                        scope.launch { viewModel.loadUsers() }
                    }
                ) {
                    Text("🔄 Recargar")
                }
                
                Button(
                    onClick = { 
                        scope.launch { viewModel.findUser(1) }
                    }
                ) {
                    Text("🔍 Buscar ID:1")
                }
            }
            
            // Mensajes
            uiState.message?.let { message ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(message)
                        TextButton(onClick = viewModel::clearMessages) {
                            Text("OK")
                        }
                    }
                }
            }
            
            uiState.error?.let { error ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(error)
                        TextButton(onClick = viewModel::clearMessages) {
                            Text("OK")
                        }
                    }
                }
            }
            
            // Lista de usuarios
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(users) { user ->
                        Card(
                            onClick = { 
                                scope.launch { viewModel.findUser(user.id) }
                            }
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = user.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = user.email,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "ID: ${user.id}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
        
        // Panel derecho - Usuario seleccionado
        selectedUser?.let { user ->
            Card(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "👤 Usuario Seleccionado",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    
                    Text("Nombre: ${user.name}")
                    Text("Email: ${user.email}")
                    Text("ID: ${user.id}")
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { 
                            scope.launch { 
                                viewModel.createUser("Usuario Nuevo", "nuevo@email.com")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("➕ Crear Usuario Nuevo")
                    }
                }
            }
        }
    }
}

/**
 * Función main para Desktop
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP Desktop App"
    ) {
        MaterialTheme {
            DesktopUserScreen()
        }
    }
}

@Preview
@Composable
fun DesktopUserScreenPreview() {
    MaterialTheme {
        DesktopUserScreen()
    }
}
