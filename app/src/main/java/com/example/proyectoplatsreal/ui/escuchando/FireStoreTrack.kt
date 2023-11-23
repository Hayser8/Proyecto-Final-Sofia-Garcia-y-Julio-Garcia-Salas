package com.example.proyectoplatsreal.ui.escuchando

import com.google.firebase.firestore.PropertyName

data class FirestoreTrack(
    @PropertyName("songName") val songName: String = "",
    @PropertyName("artistName") val artistName: String = "",
    @PropertyName("imageUrl") val imageUrl: String? = null,
    @PropertyName("timestamp") val timestamp: Long = 0
)