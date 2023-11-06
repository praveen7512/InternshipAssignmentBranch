package com.example.internshipassignmentbranch.di

import com.example.internshipassignmentbranch.api.ApiService
import com.example.internshipassignmentbranch.data.repository.MessageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMessageRepository(apiService: ApiService): MessageRepository {
        return MessageRepository(apiService)
    }

}