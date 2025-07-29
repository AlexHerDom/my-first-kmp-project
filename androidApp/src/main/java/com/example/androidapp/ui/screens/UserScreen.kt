package com.example.androidapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapp.presentation.AndroidUserViewModel
import com.example.androidapp.ui.components.*
import com.example.androidapp.ui.theme.MyfirstkmpprojectTheme
import com.example.myfirstkmp.data.User
import com.example.myfirstkmp.presentation.UserUiState

/**
 * Compose Fundamentals Refresher + MVVM Integration
 * 
 * @Composable Function:
 * - Declarative UI: Describe what UI should look like
 * - Recomposition: Automatically updates when state changes
 * - No findViewById, no XML layouts
 * - Functions that emit UI elements
 * 
 * State Management in Compose:
 * - remember { }: Survives recomposition, dies on configuration change
 * - collectAsStateWithLifecycle(): StateFlow → State, lifecycle-aware
 * - by keyword: Property delegation for automatic recomposition trigger
 * 
 * MVVM in Compose:
 * - Screen observes ViewModel states via StateFlow
 * - User interactions call ViewModel methods
 * - ViewModel updates StateFlow, Compose recomposes automatically
 * - No manual view.setText() or adapter.notifyDataSetChanged()
 */
@Composable
fun UserScreen(
    viewModel: AndroidUserViewModel = viewModel(), // DI integration point
    modifier: Modifier = Modifier
) {
    /**
     * State Collection - Compose + StateFlow Integration
     * 
     * collectAsStateWithLifecycle() pattern:
     * - Converts StateFlow<T> to State<T>
     * - Lifecycle-aware: stops collection when UI not visible
     * - Automatic recomposition: UI updates when StateFlow emits
     * - Memory efficient: no leaks from background collection
     */
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val users by viewModel.users.collectAsStateWithLifecycle()
    val selectedUser by viewModel.selectedUser.collectAsStateWithLifecycle()
    
    /**
     * Side Effects in Compose - LaunchedEffect
     * 
     * LaunchedEffect(Unit):
     * - Runs once when Composable enters composition
     * - Perfect for initial data loading
     * - Cancels coroutine if Composable leaves composition
     * - Unit key = run only once (no re-trigger)
     */
    LaunchedEffect(Unit) {
        viewModel.loadUsers() // Trigger initial load
    }
    
    /**
     * Compose Layout - Declarative UI Structure
     * 
     * Column: Vertical arrangement (like LinearLayout vertical)
     * Row: Horizontal arrangement (like LinearLayout horizontal)
     * Modifier: Chainable styling (like view attributes)
     * Arrangement.spacedBy(): Even spacing between children
     */
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "👥 Gestión de Usuarios",
            style = MaterialTheme.typography.headlineMedium
        )
        
        // Botones de acción
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.loadUsers() },
                modifier = Modifier.weight(1f)
            ) {
                Text("🔄 Recargar")
            }
            
            Button(
                onClick = { viewModel.findUser(1) },
                modifier = Modifier.weight(1f)
            ) {
                Text("🔍 Buscar ID:1")
            }
        }
        
        // Mensaje de estado
        ErrorMessage(
            message = uiState.message,
            isError = false,
            onDismiss = viewModel::clearMessages
        )
        
        // Mensaje de error
        ErrorMessage(
            message = uiState.error,
            isError = true,
            onDismiss = viewModel::clearMessages
        )
        
        // Loading o contenido
        if (uiState.isLoading) {
            LoadingIndicator()
        } else {
            // Lista de usuarios
            UserList(
                users = users,
                onUserClick = { user ->
                    viewModel.findUser(user.id)
                }
            )
        }
        
        // Usuario seleccionado
        selectedUser?.let { user ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "👤 Usuario Seleccionado:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Nombre: ${user.name}")
                    Text("Email: ${user.email}")
                    Text("ID: ${user.id}")
                }
            }
        }
    }
}

// ========================================
// 🎨 COMPOSE PREVIEWS
// ========================================

/**
 * Preview Parameter Provider - Data for Previews
 * 
 * Compose Preview Best Practices:
 * - Use @PreviewParameter for dynamic data
 * - Test different states (empty, loading, success, error)
 * - Multiple device configurations
 * - Light/Dark theme variants
 */
class UserScreenPreviewParameterProvider : PreviewParameterProvider<Pair<UserUiState, List<User>>> {
    override val values: Sequence<Pair<UserUiState, List<User>>> = sequenceOf(
        // Loading state
        Pair(UserUiState(isLoading = true), emptyList()),
        
        // Success state with users
        Pair(
            UserUiState(message = "✅ Usuarios cargados exitosamente"), 
            listOf(
                User(1, "Ana García", "ana@ejemplo.com"),
                User(2, "Carlos López", "carlos@ejemplo.com"),
                User(3, "María Rodríguez", "maria@ejemplo.com")
            )
        ),
        
        // Empty state
        Pair(UserUiState(), emptyList()),
        
        // Error state
        Pair(UserUiState(error = "Error de conexión"), emptyList())
    )
}

/**
 * Preview: Default Light Theme
 * 
 * @Preview annotation:
 * - Renders composable in Android Studio Design panel
 * - Interactive preview for rapid UI development
 * - No need to run app to see changes
 */
@Preview(
    name = "UserScreen - Light Theme",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun UserScreenPreview(
    @PreviewParameter(UserScreenPreviewParameterProvider::class) 
    stateAndUsers: Pair<UserUiState, List<User>>
) {
    MyfirstkmpprojectTheme {
        // Create mock content based on preview parameter
        UserScreenContent(
            uiState = stateAndUsers.first,
            users = stateAndUsers.second,
            selectedUser = stateAndUsers.second.firstOrNull(),
            onLoadUsers = { },
            onUserClick = { },
            onRefresh = { }
        )
    }
}

/**
 * Preview: Dark Theme
 */
@Preview(
    name = "UserScreen - Dark Theme",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun UserScreenDarkPreview() {
    val sampleUsers = listOf(
        User(1, "Ana García", "ana@ejemplo.com"),
        User(2, "Carlos López", "carlos@ejemplo.com")
    )
    
    MyfirstkmpprojectTheme {
        UserScreenContent(
            uiState = UserUiState(message = "✅ Usuarios cargados"),
            users = sampleUsers,
            selectedUser = sampleUsers.first(),
            onLoadUsers = { },
            onUserClick = { },
            onRefresh = { }
        )
    }
}

/**
 * Preview: Different Screen Sizes
 */
@Preview(
    name = "UserScreen - Tablet",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
fun UserScreenTabletPreview() {
    val sampleUsers = listOf(
        User(1, "Ana García", "ana@ejemplo.com"),
        User(2, "Carlos López", "carlos@ejemplo.com"),
        User(3, "María Rodríguez", "maria@ejemplo.com"),
        User(4, "Juan Pérez", "juan@ejemplo.com")
    )
    
    MyfirstkmpprojectTheme {
        UserScreenContent(
            uiState = UserUiState(),
            users = sampleUsers,
            selectedUser = null,
            onLoadUsers = { },
            onUserClick = { },
            onRefresh = { }
        )
    }
}

/**
 * Extracted Content Composable for Previews
 * 
 * Preview Best Practice:
 * - Extract UI logic to separate composable
 * - Keep stateful composable for real usage
 * - Use stateless version for previews
 * - Easier to test different states
 */
@Composable
private fun UserScreenContent(
    uiState: UserUiState,
    users: List<User>,
    selectedUser: User?,
    onLoadUsers: () -> Unit,
    onUserClick: (User) -> Unit,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "🚀 Mi Primer App KMP",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Button(
            onClick = onLoadUsers,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🔄 Cargar Usuarios")
        }
        
        // State-based content
        when {
            uiState.isLoading -> {
                LoadingIndicator()
            }
            
            uiState.error != null -> {
                ErrorMessage(
                    message = uiState.error,
                    onDismiss = onRefresh,
                    isError = true
                )
            }
            
            else -> {
                UserList(
                    users = users,
                    onUserClick = onUserClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        // Success message
        if (uiState.message != null) {
            ErrorMessage(
                message = uiState.message,
                onDismiss = { },
                isError = false
            )
        }
        
        // Selected user info
        selectedUser?.let { user ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "👤 Usuario Seleccionado:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Nombre: ${user.name}")
                    Text("Email: ${user.email}")
                    Text("ID: ${user.id}")
                }
            }
        }
    }
}
