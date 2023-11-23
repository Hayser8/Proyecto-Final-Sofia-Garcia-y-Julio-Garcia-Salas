package com.example.proyectoplatsreal.ui.playlists

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoplatsreal.networking.SpotifyApiRequest
import com.adamratzman.spotify.models.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistsViewModel : ViewModel() {
    private val spotifyApiRequest = SpotifyApiRequest()
    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks

    init {
        spotifyApiRequest.initialize()
    }



    fun searchTracksByGenreAndArtist(genre: String, artist: String) {
        viewModelScope.launch {
            val artistId = spotifyApiRequest.searchArtist(artist) // Necesitarás implementar esta función
            val results = spotifyApiRequest.getRecommendations(genre, artistId) // Implementa esta función también
            _tracks.value = results
        }
    }

}