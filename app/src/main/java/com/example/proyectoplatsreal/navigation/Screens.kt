package com.example.proyectoplatsreal.navigation

sealed class Screen(val route: String) {
    object SearchScreen : Screen("searchScreen")
    object ListeningScreen : Screen("listeningScreen")
    object PublishSongScreen : Screen("publishSongScreen")
    object PlaylistsScreen : Screen("playlistsScreen")
    object AjustesScreen : Screen("ajustesScreen")
    object GenreScreen : Screen("genreScreen/{genre}") {
        fun createRoute(genre: String) = "genreScreen/$genre"
    }
    object ResultScreen : Screen("generatedResultScreen")
    object SearchResultDisplay : Screen("searchResultDisplay")
    object MainScreen : Screen("mainScreen")
}
