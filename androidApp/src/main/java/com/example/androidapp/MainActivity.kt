package com.example.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidapp.ui.theme.MyfirstkmpprojectTheme
import com.example.androidapp.ui.screens.UserScreen

/**
 * MainActivity - Entry Point de la App Android
 * 
 * ComponentActivity vs Activity:
 * - ComponentActivity: Base class moderna de Jetpack
 * - Includes lifecycle extensions, compose integration
 * - Supports result contracts, view model providers
 * - Better for modern Android development
 * 
 * Activity Lifecycle Refresher:
 * onCreate() -> onStart() -> onResume() -> [Running] -> onPause() -> onStop() -> onDestroy()
 * 
 * Compose Integration:
 * - setContent{}: Replaces setContentView() for Compose UIs
 * - No XML layouts needed, everything is Kotlin code
 * - Automatic lifecycle integration
 */
class MainActivity : ComponentActivity() {
    
    /**
     * onCreate() - Activity Creation
     * 
     * Called when activity is first created
     * - Setup UI (setContent for Compose)
     * - Initialize components
     * - Don't do heavy work here (use coroutines/background)
     * 
     * savedInstanceState: Restore previous state after rotation/process death
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        /**
         * Edge-to-Edge Display
         * 
         * Modern Android pattern:
         * - App draws behind system bars (status bar, navigation bar)
         * - More immersive experience
         * - Handle window insets properly (padding around system UI)
         * - Material 3 components handle this automatically
         */
        enableEdgeToEdge()
        
        /**
         * Compose UI Setup
         * 
         * setContent: Compose equivalent of setContentView()
         * - Creates Compose hierarchy
         * - Automatically handles recomposition
         * - Integrates with Activity lifecycle
         */
        setContent {
            /**
             * Material 3 Theme Wrapper
             * 
             * Provides:
             * - Color scheme (light/dark theme support)
             * - Typography system (Material 3 text styles)
             * - Shape system (corner radius, elevations)
             * - Component defaults
             * 
             * Theme propagation: Child composables automatically inherit theme
             */
            MyfirstkmpprojectTheme {
                /**
                 * Scaffold - App Structure Component
                 * 
                 * Material Design app structure:
                 * - Provides slots: topBar, bottomBar, floatingActionButton, content
                 * - Handles proper spacing between components
                 * - Manages edge-to-edge insets automatically
                 * - Consistent Material 3 behavior
                 */
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    /**
                     * Main Screen Content
                     * 
                     * innerPadding: Accounts for system bars, app bars, etc.
                     * Apply to main content to avoid overlapping with system UI
                     * 
                     * Navigation: In larger apps, this would be NavHost
                     * For now, directly showing UserScreen
                     */
                    UserScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}