package br.edu.ifsp.scl.sc3043983.postviewer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.repository.CommentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val repository: CommentRepository = CommentRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<PostDetailUiState>(PostDetailUiState.Loading)
    val uiState: StateFlow<PostDetailUiState> = _uiState.asStateFlow()

    fun loadComments(postId: Int) {
        viewModelScope.launch {
            _uiState.value = PostDetailUiState.Loading
            try {
                val comments = repository.getComments(postId)
                _uiState.value = PostDetailUiState.Success(comments)
            } catch (e: Exception) {
                _uiState.value = PostDetailUiState.Error(
                    e.message ?: "Erro ao carregar os comentários"
                )
            }
        }
    }
}