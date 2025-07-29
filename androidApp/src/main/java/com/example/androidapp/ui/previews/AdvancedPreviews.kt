package com.example.androidapp.ui.previews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.androidapp.ui.components.*
import com.example.androidapp.ui.theme.MyfirstkmpprojectTheme
import com.example.myfirstkmp.data.User
import com.example.myfirstkmp.presentation.UiState

/**
 * 🎨 ADVANCED COMPOSE PREVIEWS
 * 
 * Preview Best Practices Showcase:
 * - Multi-device previews
 * - Accessibility testing
 * - Dynamic color schemes
 * - Responsive design validation
 * - Performance preview patterns
 * 
 * Benefits for KMP Development:
 * - Test shared UI components visually
 * - Validate design system consistency
 * - Quick iteration without running on device
 * - Accessibility and responsive design validation
 */

// ========================================
// PREVIEW PARAMETER PROVIDERS
// ========================================

/**
 * User Data Provider for Dynamic Previews
 */
class UserPreviewParameterProvider : PreviewParameterProvider<List<User>> {
    override val values: Sequence<List<User>> = sequenceOf(
        // Empty list
        emptyList(),
        
        // Single user
        listOf(User(1, "Ana García", "ana@ejemplo.com")),
        
        // Multiple users
        listOf(
            User(1, "Ana García", "ana@ejemplo.com"),
            User(2, "Carlos López", "carlos@ejemplo.com"),
            User(3, "María Rodríguez", "maria@ejemplo.com")
        ),
        
        // Long names and emails (edge case)
        listOf(
            User(
                999, 
                "María Fernanda Jiménez González", 
                "maria.fernanda.jimenez@empresa.ejemplo.com"
            )
        ),
        
        // Many users (performance test)
        (1..20).map { 
            User(it, "Usuario $it", "usuario$it@ejemplo.com") 
        }
    )
}

// ========================================
// MULTI-DEVICE PREVIEWS
// ========================================

/**
 * Phone Preview - Compact Screen
 */
@Preview(
    name = "📱 Phone - UserList",
    showBackground = true,
    device = "spec:width=360dp,height=640dp,dpi=480"
)
@Composable
fun UserListPhonePreview(
    @PreviewParameter(UserPreviewParameterProvider::class) users: List<User>
) {
    MyfirstkmpprojectTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("📱 Phone Layout", style = MaterialTheme.typography.titleSmall)
            UserList(
                users = users.take(3), // Limit for phone preview
                onUserClick = { }
            )
        }
    }
}

/**
 * Tablet Preview - Medium Screen
 */
@Preview(
    name = "💻 Tablet - UserList",
    showBackground = true,
    device = "spec:width=840dp,height=1340dp,dpi=320"
)
@Composable
fun UserListTabletPreview() {
    val sampleUsers = (1..6).map { 
        User(it, "Usuario $it", "usuario$it@ejemplo.com") 
    }
    
    MyfirstkmpprojectTheme {
        Row(modifier = Modifier.padding(16.dp)) {
            // Left column
            Column(modifier = Modifier.weight(1f)) {
                Text("💻 Tablet Layout - Lista", style = MaterialTheme.typography.titleMedium)
                UserList(
                    users = sampleUsers,
                    onUserClick = { }
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Right column - Detail view
            Column(modifier = Modifier.weight(1f)) {
                Text("📋 Detalle", style = MaterialTheme.typography.titleMedium)
                sampleUsers.firstOrNull()?.let { user ->
                    UserCard(user = user, onClick = { })
                }
            }
        }
    }
}

/**
 * Desktop Preview - Large Screen
 */
@Preview(
    name = "🖥️ Desktop - UserList",
    showBackground = true,
    device = "spec:width=1920dp,height=1080dp,dpi=160"
)
@Composable
fun UserListDesktopPreview() {
    val sampleUsers = (1..8).map { 
        User(it, "Usuario $it", "usuario$it@ejemplo.com") 
    }
    
    MyfirstkmpprojectTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Sidebar
            Column(modifier = Modifier.weight(1f)) {
                Text("🖥️ Desktop Layout", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))
                UserList(
                    users = sampleUsers,
                    onUserClick = { }
                )
            }
            
            // Main content area
            Column(modifier = Modifier.weight(2f)) {
                Text("📊 Dashboard Principal", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))
                
                // Grid layout for desktop
                val chunkedUsers = sampleUsers.chunked(2)
                chunkedUsers.forEach { userPair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        userPair.forEach { user ->
                            UserCard(
                                user = user,
                                onClick = { },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        // Fill remaining space if odd number
                        if (userPair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

// ========================================
// THEME & ACCESSIBILITY PREVIEWS
// ========================================

/**
 * Theme Comparison Preview
 */
@Preview(
    name = "🌞 Light vs 🌙 Dark Theme",
    showBackground = true,
    heightDp = 600
)
@Composable
fun ThemeComparisonPreview() {
    val sampleUsers = listOf(
        User(1, "Ana García", "ana@ejemplo.com"),
        User(2, "Carlos López", "carlos@ejemplo.com")
    )
    
    Column {
        // Light theme
        MyfirstkmpprojectTheme(darkTheme = false) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("🌞 Light Theme", style = MaterialTheme.typography.titleMedium)
                    UserList(users = sampleUsers, onUserClick = { })
                }
            }
        }
        
        // Dark theme
        MyfirstkmpprojectTheme(darkTheme = true) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("🌙 Dark Theme", style = MaterialTheme.typography.titleMedium)
                    UserList(users = sampleUsers, onUserClick = { })
                }
            }
        }
    }
}

/**
 * Accessibility Preview - Large Font
 */
@Preview(
    name = "♿ Accessibility - Large Font",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
fun AccessibilityLargeFontPreview() {
    MyfirstkmpprojectTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("♿ Prueba de Accesibilidad", style = MaterialTheme.typography.titleMedium)
            Text("Fuente grande para usuarios con discapacidad visual", style = MaterialTheme.typography.bodySmall)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            UserCard(
                user = User(1, "Ana García López", "ana@ejemplo.com"),
                onClick = { }
            )
        }
    }
}

// ========================================
// EDGE CASE PREVIEWS
// ========================================

/**
 * Edge Cases - Long Text & Overflow
 */
@Preview(
    name = "⚠️ Edge Cases - Long Text",
    showBackground = true,
    widthDp = 280 // Narrow screen
)
@Composable
fun EdgeCasesPreview() {
    MyfirstkmpprojectTheme {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("⚠️ Casos Extremos", style = MaterialTheme.typography.titleSmall)
            
            // Very long name
            UserCard(
                user = User(
                    1, 
                    "María del Carmen Fernández González de la Torre y Vásquez", 
                    "maria.del.carmen.fernandez.gonzalez.delatorre@empresa.ejemplo.com"
                ),
                onClick = { }
            )
            
            // Numbers and special characters
            UserCard(
                user = User(
                    999999, 
                    "Test User 123 !@#$%", 
                    "test.user.123+tag@sub.domain.example.com"
                ),
                onClick = { }
            )
        }
    }
}

/**
 * Performance Preview - Many Items
 */
@Preview(
    name = "⚡ Performance - 50 Users",
    showBackground = true,
    heightDp = 600
)
@Composable
fun PerformancePreview() {
    val manyUsers = (1..50).map { 
        User(it, "Usuario $it", "usuario$it@ejemplo.com") 
    }
    
    MyfirstkmpprojectTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("⚡ Preview de Rendimiento", style = MaterialTheme.typography.titleMedium)
            Text("50 usuarios en LazyColumn", style = MaterialTheme.typography.bodySmall)
            
            Spacer(modifier = Modifier.height(8.dp))
            
            UserList(
                users = manyUsers,
                onUserClick = { }
            )
        }
    }
}
