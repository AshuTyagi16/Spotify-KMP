package com.spotify.app.kmp.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.spotify.app.feature_album_detail.shared.ui.AlbumDetailViewModel
import com.spotify.app.feature_album_detail.ui.AlbumDetailComposable
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import com.spotify.app.feature_homepage.ui.HomePageComposable
import com.spotify.app.feature_playlist_detail.shared.ui.PlaylistDetailViewModel
import com.spotify.app.feature_playlist_detail.ui.PlaylistDetailComposable
import com.spotify.app.kmp.navigation.Screen
import org.koin.androidx.compose.koinViewModel
import java.net.URLEncoder

private const val ANIMATION_DURATION = 500
private const val PLAYLIST_ID = "PLAYLIST_ID"
private const val PLAYLIST_NAME = "PLAYLIST_NAME"
private const val PLAYLIST_IMAGE = "PLAYLIST_IMAGE"

private const val ALBUM_ID = "ALBUM_ID"
private const val ALBUM_IMAGE = "ALBUM_IMAGE"
private const val ALBUM_NAME = "ALBUM_NAME"

@Composable
fun SpotifyRootView() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomePage.route) {

        composable(
            route = Screen.HomePage.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            }
        ) {
            val viewModel = koinViewModel<HomePageViewModel>()
            HomePageComposable(
                viewModel = viewModel,
                onPlaylistClick = { playlistItem ->
                    navController.navigate(
                        Screen.PlaylistDetail.withArgs(
                            playlistItem.id,
                            playlistItem.name,
                            URLEncoder.encode(playlistItem.image, "utf-8")
                        )
                    )
                },
                onAlbumClick = { albumItem ->
                    navController.navigate(
                        Screen.AlbumDetail.withArgs(
                            albumItem.id,
                            albumItem.name,
                            URLEncoder.encode(albumItem.image, "utf-8")
                        )
                    )
                }
            )
        }

        // Playlist Detail
        composable(
            route = Screen.PlaylistDetail.route + "/{$PLAYLIST_ID}" + "/{$PLAYLIST_NAME}" + "/{$PLAYLIST_IMAGE}",
            arguments = listOf(
                navArgument(PLAYLIST_ID) {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument(PLAYLIST_NAME) {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument(PLAYLIST_IMAGE) {
                    type = NavType.StringType
                    nullable = false
                },
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            }
        ) { entry ->
            val playlistId = entry.arguments?.getString(PLAYLIST_ID)!!
            val playlistName = entry.arguments?.getString(PLAYLIST_NAME)!!
            val playlistImage = entry.arguments?.getString(PLAYLIST_IMAGE)!!
            val viewModel = koinViewModel<PlaylistDetailViewModel>()
            PlaylistDetailComposable(
                playlistId = playlistId,
                playlistName = playlistName,
                playlistImage = playlistImage,
                viewModel = viewModel,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

        // Album Detail
        composable(
            route = Screen.AlbumDetail.route + "/{$ALBUM_ID}" + "/{$ALBUM_NAME}" + "/{$ALBUM_IMAGE}",
            arguments = listOf(
                navArgument(ALBUM_ID) {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument(ALBUM_NAME) {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument(ALBUM_IMAGE) {
                    type = NavType.StringType
                    nullable = false
                },
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            }
        ) { entry ->
            val albumId = entry.arguments?.getString(ALBUM_ID)!!
            val albumName = entry.arguments?.getString(ALBUM_NAME)!!
            val albumImage = entry.arguments?.getString(ALBUM_IMAGE)!!
            val viewModel = koinViewModel<AlbumDetailViewModel>()

            AlbumDetailComposable(
                albumId = albumId,
                albumName = albumName,
                albumImage = albumImage,
                viewModel = viewModel,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}