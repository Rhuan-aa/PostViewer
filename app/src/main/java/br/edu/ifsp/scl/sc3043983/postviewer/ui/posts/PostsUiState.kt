package br.edu.ifsp.scl.sc3043983.postviewer.ui.posts

import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.model.Post

sealed interface PostsUiState {
    data object Loading : PostsUiState
    data class Success(val posts: List<Post>) : PostsUiState
    data class Error(val message: String) : PostsUiState
}