package com.example.blog_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blog_app.model.data.BlogPost
import com.example.blog_app.model.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class BlogViewModel: ViewModel() {
    private val _posts = MutableStateFlow<List<BlogPost>>(emptyList())
    val posts: StateFlow<List<BlogPost>> = _posts

    init {
        fetchPosts()
    }

    fun fetchPosts(page: Int = 1) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getPosts(page = page)
                _posts.value = response
            } catch (e: Exception) {
                // handle error
            }
        }
    }
}