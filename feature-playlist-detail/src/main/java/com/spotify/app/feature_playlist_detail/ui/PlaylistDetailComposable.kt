package com.spotify.app.feature_playlist_detail.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.spotify.app.core_base.shared.util.BaseConstants
import com.spotify.app.feature_playlist_detail.shared.domain.model.FetchPlaylistDetailRequest
import com.spotify.app.feature_playlist_detail.shared.ui.PlaylistDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun PlaylistDetailComposable(
    playlistId: String,
    viewModel: PlaylistDetailViewModel
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.fetchPlaylistDetail(
            fetchPlaylistDetailRequest = FetchPlaylistDetailRequest(
                playlistId = playlistId,
                limit = BaseConstants.DEFAULT_PAGE_SIZE,
                offset = BaseConstants.DEFAULT_OFFSET
            )
        )
    }

    val data = viewModel.pagingData.collectAsLazyPagingItems()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black.copy(alpha = 0.92f))
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                items(
                    count = data.itemCount,
                    key = data.itemKey { it.id },
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                    ) {
                        AsyncImage(
                            model = data[it]?.image,
                            placeholder = rememberAsyncImagePainter(
                                model = BaseConstants.LOADING_ERROR_PLACEHOLDER
                            ),
                            error = rememberAsyncImagePainter(
                                model = BaseConstants.LOADING_ERROR_PLACEHOLDER
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = data[it]?.trackName,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .width(50.dp)
                                .height(50.dp)
                        )

                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .height(50.dp)
                        ) {
                            Text(
                                text = "${it + 1} - ${data[it]?.trackName}",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                fontFamily = FontFamily.SansSerif,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                            )

                            Text(
                                text = data[it]?.artists.orEmpty(),
                                color = Color.White.copy(alpha = 0.8f),
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.SansSerif,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .padding(top = 2.dp)
                            )
                        }
                    }
                }

                when (val state = data.loadState.refresh) {
                    is LoadState.Error -> {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = state.error.message.orEmpty()
                            )
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize()
                                    .height(50.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = {
                                        data.retry()
                                    }
                                ) {
                                    Text(
                                        text = "Retry",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }

                    is LoadState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillParentMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                PlaylistDetailPlaceholder()
                            }
                        }
                    }

                    else -> {}
                }

                when (val state = data.loadState.append) {
                    is LoadState.Error -> {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = state.error.message.orEmpty()
                            )
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = {
                                        data.retry()
                                    }
                                ) {
                                    Text(
                                        text = "Retry",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }

                    is LoadState.Loading -> { // Pagination Loading UI
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {

                                Text(
                                    text = "Loading",
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                CircularProgressIndicator(color = Color.White)
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}

