package com.example.internshipassignmentbranch.api

import com.example.internshipassignmentbranch.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val token = tokenManager.getToken()
        request.addHeader("Content-Type","application/json").
        addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}
