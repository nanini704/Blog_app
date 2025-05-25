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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private var currentPage = 1
    private var endReached = false


    init {
        fetchPosts()
    }

    fun fetchPosts(page: Int = 1) {
        if (_isLoading.value || endReached) return
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.getPosts(page = page)
                Log.d("BlogViewModel", "Fetched posts count: ${response.size}")
                if (response.isNotEmpty()) {
                    if (page == 1) {
                        _posts.value = response
                    } else {
                        _posts.value = _posts.value + response
                    }
                    currentPage = page
                } else {
                    endReached = true
                }
            } catch (e: Exception) {
                Log.e("BlogViewModel", "Error fetching posts", e)
                _errorMessage.value = "Failed to load posts: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadNextPage() {
        if (_isLoading.value || endReached) return
        fetchPosts(currentPage + 1)
    }
}