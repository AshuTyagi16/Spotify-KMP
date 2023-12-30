package com.spotify.app.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform