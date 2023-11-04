package com.example.internshipassignmentbranch.data.network

import com.example.internshipassignmentbranch.data.model.Post
import retrofit2.http.GET

interface ApiService {

    @GET("posts") // Replace "posts" with the actual endpoint URL
    suspend fun getPosts(): List<Post>


}