package com.example.demodowloadmusic

sealed interface SplitUiState {
    object Default : SplitUiState
    object Loading : SplitUiState
    object Error : SplitUiState
    object Success : SplitUiState

}