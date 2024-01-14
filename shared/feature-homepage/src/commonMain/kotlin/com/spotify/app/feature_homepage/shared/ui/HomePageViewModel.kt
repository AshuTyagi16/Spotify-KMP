package com.spotify.app.feature_homepage.shared.ui

import com.spotify.app.core_base.shared.domain.model.AlbumItem
import com.spotify.app.core_base.shared.models.ViewModel
import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.core_network.shared.impl.util.isError
import com.spotify.app.core_network.shared.impl.util.isLoading
import com.spotify.app.core_network.shared.impl.util.isSuccess
import com.spotify.app.core_network.shared.impl.util.onError
import com.spotify.app.core_network.shared.impl.util.onLoading
import com.spotify.app.core_network.shared.impl.util.onSuccess
import com.spotify.app.feature_homepage.shared.domain.model.playlist.PlaylistItem
import com.spotify.app.feature_homepage.shared.domain.use_case.FetchFeaturedAlbumsUseCase
import com.spotify.app.feature_homepage.shared.domain.use_case.FetchFeaturedPlaylistsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine

class HomePageViewModel(
    private val fetchFeaturedPlaylistsUseCase: FetchFeaturedPlaylistsUseCase,
    private val fetchFeaturedAlbumsUseCase: FetchFeaturedAlbumsUseCase
) : ViewModel() {

    private val currentState: HomePageContract.State
        get() = uiState.value

    private val _uiState: MutableStateFlow<HomePageContract.State> =
        MutableStateFlow(createInitialState())
    val uiState: StateFlow<HomePageContract.State>
        get() = _uiState

    private val _event: MutableSharedFlow<HomePageContract.Event> = MutableSharedFlow()

    private val _effect: MutableSharedFlow<HomePageContract.Effect> = MutableSharedFlow()
    val effect: SharedFlow<HomePageContract.Effect>
        get() = _effect


    suspend fun init() {
        subscribeEvents()
    }

    private suspend fun fetchHomePageData() {
        fetchFeaturedPlaylistsUseCase.invoke()
            .combine<RestClientResult<List<PlaylistItem>>, RestClientResult<List<AlbumItem>>, RestClientResult<HomePageContract.HomePageState>>(
                fetchFeaturedAlbumsUseCase.invoke()
            ) { playlistResponse, albumsResponse ->
                if (playlistResponse.isLoading() || albumsResponse.isLoading()) {
                    RestClientResult.loading()
                } else if (playlistResponse.isSuccess() && albumsResponse.isSuccess()) {
                    RestClientResult.success(
                        HomePageContract.HomePageState.Success(
                            playlists = playlistResponse.data!!,
                            albums = albumsResponse.data!!
                        )
                    )

                } else if (playlistResponse.isError()) {
                    RestClientResult.error(
                        errorMessage = playlistResponse.errorMessage.orEmpty(),
                        errorCode = playlistResponse.errorCode
                    )
                } else if (albumsResponse.isError()) {
                    RestClientResult.error(
                        errorMessage = albumsResponse.errorMessage.orEmpty(),
                        errorCode = albumsResponse.errorCode
                    )
                } else {
                    RestClientResult.idle()
                }
            }.collectLatest { homePageResponse ->
                homePageResponse
                    .onLoading {
                        setState {
                            copy(state = HomePageContract.HomePageState.Loading)
                        }
                    }
                    .onSuccess {
                        setState {
                            copy(state = it)
                        }
                    }
                    .onError { errorMessage, _ ->
                        setEffect {
                            HomePageContract.Effect.ShowToast(message = errorMessage)
                        }
                    }
            }
    }

    private fun createInitialState(): HomePageContract.State {
        return HomePageContract.State(
            HomePageContract.HomePageState.Loading
        )
    }

    private suspend fun handleEvent(event: HomePageContract.Event) {
        when (event) {
            HomePageContract.Event.OnFetchHomePageEvent -> {
                fetchHomePageData()
            }
        }
    }

    /**
     * Start listening to Event
     */
    private suspend fun subscribeEvents() {
        _event.collect {
            handleEvent(it)
        }
    }


    /**
     * Set new Event
     */
    suspend fun setEvent(event: HomePageContract.Event) {
        _event.emit(event)
    }


    /**
     * Set new Ui State
     */
    private suspend fun setState(reduce: HomePageContract.State.() -> HomePageContract.State) {
        val newState = currentState.reduce()
        _uiState.emit(newState)
    }

    /**
     * Set new Effect
     */
    private suspend fun setEffect(builder: () -> HomePageContract.Effect) {
        val effectValue = builder()
        _effect.emit(effectValue)
    }

    override fun onCleared() {
        super.onCleared()
    }
}