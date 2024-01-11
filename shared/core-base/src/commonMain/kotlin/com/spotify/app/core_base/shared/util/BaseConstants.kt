package com.spotify.app.core_base.shared.util

import kotlin.time.Duration.Companion.hours

object BaseConstants {

    val CACHE_EXPIRE_TIME = 6.hours

    const val DEFAULT_PAGE_SIZE = 10L
    const val DEFAULT_OFFSET = 0L
    
    const val SHIMMER_ALPHA = 0.2f

    const val LOADING_ERROR_PLACEHOLDER = "https://imgur.com/UWROM7r.png"
}