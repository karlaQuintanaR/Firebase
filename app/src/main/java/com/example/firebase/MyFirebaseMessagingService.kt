package com.example.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM_KARLA", "¡Mensaje recibido de: ${remoteMessage.from}!")

        // Usamos ?.let para verificar que la notificación no sea nula
        remoteMessage.notification?.let {
            // DEBES usar it.title e it.body
            val titulo = it.title ?: "Sin título"
            val cuerpo = it.body ?: "Sin cuerpo"

            Log.d("FCM_KARLA", "Título: $titulo")
            Log.d("FCM_KARLA", "Cuerpo: $cuerpo")

            // Ahora enviamos las variables que sí existen al Storage
            NotificationStorage.latestMessage.value = Pair(titulo, cuerpo)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_KARLA", "Tu Token es: $token")
    }
}