package com.spotify.app.core_base.shared.models

actual abstract class ViewModel actual constructor() {
    protected actual open fun onCleared() {
    }

    fun clear() {
        onCleared()
    }
}