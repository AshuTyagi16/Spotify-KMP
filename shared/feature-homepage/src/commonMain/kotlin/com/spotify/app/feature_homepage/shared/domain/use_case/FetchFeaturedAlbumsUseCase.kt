package com.spotify.app.feature_homepage.shared.domain.use_case

import com.spotify.app.core_network.shared.impl.data.model.RestClientResult
import com.spotify.app.core_base.shared.domain.model.AlbumItem
import kotlinx.coroutines.flow.Flow

@Suppress("FUN_INTERFACE_WITH_SUSPEND_FUNCTION") // TODO: Remove once KTIJ-7642 is fixed
fun interface FetchFeaturedAlbumsUseCase: suspend () ->Flow<RestClientResult<List<AlbumItem>>>