package com.example.demodowloadmusic

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.harmonyhub.MyApplication
import com.example.harmonyhub.data.network.ResponseSplit
import com.example.harmonyhub.data.repository.HomeScreenRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplitViewModel (
    private val splitRepo : HomeScreenRepo
) : ViewModel(){
    private val _stateMusic1 = MutableStateFlow<SplitUiState>(SplitUiState.Default)
    val stateMusic1 = _stateMusic1.asStateFlow()

    private val _stateMusic2 = MutableStateFlow<SplitUiState>(SplitUiState.Default)
    val stateMusic2 = _stateMusic2.asStateFlow()

    private val _split1 = MutableStateFlow<ResponseSplit>(ResponseSplit("","","","")) // Giá trị mặc định
    val split1 = _split1.asStateFlow()

    private val _split2 = MutableStateFlow<ResponseSplit>(ResponseSplit("","","","")) // Giá trị mặc định
    val split2 = _split2.asStateFlow()

    fun splitMusic1(filePath: String) {
        viewModelScope.launch (Dispatchers.IO){
            _stateMusic1.value = SplitUiState.Loading
            Thread.sleep(30000)
            _stateMusic1.value = SplitUiState.Success

            //val result = splitRepo.splitMusic(filePath)
            //_stateMusic1.value = result?.let {
            //    _split1.value = result
            //    SplitUiState.Success
            // ?: SplitUiState.Error
            Log.d("splitstate", "${_stateMusic1.value}")

        }
    }

    fun splitMusic2(filePath: String) {
        viewModelScope.launch (Dispatchers.IO){
            _stateMusic2.value = SplitUiState.Loading
            Thread.sleep(30000)
            _stateMusic2.value = SplitUiState.Success

            //  val result = splitRepo.splitMusic(filePath)
           // _stateMusic2.value = result?.let {
          //      _split2.value = result
          //      SplitUiState.Success
         //   } ?: SplitUiState.Error
            Log.d("splitstate", "${_stateMusic2.value}")

        }
    }

    companion object {
        val Factory2: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MyApplication)
                val splitRepo = application.container
                SplitViewModel(splitRepo = splitRepo)
            }
        }
    }

}