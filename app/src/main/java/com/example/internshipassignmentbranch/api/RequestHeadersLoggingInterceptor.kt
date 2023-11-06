package com.example.internshipassignmentbranch.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestHeadersLoggingInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val headers = request.headers()
        for (i in 0 until headers.size()) {
            val name = headers.name(i)
            val value = headers.value(i)
            Log.d("RequestHeadersLogging", "$name: $value")
        }
        return chain.proceed(request)
    }
}