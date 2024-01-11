package com.spotify.app.feature_homepage.shared.ui

import com.spotify.app.core_base.shared.domain.model.AlbumItem
import com.spotify.app.core_base.shared.ui.UiEffect
import com.spotify.app.core_base.shared.ui.UiEvent
import com.spotify.app.core_base.shared.ui.UiState
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem

class HomePageContract {
    sealed interface Event : UiEvent {
        data object OnFetchHomePageEvent : Event
    }

    data class State(
        val state: HomePageState
    ) : UiState

    sealed interface HomePageState {
        data object Idle : HomePageState
        data object Loading : HomePageState
        data class Success(
            val playlists: List<PlaylistItem>,
            val albums: List<AlbumItem>
        ) : HomePageState
    }

    sealed interface Effect : UiEffect {
        data class ShowToast(val message: String) : Effect
    }
}