package com.spotify.app.feature_homepage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.spotify.app.core_base.shared.domain.model.AlbumItem
import com.spotify.app.feature_homepage.shared.ui.HomePageContract
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomePageComposable(
    viewModel: HomePageViewModel,
    onPlaylistClick: (playlistId: String) -> Unit,
    onAlbumClick: (albumItem: AlbumItem) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is HomePageContract.Effect.ShowToast -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = it.message
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.init()
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(HomePageContract.Event.OnFetchHomePageEvent)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black)
        ) {
            when (val data = uiState.value.state) {
                HomePageContract.HomePageState.Idle -> {
                    //Ignore this case
                }

                HomePageContract.HomePageState.Loading -> {
                    HomeScreenPlaceholder()
                }

                is HomePageContract.HomePageState.Success -> {
                    val playlists = data.playlists
                    val albums = data.albums
                    LazyColumn {
                        item {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp)
                            )
                        }
                        item {
                            if (playlists.isNotEmpty()) {
                                Text(
                                    text = "Featured Playlists",
                                    fontSize = 24.sp,
                                    color = Color.White,
                                    fontStyle = FontStyle.Normal,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily.SansSerif,
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(horizontal = 6.dp)
                                )
                            }
                        }
                        item {
                            LazyRow(
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                            ) {
                                items(
                                    items = playlists,
                                    key = { it.id }
                                ) {
                                    Column {
                                        AsyncImage(
                                            model = it.image,
                                            contentDescription = "Playlist ${it.name}",
                                            modifier = Modifier
                                                .padding(horizontal = 6.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .width(200.dp)
                                                .height(200.dp)
                                                .clickable {
                                                    onPlaylistClick.invoke(it.id)
                                                }
                                        )

                                        Text(
                                            text = it.name,
                                            color = Color.White,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp,
                                            fontFamily = FontFamily.SansSerif,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .padding(start = 6.dp, top = 4.dp)
                                                .width(180.dp)
                                        )

                                        Text(
                                            text = it.description,
                                            color = Color.White.copy(alpha = 0.8f),
                                            fontWeight = FontWeight.Light,
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily.SansSerif,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .padding(start = 6.dp, top = 2.dp)
                                                .width(180.dp)
                                        )
                                    }
                                }
                            }
                        }
                        item {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp)
                            )
                        }
                        item {
                            if (albums.isNotEmpty()) {
                                Text(
                                    text = "Featured Albums",
                                    fontSize = 24.sp,
                                    color = Color.White,
                                    fontStyle = FontStyle.Normal,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily.SansSerif,
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(horizontal = 6.dp)
                                )
                            }
                        }
                        item {
                            LazyRow(
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                            ) {
                                items(
                                    items = albums,
                                    key = { it.id }
                                ) {
                                    Column {
                                        AsyncImage(
                                            model = it.image,
                                            contentDescription = "Album ${it.name}",
                                            modifier = Modifier
                                                .padding(horizontal = 6.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .width(200.dp)
                                                .height(200.dp)
                                                .clickable {
                                                    onAlbumClick.invoke(it)
                                                }
                                        )

                                        Text(
                                            text = it.name,
                                            color = Color.White,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp,
                                            fontFamily = FontFamily.SansSerif,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .padding(start = 6.dp, top = 4.dp)
                                                .width(180.dp)
                                        )

                                        Text(
                                            text = it.artists,
                                            color = Color.White.copy(alpha = 0.8f),
                                            fontWeight = FontWeight.Light,
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily.SansSerif,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .padding(start = 6.dp, top = 2.dp)
                                                .width(180.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}