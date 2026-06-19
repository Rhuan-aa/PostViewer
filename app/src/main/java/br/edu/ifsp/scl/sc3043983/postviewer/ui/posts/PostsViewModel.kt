package br.edu.ifsp.scl.sc3043983.postviewer.ui.posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sc3043983.postviewer.data.local.AppDatabase
import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.repository.CommentRepository
import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.repository.PostRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PostsViewModel(
    private val repository: PostRepository = PostRepository(),
    application: Application
) : ViewModel() {

    private val commentRepository = CommentRepository(
        AppDatabase.getInstance(application).localCommentDao()
    )
    private val _uiState = MutableStateFlow<PostsUiState>(PostsUiState.Loading)
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = PostsUiState.Loading
            try {
                val posts = repository.getPosts()
                posts.forEach { post ->
                    val apiComments = commentRepository.getApiComments(post.id)
                    val localComments = commentRepository.getLocalComments(post.id).first()
                    post.numComments = apiComments.size + localComments.size
                }
                _uiState.value = PostsUiState.Success(posts)
            } catch (e: Exception) {
                _uiState.value = PostsUiState.Error(
                    e.message ?: "Erro ao carregar os posts"
                )
            }
        }
    }
}