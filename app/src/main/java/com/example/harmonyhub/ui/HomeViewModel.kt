package com.example.harmonyhub.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harmonyhub.data.APIService
import com.example.harmonyhub.data.APIService.getHomePageOverview
import com.example.harmonyhub.data.HomeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeUIState())
    val state: StateFlow<HomeUIState> = _state.asStateFlow()

    init {
        fetchHomePageData()
    }

    fun fetchHomePageData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                // Gọi API
                val api = APIService
                api.getHomePageOverview()
                Log.d("HomeVMScreen", "Popular Artists: ${api.listPopularArtist?.size}")
                // Cập nhật state với dữ liệu từ API
                _state.value = _state.value.copy(
                    listPopularArtist = api.listPopularArtist,
                    listPopularAlbums = api.listPopularAlbums,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
}
