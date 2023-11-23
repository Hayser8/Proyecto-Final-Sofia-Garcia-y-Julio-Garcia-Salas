import android.util.Log
import androidx.activity.ComponentActivity
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.adamratzman.spotify.models.Track
import com.example.proyectoplatsreal.R
import com.example.proyectoplatsreal.ui.playlists.PlaylistsViewModel
import com.example.proyectoplatsreal.ui.principal.GenreTrackCard
import com.example.proyectoplatsreal.ui.principal.MainViewModel

@Composable
fun ResultScreen(navController: NavController, isDarkMode: Boolean) {
    val viewModel: PlaylistsViewModel = viewModel(LocalContext.current as ComponentActivity)
    val tracks = viewModel.tracks.collectAsState()

    LaunchedEffect(tracks.value) {
        Log.d("ResultScreen", "Tracks en la UI: ${tracks.value.joinToString { it.name }}")
    }

    LazyColumn {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_content_description),
                        tint = Color.White
                    )
                }
                Text(
                    stringResource(R.string.generatedplaylist),
                    style = MaterialTheme.typography.h6.copy(
                        color = Color.White,
                        fontSize = 17.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
        items(tracks.value) { track ->
            GenreTrackCard(track, isDarkMode ) // Muestra el nombre de cada track
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}