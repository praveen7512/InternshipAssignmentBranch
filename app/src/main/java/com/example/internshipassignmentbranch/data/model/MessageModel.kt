package com.example.internshipassignmentbranch.data.model

data class MessagesModel(
    val id: Int,
    val thread_id: Int,
    val user_id: String,
    val body: String,
    val timestamp: String,
    val agentId: Int?
)