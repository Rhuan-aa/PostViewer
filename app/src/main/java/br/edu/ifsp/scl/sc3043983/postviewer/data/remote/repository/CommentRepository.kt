package br.edu.ifsp.scl.sc3043983.postviewer.data.remote.repository

import br.edu.ifsp.scl.sc3043983.postviewer.data.local.LocalCommentDao
import br.edu.ifsp.scl.sc3043983.postviewer.data.local.LocalCommentEntity
import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.RetrofitInstance
import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.model.Comment
import kotlinx.coroutines.flow.Flow

class CommentRepository(
    private val localCommentDao: LocalCommentDao
) {
    suspend fun getApiComments(postId: Int): List<Comment> =
        RetrofitInstance.api.getComments(postId)

    fun getLocalComments(postId: Int): Flow<List<LocalCommentEntity>> =
        localCommentDao.getByPost(postId)

    suspend fun addLocalComment(postId: Int, body: String) =
        localCommentDao.insert(LocalCommentEntity(postId = postId, body = body))
}