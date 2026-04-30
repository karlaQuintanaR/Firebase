package com.example.firebase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebase.ui.theme.FirebaseTheme
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // --- Recuperar Token para el Logcat ---
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM_KARLA", "Fallo al obtener el token", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("FCM_KARLA", "Token actual: $token")
        }

        setContent {
            FirebaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Llamamos a nuestra pantalla principal
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    // "Observamos" el almacenamiento de notificaciones.
    // Si cambia en el Service, aquí se activa la magia.
    val messageReceived by NotificationStorage.latestMessage.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Panel de Notificaciones",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4E342E) // Café oscuro
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Si NO hay mensaje, mostramos un aviso simple
        if (messageReceived == null) {
            Text(text = "Esperando notificación desde PHP...", color = Color.Gray)
        } else {
            // Si HAY mensaje, mostramos la tarjeta café
            NotificationCard(
                titulo = messageReceived!!.first,
                cuerpo = messageReceived!!.second
            )
        }
    }
}

@Composable
fun NotificationCard(titulo: String, cuerpo: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF4E342E) // Tu color café
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = titulo,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = cuerpo,
                color = Color(0xFFD7CCC8), // Café clarito para el texto
                fontSize = 16.sp
            )
        }
    }
}