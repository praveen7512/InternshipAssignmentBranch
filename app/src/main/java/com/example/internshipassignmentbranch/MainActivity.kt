package com.example.internshipassignmentbranch

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.internshipassignmentbranch.api.ApiService
import com.example.internshipassignmentbranch.data.model.LoginRequest
//import com.example.internshipassignmentbranch.screens.LoginScreen
import com.example.internshipassignmentbranch.ui.theme.InternshipAssignmentBranchTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


   @Inject
   lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InternshipAssignmentBranchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   LaunchedEffect(true){
                       try {
                           val token =apiService.login(LoginRequest("praveen.sahu7512@gmail.com","moc.liamg@2157uhas.neevarp")).body().toString()
                           Log.d("praveen",token )
                       } catch (e: Exception) {
                           Log.d("errorhai", e.toString())
                       }
                   }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InternshipAssignmentBranchTheme {
        Greeting("Android")
    }
}