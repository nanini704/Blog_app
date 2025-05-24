package com.example.blog_app.view.bloglist
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.blog_app.viewmodel.BlogViewModel


@Composable
fun BlogListScreen(navController: NavController, viewModel: BlogViewModel = viewModel()) {
    val posts by viewModel.posts.collectAsState()

    LazyColumn {
        items(posts) { post ->
           Text(
               text = post.title.rendered,
               modifier = Modifier
                   .fillMaxWidth()
                   .clickable {
                       navController.navigate("detail/${Uri.encode(post.link)}")
                   }
                   .padding(16.dp)
           )
        }
    }
}
