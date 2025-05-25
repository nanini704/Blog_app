package com.example.blog_app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blog_app.model.data.BlogPost
import com.example.blog_app.model.network.BlogRepository
import com.example.blog_app.model.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class BlogViewModel(private val repository: BlogRepository): ViewModel() {
    private val _posts = MutableStateFlow<List<BlogPost>>(emptyList())
    val posts: StateFlow<List<BlogPost>> = _posts

    init {
        fetchPosts()
    }

    fun fetchPosts(page: Int = 1) {
        viewModelScope.launch {
            try {
                val response = repository.getPosts(page = page)
                Log.d("BlogViewModel", "Fetched posts count: ${response.size}")
                _posts.value = _posts.value.plus(response)
            } catch (e: Exception) {
                Log.e("BlogViewModel", "Error fetching posts", e)
            }
        }
    }
}