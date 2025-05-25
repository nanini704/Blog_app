package com.example.blog_app.model.network

import com.example.blog_app.model.data.BlogPost

class BlogRepository(private val api: BlogApiService) {
    suspend fun getPosts(page: Int, perPage: Int = 10): List<BlogPost> {
        return api.getPosts(page = page, perPage = 10)
    }
}
