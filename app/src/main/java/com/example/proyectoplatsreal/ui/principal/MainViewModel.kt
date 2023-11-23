package com.example.proyectoplatsreal.ui.principal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoplatsreal.networking.SpotifyApiRequest
import com.adamratzman.spotify.models.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val spotifyApiRequest = SpotifyApiRequest().apply {
        initialize()
    }

    private val _searchResults = MutableStateFlow<List<Track>>(emptyList())
    val searchResults: StateFlow<List<Track>> = _searchResults

    private val _recommendations = MutableStateFlow<List<Track>>(emptyList())
    val recommendations: StateFlow<List<Track>> = _recommendations

    fun searchTracks(query: String) {
        viewModelScope.launch {
            try {
                val searchResults = spotifyApiRequest.trackSearch(query)
                _searchResults.value = searchResults

                if (searchResults.isNotEmpty()) {
                    val trackId = searchResults.first().id
                    getRecommendationsForTrack(trackId)
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun getRecommendationsForTrack(trackId: String) {
        viewModelScope.launch {
            try {
                val recommendations = spotifyApiRequest.getRecommendationsForTrack(trackId)
                _recommendations.value = recommendations
            } catch (e: Exception) {
            }
        }
    }

    private val _selectedGenre = MutableStateFlow<String?>(null)
    val selectedGenre: StateFlow<String?> = _selectedGenre

    fun selectGenre(genre: String) {
        _selectedGenre.value = genre
        searchByGenre(genre)
    }

    fun searchByGenre(genre: String) {
        viewModelScope.launch {
            try {
                val searchResults = spotifyApiRequest.genreSearch(genre)
                _searchResults.value = searchResults

                if (searchResults.isNotEmpty()) {
                    val trackId = searchResults.first().id
                    getRecommendationsForTrack(trackId)
                }
            } catch (e: Exception) {
            }
        }
    }
    fun clearGenreSelection() {
        _selectedGenre.value = null
    }
}
