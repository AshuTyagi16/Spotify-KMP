package com.spotify.app.feature_album_detail.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.spotify.app.feature_album_detail.shared.ui.AlbumDetailViewModel
import kotlinx.coroutines.launch
import java.net.URLDecoder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumDetailComposable(
    albumId: String,
    albumName: String,
    albumImage: String,
    viewModel: AlbumDetailViewModel,
    onBackPressed: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.fetchAlbumDetail(albumId = albumId)
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
            ) {
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.92f))
                            .padding(horizontal = 6.dp, vertical = 12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = com.spotify.app.core_ui.R.drawable.ic_arrow_back),
                            contentDescription = "Back Button",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .clickable {
                                    onBackPressed.invoke()
                                }
                        )

                        Text(
                            text = albumName,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                        )
                    }
                }
                item {
                    AsyncImage(
                        model = URLDecoder.decode(albumImage, "utf-8"),
                        placeholder = rememberAsyncImagePainter(
                            model = BaseConstants.LOADING_ERROR_PLACEHOLDER
                        ),
                        error = rememberAsyncImagePainter(
                            model = BaseConstants.LOADING_ERROR_PLACEHOLDER
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = albumName,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .height(400.dp)
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                }
                items(
                    count = data.itemCount,
                    key = data.itemKey { it.id },
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                    ) {
                        AsyncImage(
                            model = URLDecoder.decode(albumImage, "utf-8"),
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
                                AlbumDetailPlaceholder()
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

