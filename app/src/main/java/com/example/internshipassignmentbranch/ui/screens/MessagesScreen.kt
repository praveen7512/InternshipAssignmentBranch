package com.example.internshipassignmentbranch.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.internshipassignmentbranch.data.model.MessagesModel
import com.example.internshipassignmentbranch.ui.viewmodel.MessageViewModel
import com.example.internshipassignmentbranch.utils.NetworkResult
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import com.example.internshipassignmentbranch.navigation.Destinations
import com.example.internshipassignmentbranch.ui.viewmodel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(onClick: () -> Unit, sharedViewModel: SharedViewModel,navController: NavController) {

    val messageViewModel: MessageViewModel = hiltViewModel()
    // Observe the messages from the ViewModel
    val messages by messageViewModel.messages.observeAsState(NetworkResult.Loading())

    // Fetch messages when the screen is first displayed
    LaunchedEffect(Unit) {
        messageViewModel.fetchMessages()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                {
                    Text(
                        text = "Messages",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    text = "Logout",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Destinations.LoginScreen.route)
                        }
                )
            }
        }

    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(it)
        ) {
            when (messages) {
                is NetworkResult.Loading<*> -> {
                    item {
                        // Display a loading indicator
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LinearProgressBarCentered()
                        }
                    }
                }

                is NetworkResult.Success<*> -> {
                    val messageList: List<MessagesModel>? = messages.data as? List<MessagesModel>
                    if (messageList != null) {
                        // Sort the messages by the newest first
                        val sortedMessageList = messageList.sortedByDescending {
                            SimpleDateFormat(
                                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                Locale.US
                            ).parse(it.timestamp)
                        }
                        items(sortedMessageList) { message ->
                            // Display individual message items
                            MessageItem(message = message, onClick, sharedViewModel)
                        }
                    } else {
                        // Handle the case when the messageList is null
                    }
                }

                is NetworkResult.Error<*> -> {
                    item {
                        // Handle the error case
                        val error = (messages as NetworkResult.Error<*>).message.toString()
                        Text(text = "Error: $error")
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageItem(message: MessagesModel, onClick: () -> Unit, sharedViewModel: SharedViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            sharedViewModel.setMessageDetail(message)
            onClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = message.user_id ?: "Unknown User",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            val messageBody = message.body ?: "Unknown User"
            val maxLines = 2 // Adjust the number of lines to show

            Text(
                text = "Msg :" + buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        val ellipsis = if (messageBody.countLines() > maxLines) "..." else ""
                        val truncatedText = messageBody.lines().take(maxLines).joinToString("\n")
                        append("$truncatedText$ellipsis")
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3
            )

            Text(
                text = "Date :" + message.timestamp.substring(0, 10) ?: "Unknown User",
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = "ThreadId :" + message.thread_id ?: "Unknown User",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

fun String.countLines(): Int {
    return split('\n').size
}

@Composable
fun LinearProgressBarCentered() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary,
        )
    }
}
