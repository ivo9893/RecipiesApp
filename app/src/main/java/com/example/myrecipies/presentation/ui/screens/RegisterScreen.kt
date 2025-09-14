package com.example.myrecipies.presentation.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RegisterLayoutPreview() {
    val previewState = RegisterViewState(
        firstName = "Ivaylo",
        lastName = "Stoyanov",
        email = "william.henry.harrison@example-pet-store.com",
        password = "password"
    )

    RegisterLayoutParams(
        viewState = previewState,
        onFirstNameChanged = {},
        onLastNameChanged = {},
        onEmailChanged = {},
        onPasswordChanged = {},
        onRepeatPasswordChanged = {},
        onTogglePasswordVisibility = {},
        onToggleRepeatPasswordVisibility = {},
        onRegisterClick = {},
        navigateToLogin = {}
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel, navigateToLogin: () -> Unit) {
    // Collect the StateFlow as Compose state
    val state by viewModel.uiState.collectAsState()

    RegisterLayoutParams(
        viewState = state,
        onFirstNameChanged = viewModel::onFirstNameChanged,
        onLastNameChanged = viewModel::onLastNameChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onRepeatPasswordChanged = viewModel::onRepeatPasswordChanged,
        onTogglePasswordVisibility = viewModel::togglePasswordVisibility,
        onToggleRepeatPasswordVisibility = viewModel::toggleRepeatPasswordVisibility,
        onRegisterClick = {
            viewModel.registerUser (
                email = state.email,
                password = state.password,
                firstName =  state.firstName,
                lastName = state.lastName
            )
        },
        navigateToLogin = navigateToLogin
    )
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterLayoutParams(
    viewState: RegisterViewState,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRepeatPasswordChanged: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onToggleRepeatPasswordVisibility: () -> Unit,
    onRegisterClick: () -> Unit,
    navigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Get started now",
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Create an account or log in to explore about our app",
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 12.sp,
            color = Color.Gray
        )

        // Sign Up / Log In toggle
        Row(
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(50))
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFD4FF4A))
                    .clickable { /* stay on Sign up */ }
            ) {
                Text(
                    text = "Sign up",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(Color.Transparent)
                    .clickable { navigateToLogin() }
            ) {
                Text(
                    text = "Log in",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // First + Last Name
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // First Name
            Column(modifier = Modifier.weight(1f).padding(4.dp)) {
                Text(
                    text = "First Name",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewState.firstName,
                    onValueChange = onFirstNameChanged,
                    shape = RoundedCornerShape(50.dp),
                    isError = viewState.firstNameEmpty,
                    supportingText = {
                        if (viewState.firstNameEmpty) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "First name is empty",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    trailingIcon = {
                        if (viewState.firstNameEmpty)
                            Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                    }
                )
            }

            // Last Name
            Column(modifier = Modifier.weight(1f).padding(4.dp)) {
                Text(
                    text = "Last Name",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewState.lastName,
                    onValueChange = onLastNameChanged,
                    shape = RoundedCornerShape(50.dp),
                    isError = viewState.lastNameEmpty,
                    supportingText = {
                        if (viewState.lastNameEmpty) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Last name is empty",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    trailingIcon = {
                        if (viewState.lastNameEmpty)
                            Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                    }
                )
            }
        }

        // Email
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewState.email,
                    onValueChange = onEmailChanged,
                    shape = RoundedCornerShape(50.dp),
                    isError = viewState.emailEmpty,
                    supportingText = {
                        if (viewState.emailEmpty) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Email is empty",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    trailingIcon = {
                        if (viewState.emailEmpty)
                            Icon(
                                Icons.Filled.Error,
                                "error",
                                tint = MaterialTheme.colorScheme.error
                            )
                    }
                )
            }

        // Password
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewState.password,
                    onValueChange = onPasswordChanged,
                    shape = RoundedCornerShape(50.dp),
                    visualTransformation =
                        if (viewState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = viewState.passwordEmpty || !viewState.passwordMatch,
                    supportingText = {
                        if (viewState.passwordEmpty) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Password is empty",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    trailingIcon = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = onTogglePasswordVisibility) {
                                Icon(
                                    imageVector = if (viewState.passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (viewState.passwordVisible) "Hide password" else "Show password"
                                )
                            }
                            if (viewState.passwordEmpty || !viewState.passwordMatch)
                                Icon(
                                    Icons.Filled.Error,
                                    "error",
                                    tint = MaterialTheme.colorScheme.error
                                )
                        }
                    }
                )
            }
        // Repeat Password
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = "Repeat password",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewState.repeatPassword,
                onValueChange = onRepeatPasswordChanged,
                shape = RoundedCornerShape(50.dp),
                visualTransformation =
                    if (viewState.repeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = viewState.confirmPasswordEmpty || !viewState.passwordMatch,
                supportingText = {
                    if (viewState.confirmPasswordEmpty) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Repeat password is empty",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    if (!viewState.passwordMatch) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Passwords don't match",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onToggleRepeatPasswordVisibility) {
                            Icon(
                                imageVector = if (viewState.repeatPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (viewState.repeatPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                        if (viewState.confirmPasswordEmpty || !viewState.passwordMatch)
                            Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }

        // Register button
        Row(modifier = Modifier.padding(16.dp).height(50.dp)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFD4FF4A))
                    .clickable { onRegisterClick() }
            ) {
                Text(
                    text = "Sign up",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Loading
        if (viewState.isLoading) {
            Dialog(onDismissRequest = {}) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Registering...")
                    }
                }
            }
        }

        LaunchedEffect(viewState.registrationSuccess) {
            if (viewState.registrationSuccess == true) {
                navigateToLogin()
            }
        }
    }
}

