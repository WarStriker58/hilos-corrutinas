package com.example.ejemploshiloscorrutinas

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
    var estado by remember { mutableStateOf("Esperando acción...") }

    // Solución correcta para Corrutinas en Compose: obtener el scope del ciclo de vida del componente
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = estado,
            style = MaterialTheme.typography.headlineSmall
        )

        // Botón 1 - SIN HILOS (Bloquea la UI por completo)
        Button(onClick = {
            estado = "Iniciando tarea SIN hilos..."
            Thread.sleep(5000)
            estado = "Tarea completada (sin hilos)"
        }) {
            Text("Ejemplo SIN hilos")
        }

        // Botón 2 - CON HILOS (Corregido para actualizar la UI de forma segura)
        Button(onClick = {
            estado = "Iniciando tarea CON hilos..."
            Thread {
                Thread.sleep(5000) // Tarea pesada en hilo secundario

                // CORRECCIÓN: Volver al hilo principal para actualizar el estado
                Handler(Looper.getMainLooper()).post {
                    estado = "Tarea completada (con hilos)"
                }
            }.start()
        }) {
            Text("Ejemplo CON hilos")
        }

        // Botón 3 - ENFOQUE TRADICIONAL (Alternativa clásica a las corrutinas)
        Button(onClick = {
            estado = "Iniciando tarea SIN corrutinas..."

            // CORRECCIÓN: Especificar explícitamente el Looper principal
            Handler(Looper.getMainLooper()).postDelayed({
                estado = "Tarea completada (sin corrutinas)"
            }, 5000)
        }) {
            Text("Ejemplo SIN corrutinas")
        }

        // Botón 4 - CON CORRUTINAS (Corregido con las mejores prácticas de Compose)
        Button(onClick = {
            estado = "Iniciando tarea CON corrutinas..."

            // CORRECCIÓN: El scope se cancelará automáticamente si la pantalla se destruye
            scope.launch {
                delay(5000) // Suspensión eficiente sin bloquear ningún hilo
                estado = "Tarea completada (con corrutinas)"
            }
        }) {
            Text("Ejemplo CON corrutinas")
        }
    }
}
