package com.example.androidapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidapp.ui.theme.MyfirstkmpprojectTheme
import com.example.myfirstkmp.data.User

/**
 * Reusable Composables - Component Architecture
 * 
 * Compose Best Practice: Break UI into small, reusable functions
 * - Single responsibility: Each Composable does one thing
 * - Testable: Can be tested in isolation
 * - Reusable: Can be used in different screens
 * - Stateless: Receives data via parameters, emits events via lambdas
 * 
 * LazyColumn Refresher:
 * - RecyclerView equivalent in Compose
 * - Lazy: Only renders visible items (performance)
 * - items(list) { item -> }: Iterates over collection
 * - Built-in scroll behavior, no ScrollView needed
 */
@Composable
fun UserList(
    users: List<User>,
    onUserClick: (User) -> Unit, // Event emission pattern
    modifier: Modifier = Modifier
) {
    if (users.isEmpty()) {
        /**
         * Box + Alignment - Centering Content
         * 
         * Box: FrameLayout equivalent, stacks children
         * contentAlignment: Centers content within Box
         * Better than Column with weight + arrangement
         */
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay usuarios para mostrar",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) { user ->
                UserCard(
                    user = user,
                    onClick = { onUserClick(user) } // Event bubbling up
                )
            }
        }
    }
}

/**
 * Material 3 Card - Modern Design Component
 * 
 * Card vs Surface:
 * - Card: Pre-styled with elevation, corners, click handling
 * - Surface: Lower-level, more customizable
 * - onClick: Built-in click ripple effect
 * - CardDefaults.cardElevation(): Material 3 elevation system
 */
@Composable
fun UserCard(
    user: User,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        /**
         * Column Layout + Spacing
         * 
         * Column: Vertical LinearLayout equivalent
         * Modifier.padding(): Internal spacing (like android:padding)
         * Spacer: Empty space between elements (better than margins)
         */
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "ID: ${user.id}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Loading indicator reutilizable
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Cargando...",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * Mensaje de estado (éxito o error)
 */
@Composable
fun ErrorMessage(
    message: String?,
    isError: Boolean = false,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (message != null) {
        Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (isError) 
                    MaterialTheme.colorScheme.errorContainer 
                else 
                    MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isError) 
                        MaterialTheme.colorScheme.onErrorContainer 
                    else 
                        MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            }
        }
    }
}

// ========================================
// 🎨 COMPOSE PREVIEWS - COMPONENTS
// ========================================

/**
 * Previews for Individual Components
 * 
 * Component Preview Benefits:
 * - Fast iteration on individual components
 * - Test edge cases (empty lists, long text, etc.)
 * - Visual regression testing
 * - Design system validation
 */

// Sample data for previews
private val sampleUsers = listOf(
    User(1, "Ana García López", "ana.garcia.lopez@ejemplo.com"),
    User(2, "Carlos Miguel Rodríguez", "carlos@ejemplo.com"),
    User(3, "María Fernanda Jiménez", "maria.fernanda@ejemplo.com"),
    User(4, "Juan Pablo Martínez", "juan.pablo@ejemplo.com")
)

/**
 * Preview: UserList with multiple users
 */
@Preview(
    name = "UserList - Multiple Users",
    showBackground = true
)
@Composable
fun UserListPreview() {
    MyfirstkmpprojectTheme {
        UserList(
            users = sampleUsers,
            onUserClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Preview: UserList empty state
 */
@Preview(
    name = "UserList - Empty State",
    showBackground = true
)
@Composable
fun UserListEmptyPreview() {
    MyfirstkmpprojectTheme {
        UserList(
            users = emptyList(),
            onUserClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Preview: UserList dark theme
 */
@Preview(
    name = "UserList - Dark Theme",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun UserListDarkPreview() {
    MyfirstkmpprojectTheme {
        UserList(
            users = sampleUsers,
            onUserClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Preview: Single UserCard
 */
@Preview(
    name = "UserCard - Normal",
    showBackground = true
)
@Composable
fun UserCardPreview() {
    MyfirstkmpprojectTheme {
        UserCard(
            user = User(1, "Ana García López", "ana.garcia.lopez@ejemplo.com"),
            onClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Preview: UserCard with long text
 */
@Preview(
    name = "UserCard - Long Text",
    showBackground = true,
    widthDp = 300
)
@Composable
fun UserCardLongTextPreview() {
    MyfirstkmpprojectTheme {
        UserCard(
            user = User(
                999, 
                "María Fernanda Jiménez González de la Torre", 
                "maria.fernanda.jimenez.gonzalez.delatorre@ejemplo.com"
            ),
            onClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Preview: UserCard dark theme
 */
@Preview(
    name = "UserCard - Dark Theme",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun UserCardDarkPreview() {
    MyfirstkmpprojectTheme {
        UserCard(
            user = User(1, "Ana García", "ana@ejemplo.com"),
            onClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Preview: LoadingIndicator
 */
@Preview(
    name = "LoadingIndicator",
    showBackground = true
)
@Composable
fun LoadingIndicatorPreview() {
    MyfirstkmpprojectTheme {
        LoadingIndicator(
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Preview: ErrorMessage
 */
@Preview(
    name = "ErrorMessage - Error",
    showBackground = true
)
@Composable
fun ErrorMessagePreview() {
    MyfirstkmpprojectTheme {
        ErrorMessage(
            message = "Error de conexión a internet. Verifica tu conexión y vuelve a intentar.",
            onDismiss = { },
            isError = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Preview: Success Message
 */
@Preview(
    name = "ErrorMessage - Success",
    showBackground = true
)
@Composable
fun SuccessMessagePreview() {
    MyfirstkmpprojectTheme {
        ErrorMessage(
            message = "¡Usuarios cargados exitosamente!",
            onDismiss = { },
            isError = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Preview: Message states comparison
 */
@Preview(
    name = "Message States",
    showBackground = true,
    heightDp = 400
)
@Composable
fun MessageStatesPreview() {
    MyfirstkmpprojectTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Comparación de Estados:", style = MaterialTheme.typography.titleMedium)
            
            ErrorMessage(
                message = "Error de conexión",
                onDismiss = { },
                isError = true
            )
            
            ErrorMessage(
                message = "Operación exitosa",
                onDismiss = { },
                isError = false
            )
            
            LoadingIndicator()
        }
    }
}
