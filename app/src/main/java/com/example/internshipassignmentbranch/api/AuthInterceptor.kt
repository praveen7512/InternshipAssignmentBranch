package com.example.internshipassignmentbranch.api

import android.util.Log
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

        // Check if the token is not null before adding the header
        if (token != null) {
            request
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Branch-Auth-Token", "$token")
        }

        return chain.proceed(request.build())
    }
}
