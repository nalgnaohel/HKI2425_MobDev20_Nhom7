package com.example.harmonyhub.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.harmonyhub.HarmonyHubApplication
import com.example.harmonyhub.data.network.APIService
import com.example.harmonyhub.data.repository.HomeScreenRepo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeScreenRepo : HomeScreenRepo
) : ViewModel() {
    private val _state = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val state = _state.asStateFlow()

    init {
        fetchHomePageData()
    }

    fun fetchHomePageData() {
        viewModelScope.launch {
            _state.value = HomeUIState.Loading
            val result = homeScreenRepo.updatePopularItem()

            _state.value = result?.let {
                HomeUIState.Success(it)
            } ?: HomeUIState.Error

        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as HarmonyHubApplication)
                val homeRepository = application.container
                HomeViewModel(homeScreenRepo = homeRepository)
            }
        }
    }
}
