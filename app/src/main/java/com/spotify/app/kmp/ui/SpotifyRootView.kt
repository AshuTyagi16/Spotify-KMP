package com.spotify.app.kmp.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import com.spotify.app.feature_homepage.ui.HomePageComposable
import com.spotify.app.feature_playlist_detail.shared.ui.PlaylistDetailViewModel
import com.spotify.app.feature_playlist_detail.ui.PlaylistDetailComposable
import com.spotify.app.kmp.navigation.Screen
import org.koin.androidx.compose.koinViewModel

private const val ANIMATION_DURATION = 500
private const val PLAYLIST_ID = "PLAYLIST_ID"

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
                onPlaylistClick = { playlistId ->
                    navController.navigate(
                        Screen.PlaylistDetail.withArgs(playlistId)
                    )
                }
            )
        }

        composable(
            route = Screen.PlaylistDetail.route + "/{$PLAYLIST_ID}",
            arguments = listOf(
                navArgument(PLAYLIST_ID) {
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
            val viewModel = koinViewModel<PlaylistDetailViewModel>()
            PlaylistDetailComposable(
                playlistId = playlistId,
                viewModel = viewModel
            )
        }
    }
}