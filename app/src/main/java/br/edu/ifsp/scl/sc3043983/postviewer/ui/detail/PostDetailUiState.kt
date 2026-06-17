package br.edu.ifsp.scl.sc3043983.postviewer.ui.detail

import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.model.Comment

sealed interface PostDetailUiState {
    data object Loading : PostDetailUiState
    data class Success(val comments: List<Comment>) : PostDetailUiState
    data class Error(val message: String) : PostDetailUiState
}