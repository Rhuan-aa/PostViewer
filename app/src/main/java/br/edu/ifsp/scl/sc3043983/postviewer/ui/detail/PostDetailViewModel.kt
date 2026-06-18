package br.edu.ifsp.scl.sc3043983.postviewer.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sc3043983.postviewer.data.local.AppDatabase
import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.repository.CommentRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CommentRepository(
        AppDatabase.getInstance(application).localCommentDao()
    )

    private val _uiState = MutableStateFlow<PostDetailUiState>(PostDetailUiState.Loading)
    val uiState: StateFlow<PostDetailUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null

    fun loadComments(postId: Int) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.value = PostDetailUiState.Loading
            try {
                val apiComments = repository.getApiComments(postId).map { comment ->
                    CommentUi(
                        id = "api-${comment.id}",
                        author = comment.name,
                        body = comment.body,
                        isLocal = false
                    )
                }
                repository.getLocalComments(postId).collect { entities ->
                    val localComments = entities.map { comment ->
                        CommentUi(
                            id = "local-${comment.id}",
                            author = "Você",
                            body = comment.body,
                            isLocal = true
                        )
                    }
                    _uiState.value = PostDetailUiState.Success(localComments + apiComments)
                }
            } catch (e: Exception) {
                _uiState.value = PostDetailUiState.Error(
                    e.message ?: "Erro ao carregar os comentários"
                )
            }
        }
    }
}