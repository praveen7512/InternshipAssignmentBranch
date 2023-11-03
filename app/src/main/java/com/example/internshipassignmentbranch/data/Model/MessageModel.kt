package com.example.internshipassignmentbranch.data.Model

data class MessagesModel(
    val id: Int,
    val threadId: Int,
    val userId: String,
    val body: String,
    val timestamp: String,
    val agentId: Int?
)