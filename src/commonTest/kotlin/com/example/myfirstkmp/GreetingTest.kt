package com.example.myfirstkmp

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertContains

/**
 * Tests que se ejecutan en todas las plataformas
 * Esto asegura que nuestro código funciona igual en todas partes
 */
class GreetingTest {

    @Test
    fun testGreeting() {
        val greeting = Greeting()
        val result = greeting.greet()
        
        // Verificamos que el saludo contiene "Hola"
        assertContains(result, "Hola")
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun testGreetingWithName() {
        val greeting = Greeting()
        val name = "María"
        val result = greeting.greetWithName(name)
        
        // Verificamos que incluye el nombre
        assertContains(result, name)
        assertContains(result, "Hola")
    }

    @Test
    fun testRandomMessage() {
        val greeting = Greeting()
        val message = greeting.getRandomMessage()
        
        // Verificamos que devuelve algo
        assertTrue(message.isNotEmpty())
    }
}
