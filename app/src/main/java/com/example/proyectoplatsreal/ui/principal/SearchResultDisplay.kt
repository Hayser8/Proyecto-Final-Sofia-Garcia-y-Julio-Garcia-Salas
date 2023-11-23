package com.example.proyectoplatsreal.ui.principal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.adamratzman.spotify.models.Track
import com.example.proyectoplatsreal.R

@Composable
fun SearchResultDisplay(
    searchResults: State<List<Track>>,
    recommendations: State<List<Track>>,
    navController: NavController,
    isDarkMode: Boolean
) {
    Column {
        LazyColumn {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription =  stringResource(R.string.back_content_description),
                            tint = Color.White
                        )
                    }
                    Text(
                        stringResource(R.string.search_results_title),
                        style = MaterialTheme.typography.h6.copy(
                            color = Color.White,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(searchResults.value.take(3)) { track ->
                TrackCard(track = track, isDarkMode)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (recommendations.value.isNotEmpty()) {
                item {
                    Text(
                        stringResource(R.string.search_recommendations_title),
                        style = MaterialTheme.typography.h6.copy(
                            color = Color.White,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                items(recommendations.value) { track ->
                    TrackCard(track = track, isDarkMode)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

