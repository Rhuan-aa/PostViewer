package br.edu.ifsp.scl.sc3043983.postviewer.data.remote.model

data class Comment (
    val id: Int,
    val postId: Int,
    val name: String,
    val email : String,
    val body: String
)