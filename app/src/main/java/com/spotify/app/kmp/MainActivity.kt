package com.spotify.app.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_homepage.shared.domain.model.album.AlbumItem
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import com.spotify.app.kmp.ui.theme.SpotifyKMPTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: HomePageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotifyKMPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(viewModel) {
                        launch {
                            viewModel.fetchFeaturedPlaylists()
                        }
                        launch {
                            viewModel.fetchFeaturedAlbums()
                        }
                    }
                    val playlistState = viewModel.featuredPlaylistData.collectAsState()
                    val albumState = viewModel.featuredAlbumsData.collectAsState()
                    Greeting(playlistState, albumState)
                }
            }
        }
    }
}

@Composable
fun Greeting(
    playlistState: State<RestClientResult<List<PlaylistItem>>>,
    albumState: State<RestClientResult<List<AlbumItem>>>,
    modifier: Modifier = Modifier
) {
    Column {

        Text(
            text = "Playlists ${playlistState.value.status.name}! :: count is ${playlistState.value.data?.size}",
            modifier = modifier
        )

        Text(
            text = "Albums ${albumState.value.status.name}! :: count is ${albumState.value.data?.size}",
            modifier = modifier
        )
    }
}