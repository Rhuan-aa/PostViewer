package br.edu.ifsp.scl.sc3043983.postviewer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.edu.ifsp.scl.sc3043983.postviewer.ui.detail.PostDetailScreen
import br.edu.ifsp.scl.sc3043983.postviewer.ui.posts.PostScreen

object Routes {
    const val POSTS = "posts"
    const val POST_DETAIL = "posts/{postId}"

    fun postDetail(postId: Int) = "posts/$postId"

    @Composable
    fun PostViewerNavHost() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = POSTS
        ) {
            composable(POSTS) {
                PostScreen(
                    onPostClick = { postId ->
                        navController.navigate(postDetail(postId))
                    }
                )
            }

            composable(
                route = POST_DETAIL,
                arguments = listOf(navArgument("postId") { type = NavType.Companion.IntType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getInt("postId") ?: 0
                PostDetailScreen(postId = postId)
            }
        }
    }
}