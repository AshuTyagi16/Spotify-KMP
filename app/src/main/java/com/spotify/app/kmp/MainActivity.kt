package com.spotify.app.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import com.spotify.app.kmp.ui.theme.SpotifyKMPTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel:HomePageViewModel by viewModel()

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
                        viewModel.fetchFeaturedPlaylists()
                    }
                    val state = viewModel.data.collectAsState()
                    Greeting(state)
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: State<RestClientResult<List<PlaylistItem>>>,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hello ${name.value.status.name}! :: data is ${name.value.data?.size}",
        modifier = modifier
    )
}