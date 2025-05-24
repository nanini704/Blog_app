package com.example.blog_app.model.data

data class BlogPost(
    val id: Int,
    val excerpt: Rendered,
    val link: String
)


data class Rendered(
    val rendered: String
)
