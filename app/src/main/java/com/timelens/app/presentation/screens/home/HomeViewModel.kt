package com.timelens.app.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timelens.app.domain.model.DaySummary
import com.timelens.app.domain.repository.UsageRepository
import com.timelens.app.domain.usecase.GetDailySummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val summary: DaySummary) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
    object MissingPermission : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailySummaryUseCase: GetDailySummaryUseCase,
    private val checkUsagePermissionUseCase: CheckUsagePermissionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                if (!checkUsagePermissionUseCase()) {
                    _uiState.value = HomeUiState.MissingPermission
                    return@launch
                }
                val summary = getDailySummaryUseCase()
                _uiState.value = HomeUiState.Success(summary)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
