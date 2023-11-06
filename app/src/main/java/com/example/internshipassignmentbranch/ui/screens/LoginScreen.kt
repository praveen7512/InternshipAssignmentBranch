package com.example.internshipassignmentbranch.ui.screens

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.internshipassignmentbranch.data.model.LoginRequest
import com.example.internshipassignmentbranch.data.model.TokenInfo
import com.example.internshipassignmentbranch.ui.viewmodel.MessageViewModel
import com.example.internshipassignmentbranch.utils.NetworkResult
import com.example.internshipassignmentbranch.utils.TokenManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onClick: () -> Unit, tokenManager: TokenManager) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val messageViewModel: MessageViewModel = hiltViewModel()
    val loginResult by messageViewModel.loginResult.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Enter Your Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                messageViewModel.performLogin(LoginRequest(username, password))
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Login")
        }

        when (loginResult) {
            is NetworkResult.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResult.Success -> {
                val tokenInfo = (loginResult as NetworkResult.Success<TokenInfo>).data
                if (tokenInfo != null) {
                    val token = tokenInfo.auth_token
                    tokenManager.saveToken(token) // Save the token to SharedPreferences
                }
                onClick()
            }

            is NetworkResult.Error -> {
                val error = (loginResult as NetworkResult.Error).message
                Text("Error: $error")
            }

            else -> Text(text = "Click On The Button To Procced")
        }
    }
}

