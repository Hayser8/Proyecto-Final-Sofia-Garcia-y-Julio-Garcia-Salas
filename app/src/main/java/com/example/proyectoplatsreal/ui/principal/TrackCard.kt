package com.example.proyectoplatsreal.ui.principal

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import com.adamratzman.spotify.models.Track
import com.example.proyectoplatsreal.R
import com.example.proyectoplatsreal.ui.theme.link
import com.example.proyectoplatsreal.ui.theme.links
import com.example.proyectoplatsreal.ui.theme.secondarybc
import com.example.proyectoplatsreal.ui.theme.tertiarybase
import com.example.proyectoplatsreal.ui.theme.tertiarydark
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun TrackCard(track: Track, isDarkMode: Boolean) {
    val context = LocalContext.current
    val imageUrl = track.album?.images?.firstOrNull()?.url
    val spotifyUri = track.uri
    val artistNames = track.artists.joinToString { it.name }
    val backgroundColor = if (isDarkMode) tertiarydark else tertiarybase
    val linkcolor = if (isDarkMode) link else links
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(20.dp)
            .background(backgroundColor)
            .padding(8.dp)
            .padding(vertical = 4.dp)
    ){
        Image(
            painter = rememberImagePainter(data = imageUrl),
            contentDescription = stringResource(R.string.song_image_description),
            modifier = Modifier
                .size(50.dp)
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.width(10.dp))


        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.song_artist_format, track.name, artistNames),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(5.dp))


            Text(
                text = stringResource(R.string.listen_on_spotify),
                color = linkcolor,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(spotifyUri.toString()))
                    context.startActivity(intent)
                }
            )
        }
    }
}







