package br.edu.ifsp.scl.sc3043983.postviewer.ui.detail

sealed interface PostDetailUiState {
    data object Loading : PostDetailUiState
    data class Success(val comments: List<CommentUi>) : PostDetailUiState
    data class Error(val message: String) : PostDetailUiState
}