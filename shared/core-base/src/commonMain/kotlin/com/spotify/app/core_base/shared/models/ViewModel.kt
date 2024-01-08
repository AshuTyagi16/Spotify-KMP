package com.spotify.app.core_base.shared.models

expect abstract class ViewModel() {
    protected open fun onCleared()
}