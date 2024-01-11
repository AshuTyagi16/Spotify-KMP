package com.spotify.app.feature_album_detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.spotify.app.core_base.shared.util.BaseConstants
import com.spotify.app.feature_album_detail.shared.domain.model.FetchAlbumDetailRequest
import com.spotify.app.feature_album_detail.shared.ui.AlbumDetailViewModel
import java.net.URLDecoder

@Composable
fun AlbumDetailComposable(
    albumId: String,
    albumUrl: String,
    viewModel: AlbumDetailViewModel
) {
    LaunchedEffect(viewModel) {
        viewModel.fetchAlbumDetail(
            fetchAlbumDetailRequest = FetchAlbumDetailRequest(
                albumId = albumId,
                limit = BaseConstants.DEFAULT_PAGE_SIZE,
                offset = BaseConstants.DEFAULT_OFFSET
            )
        )
    }
    val data = viewModel.data.collectAsLazyPagingItems()
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                        model = URLDecoder.decode(albumUrl, "utf-8"),
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

            when (val state = data.loadState.refresh) { //FIRST LOAD
                is LoadState.Error -> {
                    item {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "ERROR ${state.error.message}",
                            color = Color.White
                        )
                    }
                }

                is LoadState.Loading -> { // Loading UI
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = "Refresh Loading",
                                color = Color.White
                            )

                            CircularProgressIndicator(color = Color.Black)
                        }
                    }
                }

                else -> {}
            }

            when (val state = data.loadState.append) { // Pagination
                is LoadState.Error -> {
                    item {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "ERROR ${state.error.message}",
                            color = Color.White
                        )
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
                                text = "Pagination Loading",
                                color = Color.White
                            )

                            CircularProgressIndicator(color = Color.Black)
                        }
                    }
                }

                else -> {}
            }
        }
    }

}

