package com.spotify.app.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.feature_homepage.shared.domain.model.playlist.FeaturedPlaylists
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import com.spotify.app.kmp.ui.theme.SpotifyKMPTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<HomePageViewModelAndroid>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getInstance().fetchFeaturedPlaylists()
        setContent {
            SpotifyKMPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = viewModel.getInstance().data.collectAsState()
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