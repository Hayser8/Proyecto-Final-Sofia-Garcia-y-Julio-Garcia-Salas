package com.example.proyectoplatsreal.ui.playlists


import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyectoplatsreal.R
import com.example.proyectoplatsreal.navigation.Screen
import com.example.proyectoplatsreal.ui.principal.GenreDisplay
import com.example.proyectoplatsreal.ui.principal.MainViewModel

@Composable
fun PlaylistsScreen(navController: NavHostController) {
    val viewModel: PlaylistsViewModel = viewModel(LocalContext.current as ComponentActivity)
    val genreState = remember { mutableStateOf("") }
    val artistState = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.playlists),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(R.string.logo_description),
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            FilterSection(stringResource(R.string.genre), genreState.value) { newGenre ->
                genreState.value = newGenre
            }
            Spacer(modifier = Modifier.height(20.dp))

            FilterSection(stringResource(R.string.artist), artistState.value) { newArtist ->
                artistState.value = newArtist
            }
            Spacer(modifier = Modifier.height(20.dp))


            Button(
                onClick = {
                    viewModel.searchTracksByGenreAndArtist(genreState.value, artistState.value)
                    navController.navigate(Screen.ResultScreen.route)
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.generate), color = Color.White)
            }

            Spacer(modifier = Modifier.height(40.dp))

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(title: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = stringResource(if (title == stringResource(R.string.genre)) R.string.enter_playlist_genre else R.string.enter_playlist_artist), color = Color.Gray)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White,
                textColor = Color.White,
            ),
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
