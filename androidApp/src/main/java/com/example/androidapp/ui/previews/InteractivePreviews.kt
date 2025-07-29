package com.example.androidapp.ui.previews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidapp.ui.components.*
import com.example.androidapp.ui.theme.MyfirstkmpprojectTheme
import com.example.myfirstkmp.data.User
import com.example.myfirstkmp.presentation.UiState

/**
 * 🎮 INTERACTIVE COMPOSE PREVIEWS
 * 
 * Interactive Preview Features:
 * - Simulate user interactions
 * - Test state changes visually
 * - Validate loading and error states
 * - Preview animations and transitions
 * 
 * KMP Development Benefits:
 * - Test shared state management logic
 * - Validate cross-platform UI behavior
 * - Preview reactive state flows
 * - Test error handling scenarios
 */

// ========================================
// INTERACTIVE STATE PREVIEWS
// ========================================

/**
 * Interactive Loading States Preview
 */
@Preview(
    name = "🔄 Interactive Loading States",
    showBackground = true,
    heightDp = 400
)
@Composable
fun InteractiveLoadingStatesPreview() {
    var currentState: UiState by remember { mutableStateOf(UiState.Loading) }
    val sampleUsers = listOf(
        User(1, "Ana García", "ana@ejemplo.com"),
        User(2, "Carlos López", "carlos@ejemplo.com")
    )
    
    MyfirstkmpprojectTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("🔄 Estados Interactivos", style = MaterialTheme.typography.titleMedium)
            
            // State controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { currentState = UiState.Loading },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentState is UiState.Loading) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("⏳ Loading")
                }
                
                Button(
                    onClick = { currentState = UiState.Success },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentState is UiState.Success) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("✅ Success")
                }
                
                Button(
                    onClick = { currentState = UiState.Error("Error de prueba") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentState is UiState.Error) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("❌ Error")
                }
            }
            
            Divider()
            
            // State display
            val state = currentState // Local variable for smart cast
            when (state) {
                is UiState.Loading -> {
                    LoadingIndicator()
                    Text("Estado: Cargando...", style = MaterialTheme.typography.bodyMedium)
                }
                
                is UiState.Success -> {
                    UserList(
                        users = sampleUsers,
                        onUserClick = { },
                        modifier = Modifier.weight(1f)
                    )
                    Text("Estado: Éxito - ${sampleUsers.size} usuarios", style = MaterialTheme.typography.bodyMedium)
                }
                
                is UiState.Error -> {
                    ErrorMessage(
                        message = state.message, // Use local variable
                        onDismiss = { currentState = UiState.Loading },
                        isError = true
                    )
                    Text("Estado: Error - ${state.message}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

/**
 * Interactive User Selection Preview
 */
@Preview(
    name = "👆 Interactive User Selection",
    showBackground = true,
    heightDp = 600
)
@Composable
fun InteractiveUserSelectionPreview() {
    val users = remember {
        listOf(
            User(1, "Ana García", "ana@ejemplo.com"),
            User(2, "Carlos López", "carlos@ejemplo.com"),
            User(3, "María Rodríguez", "maria@ejemplo.com"),
            User(4, "Juan Pérez", "juan@ejemplo.com")
        )
    }
    
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var clickCount by remember { mutableStateOf(0) }
    
    MyfirstkmpprojectTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("👆 Selección Interactiva", style = MaterialTheme.typography.titleMedium)
            Text("Clicks realizados: $clickCount", style = MaterialTheme.typography.bodySmall)
            
            // User list
            UserList(
                users = users,
                onUserClick = { user ->
                    selectedUser = user
                    clickCount++
                },
                modifier = Modifier.weight(1f)
            )
            
            // Selected user display
            selectedUser?.let { user ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "✨ Usuario Seleccionado:",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("ID: ${user.id}")
                        Text("Nombre: ${user.name}")
                        Text("Email: ${user.email}")
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { 
                                selectedUser = null
                                clickCount++
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("🗑️ Limpiar Selección")
                        }
                    }
                }
            }
        }
    }
}

/**
 * Interactive Message Demo
 */
@Preview(
    name = "💬 Interactive Messages",
    showBackground = true,
    heightDp = 500
)
@Composable
fun InteractiveMessagesPreview() {
    var showSuccessMessage by remember { mutableStateOf(false) }
    var showErrorMessage by remember { mutableStateOf(false) }
    var messageCount by remember { mutableStateOf(0) }
    
    MyfirstkmpprojectTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("💬 Mensajes Interactivos", style = MaterialTheme.typography.titleMedium)
            Text("Mensajes mostrados: $messageCount", style = MaterialTheme.typography.bodySmall)
            
            // Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { 
                        showSuccessMessage = true
                        messageCount++
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("✅ Mostrar Éxito")
                }
                
                Button(
                    onClick = { 
                        showErrorMessage = true
                        messageCount++
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("❌ Mostrar Error")
                }
            }
            
            Button(
                onClick = { 
                    showSuccessMessage = false
                    showErrorMessage = false
                    messageCount = 0
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("🧹 Limpiar Todo")
            }
            
            Divider()
            
            // Messages display
            if (showSuccessMessage) {
                ErrorMessage(
                    message = "¡Operación completada exitosamente! Mensaje #$messageCount",
                    onDismiss = { showSuccessMessage = false },
                    isError = false
                )
            }
            
            if (showErrorMessage) {
                ErrorMessage(
                    message = "Error en la operación. Código: ERR_$messageCount",
                    onDismiss = { showErrorMessage = false },
                    isError = true
                )
            }
            
            // Loading simulation
            if (showSuccessMessage || showErrorMessage) {
                LoadingIndicator()
                Text("Simulando procesamiento...", style = MaterialTheme.typography.bodySmall)
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Instructions
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("📋 Instrucciones:", style = MaterialTheme.typography.labelMedium)
                    Text("• Toca los botones para mostrar mensajes", style = MaterialTheme.typography.bodySmall)
                    Text("• Los mensajes se pueden cerrar individualmente", style = MaterialTheme.typography.bodySmall)
                    Text("• El contador muestra la actividad", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

/**
 * Interactive Dynamic Content Preview
 */
@Preview(
    name = "🎲 Dynamic Content Generator",
    showBackground = true,
    heightDp = 600
)
@Composable
fun DynamicContentPreview() {
    var users by remember { mutableStateOf(emptyList<User>()) }
    var isLoading by remember { mutableStateOf(false) }
    var generationCount by remember { mutableStateOf(0) }
    
    val sampleNames = listOf("Ana", "Carlos", "María", "Juan", "Laura", "Pedro", "Carmen", "Luis")
    val sampleDomains = listOf("ejemplo.com", "test.com", "demo.com", "muestra.com")
    
    fun generateRandomUsers(count: Int): List<User> {
        return (1..count).map { id ->
            val name = sampleNames.random()
            val domain = sampleDomains.random()
            User(
                id = id + (generationCount * 100),
                name = "$name García $id",
                email = "${name.lowercase()}$id@$domain"
            )
        }
    }
    
    MyfirstkmpprojectTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("🎲 Generador Dinámico", style = MaterialTheme.typography.titleMedium)
            Text("Generación #$generationCount - ${users.size} usuarios", style = MaterialTheme.typography.bodySmall)
            
            // Generation controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { 
                        isLoading = true
                        // Simulate loading delay
                        users = generateRandomUsers(3)
                        generationCount++
                        isLoading = false
                    },
                    enabled = !isLoading
                ) {
                    Text("🔄 3 Usuarios")
                }
                
                Button(
                    onClick = { 
                        isLoading = true
                        users = generateRandomUsers(6)
                        generationCount++
                        isLoading = false
                    },
                    enabled = !isLoading
                ) {
                    Text("📈 6 Usuarios")
                }
                
                Button(
                    onClick = { 
                        users = emptyList()
                        generationCount = 0
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("🗑️ Limpiar")
                }
            }
            
            Divider()
            
            // Content display
            when {
                isLoading -> {
                    LoadingIndicator()
                    Text("Generando usuarios...", style = MaterialTheme.typography.bodyMedium)
                }
                
                users.isEmpty() -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                        ) {
                            Text("📋 No hay usuarios", style = MaterialTheme.typography.titleMedium)
                            Text("Genera algunos usuarios con los botones de arriba", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                
                else -> {
                    UserList(
                        users = users,
                        onUserClick = { user ->
                            // Simulate user interaction feedback
                            users = users.map { 
                                if (it.id == user.id) {
                                    it.copy(name = "✨ ${it.name}")
                                } else {
                                    it
                                }
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
