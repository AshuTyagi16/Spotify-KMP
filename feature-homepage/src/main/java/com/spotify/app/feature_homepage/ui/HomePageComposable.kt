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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import kotlinx.coroutines.launch

@Composable
fun HomePageComposable(
    viewModel: HomePageViewModel,
    onPlaylistClick: (playlistId: String) -> Unit
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

    val playlists = playlistState.value.data.orEmpty()
    val albums = albumState.value.data.orEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
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