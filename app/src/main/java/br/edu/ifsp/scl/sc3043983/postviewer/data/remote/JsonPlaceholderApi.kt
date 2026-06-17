package br.edu.ifsp.scl.sc3043983.postviewer.data.remote

import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.model.Comment
import br.edu.ifsp.scl.sc3043983.postviewer.data.remote.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderApi {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") postId: Int): List<Comment>

}