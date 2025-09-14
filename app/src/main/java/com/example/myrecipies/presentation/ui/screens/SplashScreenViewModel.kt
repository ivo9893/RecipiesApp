package com.example.myrecipies.presentation.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipies.data.repository.CategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import com.example.myrecipies.data.repository.UnitsRepository

sealed class SplashViewState {
    object Loading : SplashViewState()
    data class Success(val data: Boolean) : SplashViewState()
    data class Error(val message: String) : SplashViewState()
}


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
    private val unitsRepository: UnitsRepository
): ViewModel() {

    private val _state = mutableStateOf<SplashViewState>(SplashViewState.Loading)
    val state: State<SplashViewState> = _state

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.value = SplashViewState.Loading
            try {
                categoriesRepository.saveAllCategories()
                unitsRepository.saveAllUnits()
                _state.value = SplashViewState.Success(true)
            } catch (e: Exception) {
                _state.value = SplashViewState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}