package com.example.proyectoplatsreal.ui.escuchando

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamratzman.spotify.models.Track
import com.example.proyectoplatsreal.networking.SpotifyApiRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class PublishSongViewModel : ViewModel() {
    private val spotifyApiRequest = SpotifyApiRequest().apply {
        initialize()
    }
    private val firestore = FirebaseFirestore.getInstance()
    val publishState = MutableLiveData<String>()
    val navigationEvent = MutableLiveData<Event<String>>()

    fun publishSong(songName: String, artistName: String) {
        viewModelScope.launch {
            val trackDetails = spotifyApiRequest.getSongDetails(songName, artistName)
            if (trackDetails != null) {
                val firestoreTrack = convertTrackToFirestoreTrack(trackDetails)
                val songData = hashMapOf(
                    "songName" to firestoreTrack.songName,
                    "artistName" to firestoreTrack.artistName,
                    "timestamp" to System.currentTimeMillis(),
                    "imageUrl" to firestoreTrack.imageUrl // Usando la imagen desde FirestoreTrack
                )
                firestore.collection("songs").add(songData)
                    .addOnSuccessListener {
                        navigationEvent.postValue(Event("navigate_to_feed"))
                        publishState.postValue("Success")
                    }
                    .addOnFailureListener { e ->
                        publishState.postValue("Error: ${e.message}")
                    }
            } else {
                publishState.postValue("Error: Song not found on Spotify")
            }
        }
    }

    private fun convertTrackToFirestoreTrack(track: Track): FirestoreTrack {
        return FirestoreTrack(
            songName = track.name,
            artistName = track.artists.joinToString { it.name },
            imageUrl = track.album.images.firstOrNull()?.url
        )
    }
}
