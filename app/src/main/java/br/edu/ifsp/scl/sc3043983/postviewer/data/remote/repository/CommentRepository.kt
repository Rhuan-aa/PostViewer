package br.edu.ifsp.scl.sc3043983.postviewer.data.remote.repository

import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.RetrofitInstance
import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.model.Comment

class CommentRepository {
    suspend fun getComments(postId: Int): List<Comment> =
        RetrofitInstance.api.getComments(postId)
}