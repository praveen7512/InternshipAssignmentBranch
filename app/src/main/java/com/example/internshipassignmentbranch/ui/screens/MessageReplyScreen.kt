package com.example.internshipassignmentbranch.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.internshipassignmentbranch.data.model.ReplyMsg
import com.example.internshipassignmentbranch.ui.viewmodel.MessageViewModel
import com.example.internshipassignmentbranch.ui.viewmodel.SharedViewModel
import com.example.internshipassignmentbranch.utils.NetworkResult


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyScreen(sharedViewModel: SharedViewModel, onBackPressed: () -> Unit) {
    val messagesDetails = sharedViewModel.chatDetails
    val threadId = messagesDetails!!.thread_id
    val userId = messagesDetails!!.user_id
    val userMessage = messagesDetails!!.body
    var agentMessage by remember { mutableStateOf("") }
    val messageViewModel: MessageViewModel = hiltViewModel()
    val replyMessages by messageViewModel.replyResult.observeAsState(NetworkResult.Loading())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Back") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // First Container with Border
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Thread ID:", fontWeight = FontWeight.Bold, color = Color.Black)
                        Text(text = "$threadId", color = Color.Black)
                        Text(text = "User ID:", fontWeight = FontWeight.Bold, color = Color.Black)
                        Text(text = "$userId", color = Color.Black)
                    }
                }

                // Second Container with Border
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "User Message:",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(text = userMessage, color = Color.Black)
                        Text(
                            text = "Agent Id:" + "${replyMessages.data?.agentId ?: "Unknown"}",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Agent Message:",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )


                        when (replyMessages) {
                            is NetworkResult.Loading<*> -> {

                            }

                            is NetworkResult.Success<*> -> {
                                Text(
                                    text = replyMessages.data!!.body.toString(),
                                    color = Color.Black
                                )
                            }

                            is NetworkResult.Error<*> -> {
                                Text(text = "Please Try Again", color = Color.Red)
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = agentMessage,
                    onValueChange = { agentMessage = it },
                    placeholder = { Text("Agent's Reply") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        messageViewModel.replyToMessage(ReplyMsg(threadId, agentMessage))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Reply")
                }
            }
        }
    )
}


