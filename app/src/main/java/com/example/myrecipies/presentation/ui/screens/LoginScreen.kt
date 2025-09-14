package com.example.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrecipies.R
import com.example.myrecipies.data.api.dto.LoginUserDto
import com.example.myrecipies.presentation.ui.screens.LoginViewModel
import com.example.myrecipies.presentation.ui.screens.LoginViewState


@Composable
fun LoginLayout(viewModel: LoginViewModel, navigateToRegister: () -> Unit, navigateToRecipies: () -> Unit) {
    val state = viewModel.uiState.collectAsState().value

    LoginLayout(
        state = state,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onTogglePasswordVisibility = viewModel::togglePasswordVisibility,
        onRememberMeChanged = viewModel::onRememberMeChanged,
        onLoginClick = {
             viewModel.login(email = state.email, password = state.password)
        },
        navigateToRegister = navigateToRegister,
        navigateToRecipies = navigateToRecipies
    )
}

@Composable
fun LoginLayout(
    state: LoginViewState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onRememberMeChanged: (Boolean) -> Unit,
    onLoginClick: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToRecipies: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
    ) {
        Text(
            text = "Welcome Back",
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Login to access your account",
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 12.sp,
            color = Color.Gray
        )

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
                    .clickable { navigateToRegister() }
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
                    .background(Color(0xFFD4FF4A))
                    .clickable { onLoginClick() }
            ) {
                Text(
                    text = "Log in",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Row(modifier = Modifier.padding(16.dp)) {
            Column {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = onEmailChanged,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            Column {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = onPasswordChanged,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50.dp),
                    visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (state.passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description = if (state.passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { onTogglePasswordVisibility() }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = state.rememberMe,
                    onCheckedChange = onRememberMeChanged
                )

                Text(
                    text = "Remember me",
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp)
                ) {
                    Text(
                        text = "Forgot password ?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Row (modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 16.dp)
            .height(50.dp)){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFD4FF4A))
                    .clickable {
                        onLoginClick()
                    }
            ) {
                Text(
                    text = "Log in",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            LaunchedEffect(state.loginSuccess){
                if(state.loginSuccess == true){
                    navigateToRecipies()
                }
            }
        }

        Text(
            text = "Or login with",
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 12.sp,
            color = Color.Gray

        )

        Row(modifier = Modifier.height(66.dp)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ){
            Button(onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50)),
                colors = ButtonDefaults.buttonColors(Color(0xFFF7F6FB))
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Image(painterResource(R.drawable.googlelogo),
                        contentDescription = "Google")

                    Text(
                        text = "Google",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Button(onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50)),
                colors = ButtonDefaults.buttonColors(Color(0xFFF7F6FB))
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Image(painterResource(R.drawable.facebooklogo),
                        contentDescription = "Facebook")

                    Text(
                        text = "Facebook",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Row(modifier = Modifier
            .padding(top = 10.dp)
            .align(Alignment.CenterHorizontally)){
            Text(
                text = "Don't have an account? ",
                fontSize = 12.sp,
                color = Color.Gray


            )
            Text(
                text = "Sign up",
                fontSize = 12.sp,
                color = Color(0xff64B5F6),
                modifier = Modifier.padding(start = 5.dp)
                    .clickable {
                        navigateToRegister()
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){

    val previewState = LoginViewState(
        phoneSelected = false,
        email = "ivo_stoqnov@mail.bg",
        password = "password",
        passwordVisible = false,
        rememberMe = false,
        loginSuccess = null,
        errorMessage = null,
        isLoading = false
    )

    LoginLayout(
        state = previewState,
        onEmailChanged = {},
        onPasswordChanged = {},
        onTogglePasswordVisibility = {},
        onRememberMeChanged = {},
        onLoginClick = {},
        navigateToRegister = {},
        navigateToRecipies = {}
    )
}
