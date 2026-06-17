package br.edu.ifsp.scl.sc3043983.postviewer.data.remote.repository

import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.RetrofitInstance
import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.model.Post

class PostRepository {
    suspend fun getPosts(): List<Post> = RetrofitInstance.api.getPosts()
}