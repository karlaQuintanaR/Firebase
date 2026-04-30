package com.example.firebase

import kotlinx.coroutines.flow.MutableStateFlow

object NotificationStorage {
    // Esta variable guardará el Título y el Cuerpo del mensaje
    // MutableStateFlow permite que la interfaz de Compose "reaccione" al cambio
    val latestMessage = MutableStateFlow<Pair<String, String>?>(null)
}