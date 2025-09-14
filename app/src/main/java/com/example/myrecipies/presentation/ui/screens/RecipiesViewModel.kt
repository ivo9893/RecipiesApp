package com.example.myrecipies.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipies.domain.model.Recipe
import com.example.myrecipies.data.repository.RecipiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class RecipesViewState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class RecipiesViewModel @Inject constructor(
    private val repository: RecipiesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipesViewState(isLoading = true))
    val uiState: StateFlow<RecipesViewState> = _uiState

    init {
        getRecipes()
    }

    private fun getRecipes() {
        viewModelScope.launch {
            try {
                val recipes = repository.getAllRecipes()
                _uiState.value = _uiState.value.copy(
                    recipes = recipes,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error"
                )
            }
        }
    }
}