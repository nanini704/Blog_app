package com.example.blog_app.model.network

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.blog_app.model.data.BlogPost


interface BlogApiService {
    @GET("wp-json/wp/v2/posts")
    suspend fun getPosts(
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1
    ): List<BlogPost>
}