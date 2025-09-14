package com.example.myrecipies.presentation.ui.screens

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipies.data.repository.RecipiesRepository
import com.example.myrecipies.data.repository.UnitsRepository
import com.example.myrecipies.domain.model.Ingredient
import com.example.myrecipies.domain.model.Unit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateRecipeState(
    val name : String = "",
    val description : String = "",
    val cookTimeMinutes : Int = 0,
    val prepTimeMinutes : Int = 0,
    val servings : Int = 0,
    val instructions : String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val showDialog: Boolean = false,
    val availableUnits : List<Unit> = emptyList(),
    val coverImage : Uri? = null,
    var selectedImages : List<Uri> = emptyList()
)

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val unitsRepository: UnitsRepository) : ViewModel(){

    private val _uiState = MutableStateFlow(CreateRecipeState())
    val uiState: StateFlow<CreateRecipeState> = _uiState


    init {
        getUnits()
    }

    fun onNameChanged(name : String){
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onDescriptionChanged(description : String){
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onCookTimeMinutesChanged(cookTimeMinutes : Int){
        _uiState.value = _uiState.value.copy(cookTimeMinutes = cookTimeMinutes)
    }

    fun onPrepTimeMinutesChanged(prepTimeMinutes : Int){
        _uiState.value = _uiState.value.copy(prepTimeMinutes = prepTimeMinutes)
    }

    fun onServingsChanged(servings : Int){
        _uiState.value = _uiState.value.copy(servings = servings)
    }

    fun onInstructionsChanged(instructions : String){
        _uiState.value = _uiState.value.copy(instructions = instructions)
    }

    fun addIngredient(name: String, qty: String, unit: Int) {
        val updatedList = _uiState.value.ingredients + Ingredient(name, qty, unit)
        _uiState.value = _uiState.value.copy(ingredients = updatedList)
    }

    fun removeIngredient(ingredient: Ingredient) {
        val updatedList = _uiState.value.ingredients - ingredient
        _uiState.value = _uiState.value.copy(ingredients = updatedList)
    }

    fun setDialogVisible(visible: Boolean) {
        _uiState.value = _uiState.value.copy(showDialog = visible)
    }

    fun setCoverImage(uri: Uri) {
        _uiState.value = _uiState.value.copy(coverImage = uri)
    }

    fun setSelectedImages(uris: List<Uri>) {
        _uiState.value = _uiState.value.copy(selectedImages = uris)
    }

    private fun getUnits() {
        viewModelScope.launch {
            try {
                val units = unitsRepository.getAllUnits()
                _uiState.value = _uiState.value.copy(
                    availableUnits = units
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
