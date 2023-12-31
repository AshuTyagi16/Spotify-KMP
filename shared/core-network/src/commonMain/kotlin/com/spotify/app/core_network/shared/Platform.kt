package com.spotify.app.core_network.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform