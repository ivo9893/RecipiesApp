package com.example.myrecipies.presentation.ui.screens

import androidx.lifecycle.ViewModel
import com.example.myrecipies.data.api.UsersApi
import com.example.myrecipies.data.api.dto.LoginUserDto
import com.example.myrecipies.domain.model.ErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginViewState(
    val phoneSelected: Boolean = true,
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val rememberMe: Boolean = false,
    val loginSuccess: Boolean? = null,
    val errorMessage: ErrorResponse? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usersApi : UsersApi
) : ViewModel()  {

    private val _uiState = MutableStateFlow(LoginViewState())
    val uiState: StateFlow<LoginViewState> = _uiState

    fun onPhoneSelected(selected: Boolean) {
        _uiState.update {
            it.copy(phoneSelected = selected)
        }
    }

    fun onEmailChanged(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail)
        }
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword)
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(passwordVisible = !it.passwordVisible)
        }
    }

    fun onRememberMeChanged(remember: Boolean) {
        _uiState.update {
            it.copy(rememberMe = remember)
        }
    }

    fun setLoginSuccess(success: Boolean?) {
        _uiState.update {
            it.copy(loginSuccess = success)
        }
    }

    fun setErrorMessage(error: ErrorResponse?) {
        _uiState.update {
            it.copy(errorMessage = error)
        }
    }

    fun setLoading(loading: Boolean) {
        _uiState.update {
            it.copy(isLoading = loading)
        }
    }

    fun login(email : String, password: String){
        val user = LoginUserDto(email, password)
        CoroutineScope(Dispatchers.IO).launch {
            try{
                setLoading(true)
                val response = usersApi.loginUser(user)
                if(response.isSuccessful){
                    setLoginSuccess(true)
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