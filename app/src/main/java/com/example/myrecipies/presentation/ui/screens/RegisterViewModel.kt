package com.example.myrecipies.presentation.ui.screens

import androidx.lifecycle.ViewModel
import com.example.myrecipies.data.api.UsersApi
import com.example.myrecipies.data.api.dto.CreateUserDto
import com.example.myrecipies.domain.model.ErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class RegisterViewState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val repeatPassword: String = "",
    val repeatPasswordVisible: Boolean = false,

    val registrationSuccess: Boolean? = null,
    val errorMessage: ErrorResponse? = null,
    val isLoading: Boolean = false,

    val firstNameEmpty: Boolean = false,
    val lastNameEmpty: Boolean = false,
    val emailEmpty: Boolean = false,
    val passwordEmpty: Boolean = false,
    val confirmPasswordEmpty: Boolean = false,
    val passwordMatch: Boolean = true
)


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val usersApi: UsersApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterViewState())
    val uiState: StateFlow<RegisterViewState> = _uiState

    fun onFirstNameChanged(newValue: String) {
        _uiState.update { it.copy(firstName = newValue, firstNameEmpty = newValue.isBlank()) }
    }

    fun onLastNameChanged(newValue: String) {
        _uiState.update { it.copy(lastName = newValue, lastNameEmpty = newValue.isBlank()) }
    }

    fun onEmailChanged(newValue: String) {
        _uiState.update { it.copy(email = newValue, emailEmpty = newValue.isBlank()) }
    }

    fun onPasswordChanged(newValue: String) {
        _uiState.update {
            it.copy(
                password = newValue,
                passwordEmpty = newValue.isBlank(),
                passwordMatch = newValue == it.repeatPassword
            )
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun onRepeatPasswordChanged(newValue: String) {
        _uiState.update {
            it.copy(
                repeatPassword = newValue,
                confirmPasswordEmpty = newValue.isBlank(),
                passwordMatch = newValue == it.password
            )
        }
    }

    fun toggleRepeatPasswordVisibility() {
        _uiState.update { it.copy(repeatPasswordVisible = !it.repeatPasswordVisible) }
    }

    fun setRegistrationSuccess(success: Boolean?) {
        _uiState.update { it.copy(registrationSuccess = success) }
    }

    fun setErrorMessage(error: ErrorResponse?) {
        _uiState.update { it.copy(errorMessage = error) }
    }

    fun setLoading(loading: Boolean) {
        _uiState.update { it.copy(isLoading = loading) }
    }

    fun registerUser(email: String, password: String, firstName: String, lastName: String){
        val userDto = CreateUserDto(email, password, firstName, lastName)

        CoroutineScope(Dispatchers.IO).launch {
            try{
                setLoading(true)
                val response = usersApi.registerUser(userDto)
                if(response.isSuccessful){
                    setRegistrationSuccess(true)
                } else {
                    val errorResponse = response.errorBody()?.string()
                    setErrorMessage(ErrorResponse(1, errorResponse))
                }
            } catch (e : Exception){
                e.printStackTrace()
            } finally {
                setLoading(false)
            }
        }
    }
}