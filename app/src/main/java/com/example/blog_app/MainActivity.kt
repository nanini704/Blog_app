package com.example.blog_app

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import com.example.blog_app.model.network.BlogRepository
import com.example.blog_app.model.network.RetrofitClient
import com.example.blog_app.ui.theme.Blog_appTheme
import com.example.blog_app.view.blogdetail.BlogDetailScreen
import com.example.blog_app.view.bloglist.BlogListScreen
import com.example.blog_app.viewmodel.BlogViewModel
import com.example.blog_app.viewmodel.BlogViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Blog_appTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BlogApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun BlogApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val repository = remember { BlogRepository(RetrofitClient.api) }
    val blogViewModel: BlogViewModel = viewModel(
        factory = BlogViewModelFactory(repository)
    )
    NavHost(
        navController = navController,
        startDestination = "list",
        modifier = modifier
    ) {
        composable("list") {
            BlogListScreen(
                navController = navController,
                viewModel = blogViewModel)
        }
        composable("detail/{url}",
            arguments = listOf(navArgument("url") {type = NavType.StringType})
        ) {
            backStackEntry ->
            val url = Uri.decode(backStackEntry.arguments?.getString("url") ?: "")
            BlogDetailScreen(url)
        }
    }
}