package com.example.internshipassignmentbranch.data.repository

import android.util.Log
import com.example.internshipassignmentbranch.api.ApiService
import com.example.internshipassignmentbranch.data.model.LoginRequest
import com.example.internshipassignmentbranch.data.model.MessagesModel
import com.example.internshipassignmentbranch.data.model.ReplyMsg
import com.example.internshipassignmentbranch.data.model.TokenInfo
import com.example.internshipassignmentbranch.utils.NetworkResult
import javax.inject.Inject

class MessageRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getMessages(): NetworkResult<List<MessagesModel>> {
        return try {
            val response = apiService.getMessages()
            if (response.isSuccessful) {
                NetworkResult.Success(
                    response.body() ?: emptyList()
                )
            } else {
                val errorMessage = response.code().toString() ?: "Unknown error"
                NetworkResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = e.message ?: "An error occurred"
            NetworkResult.Error(errorMessage)
        }
    }

    suspend fun login(loginDetails: LoginRequest): NetworkResult<TokenInfo> {
        return try {
            val response = apiService.login(loginDetails)
            if (response.isSuccessful) {
                NetworkResult.Success(response.body() ?: TokenInfo(""))
            } else {
                NetworkResult.Error(response.code().toString())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun replyMessage(reply: ReplyMsg): NetworkResult<MessagesModel> {
        return try {
            val response = apiService.replyMessage(reply)
            if (response.isSuccessful) {
                NetworkResult.Success(
                    response.body() ?: MessagesModel(
                        id = 0,
                        thread_id = 0,
                        user_id = "",
                        body = "",
                        timestamp = "",
                        agentId = null
                    )
                )
            } else {
                NetworkResult.Error(response.message())
            }

        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An error occurred")
        }
    }
}