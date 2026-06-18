package br.edu.ifsp.scl.sc3043983.postviewer.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    postId: Int,
    viewModel: PostDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(postId) {
        viewModel.loadComments(postId)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Comentários do post #$postId") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is PostDetailUiState.Loading -> CircularProgressIndicator()
                is PostDetailUiState.Error -> ErrorContent(
                    message = state.message,
                    onRetry = { viewModel.loadComments(postId) }
                )
                is PostDetailUiState.Success -> CommentList(comments = state.comments)
            }
        }
    }

}
@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = message)
        Button(onClick = onRetry, modifier = Modifier.padding(top = 8.dp)) {
            Text("Tentar novamente")
        }
    }
}

@Composable
private fun CommentList(comments: List<CommentUi>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(comments, key = { it.id }) { comment ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = comment.author,
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (comment.isLocal) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "local",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = comment.body,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            HorizontalDivider()
        }
    }
}


