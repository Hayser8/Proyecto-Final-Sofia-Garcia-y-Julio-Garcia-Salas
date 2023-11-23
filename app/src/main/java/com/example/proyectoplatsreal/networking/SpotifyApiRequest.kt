package com.example.proyectoplatsreal.networking

import android.util.Log
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.models.SpotifyPublicUser
import com.adamratzman.spotify.models.SpotifySearchResult
import com.adamratzman.spotify.models.Track
import com.adamratzman.spotify.spotifyAppApi
import com.adamratzman.spotify.utils.Market
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URLEncoder

class SpotifyApiRequest {
    private val clientId = "d5582c13eb144d6092f05337498e4d7c"
    private val clientSecret = "fc0e81f4565b4259b1eddf7d62bd8f9d"
    private var api: SpotifyAppApi? = null


    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            buildSearchAPI()
        }
    }

    private suspend fun buildSearchAPI() {
        api = spotifyAppApi(clientId, clientSecret).build()
    }

    suspend fun trackSearch(searchQuery: String): List<Track> {
        Log.d("SpotifyApiRequest", "trackSearch - Query: $searchQuery")
        return try {
            val results = api?.search?.searchTrack(searchQuery, market = Market.US)?.items
            Log.d("SpotifyApiRequest", "trackSearch - Resultados encontrados: ${results?.size ?: 0}")
            api?.search?.searchTrack(searchQuery, market = Market.US)?.items ?: emptyList()
        } catch (e: Exception) {
            Log.e("SpotifyApiRequest", "Error en búsqueda de tracks: ${e.message}")
            emptyList()
        }
    }

    suspend fun getRecommendationsForTrack(trackId: String): List<Track> {
        return api?.browse?.getTrackRecommendations(seedTracks = listOf(trackId), market = Market.US)?.tracks ?: emptyList()
    }

    suspend fun genreSearch(genre: String): List<Track> {
        return api?.search?.searchTrack("genre:\"$genre\"", market = Market.US)?.items ?: emptyList()
    }

    suspend fun searchArtist(artistName: String): String? {
        return try {
            val searchResult = api?.search?.searchArtist(artistName, market = Market.US)?.items?.firstOrNull()
            searchResult?.id
        } catch (e: Exception) {
            Log.e("SpotifyApiRequest", "Error en búsqueda de artista: ${e.message}")
            null
        }
    }

    suspend fun getRecommendations(genre: String, artistId: String?): List<Track> {
        return try {
            if (artistId == null) {
                emptyList()
            } else {
                api?.browse?.getTrackRecommendations(seedArtists = listOf(artistId), seedGenres = listOf(genre), market = Market.US)?.tracks ?: emptyList()
            }
        } catch (e: Exception) {
            Log.e("SpotifyApiRequest", "Error al obtener recomendaciones: ${e.message}")
            emptyList()
        }
    }


    suspend fun getSongDetails(songName: String, artistName: String): Track? {
        val query = "$songName $artistName"  // Formato simplificado
        Log.d("SpotifyApiRequest", "getSongDetails - Query: $query")

        return try {
            val results = api?.search?.searchTrack(query, market = Market.US)?.items
            Log.d("SpotifyApiRequest", "getSongDetails - Resultados encontrados: ${results?.size ?: 0}")
            results?.firstOrNull()
        } catch (e: Exception) {
            Log.e("SpotifyApiRequest", "getSongDetails - Error en búsqueda de detalles de la canción: ${e.message}")
            null
        }
    }


}