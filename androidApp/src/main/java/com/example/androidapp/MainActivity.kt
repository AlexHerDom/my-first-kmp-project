package com.example.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidapp.ui.theme.MyfirstkmpprojectTheme
import com.example.myfirstkmp.Greeting // ← ¡Tu clase!

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyfirstkmpprojectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GreetingApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GreetingApp(modifier: Modifier = Modifier) {
    val greeting = remember { Greeting() } // ← ¡Tu clase en acción!
    var currentMessage by remember { mutableStateOf("¡Presiona un botón!") }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Mostrar el mensaje actual
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = currentMessage,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Botón 1: Saludo básico
        Button(
            onClick = { 
                currentMessage = greeting.greet() // ← Tu función!
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Saludo Básico")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Botón 2: Saludo con nombre
        Button(
            onClick = { 
                currentMessage = greeting.greetWithName("María") // ← Tu función!
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Saludar a María")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Botón 3: Mensaje aleatorio
        Button(
            onClick = { 
                currentMessage = greeting.getRandomMessage() // ← Tu función!
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mensaje Aleatorio")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyfirstkmpprojectTheme {
        GreetingApp()
    }
}