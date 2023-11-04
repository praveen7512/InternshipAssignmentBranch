package com.example.internshipassignmentbranch.di

import android.content.Context
import com.example.internshipassignmentbranch.api.ApiService
import com.example.internshipassignmentbranch.api.AuthInterceptor
import com.example.internshipassignmentbranch.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.API_BASE_URL) .client(okHttpClient)// Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiInstance(retrofit: Retrofit) =retrofit.create(ApiService::class.java)


    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }






}