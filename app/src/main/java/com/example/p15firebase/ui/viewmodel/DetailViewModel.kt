package com.example.p15firebase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p15firebase.model.Mahasiswa
import com.example.p15firebase.navigation.DestinasiDetail
import com.example.p15firebase.repository.RepositoryMhs
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailUiState {
    data class Success(val data: Mahasiswa) : DetailUiState()
    data class Error(val e: Throwable) : DetailUiState()
    object Loading : DetailUiState()
}

class DetailMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repoMhs: RepositoryMhs,
) : ViewModel() {

    // Mendapatkan NIM dari SavedStateHandle
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.nimArg])

    var mhsDetailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    init {
        getMhsbyId()
    }

    fun getMhsbyId() {
        viewModelScope.launch {
            repoMhs.getMhs(_nim).onStart {
                mhsDetailUiState = DetailUiState.Loading
            }
                .catch {
                    mhsDetailUiState = DetailUiState.Error(e = it)
                }
                .collect {
                    mhsDetailUiState = if (it.nim.isEmpty()) {
                        DetailUiState.Error(Exception("Belum ada data mahasiswa"))
                    }
                    else {
                        DetailUiState.Success(data = it)
                    }
                }
        }
    }
    fun getNim(): String = _nim
}