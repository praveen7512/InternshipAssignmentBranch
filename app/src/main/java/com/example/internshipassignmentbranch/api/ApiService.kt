package com.example.internshipassignmentbranch.api

import com.example.internshipassignmentbranch.data.model.LoginRequest
import com.example.internshipassignmentbranch.data.model.MessagesModel
import com.example.internshipassignmentbranch.data.model.ReplyMsg
import com.example.internshipassignmentbranch.data.model.TokenInfo
import com.example.internshipassignmentbranch.utils.AppConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET(AppConstants.MESSAGES_URL)
    suspend fun getMessages() : Response<MessagesModel>

    @POST(AppConstants.LOGIN_URL)
    suspend fun login(@Body loginDetails: LoginRequest) : Response<TokenInfo>

    @POST(AppConstants.MESSAGES_URL)
    suspend fun replyMessage(@Body reply : ReplyMsg) : Response<MessagesModel>





}