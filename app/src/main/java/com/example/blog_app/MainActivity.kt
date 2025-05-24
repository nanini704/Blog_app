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
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import com.example.blog_app.ui.theme.Blog_appTheme
import com.example.blog_app.view.blogdetail.BlogDetailScreen
import com.example.blog_app.view.bloglist.BlogListScreen


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

    NavHost(
        navController = navController,
        startDestination = "list",
        modifier = modifier
    ) {
        composable("list") {
            BlogListScreen(navController)
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