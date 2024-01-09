package com.spotify.app.feature_playlist_detail.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.ui.PlaylistDetailViewModel

@Composable
fun PlaylistDetailComposable(
    playlistId: String,
    viewModel: PlaylistDetailViewModel
) {
    LaunchedEffect(viewModel) {
        viewModel.fetchPlaylistDetail(
            fetchPlaylistDetailRequest = FetchPlaylistDetailRequest(
                playlistId = playlistId,
                limit = 1,
                offset = 0
            )
        )
    }
    val data = viewModel.data.collectAsState()

    Text(
        text = "STATUS : ${data.value.status.name} :: DATA : ${data.value.data?.size}"
    )

}