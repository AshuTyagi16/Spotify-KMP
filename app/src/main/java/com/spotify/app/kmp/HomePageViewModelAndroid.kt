package com.spotify.app.kmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotify.app.feature_homepage.shared.ui.HomePageViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

internal class HomePageViewModelAndroid : ViewModel(), KoinComponent {

    private val viewModel: HomePageViewModel by inject { parametersOf(viewModelScope) }

    fun getInstance() = viewModel

}