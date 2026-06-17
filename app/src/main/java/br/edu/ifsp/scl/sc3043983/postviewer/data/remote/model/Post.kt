package br.edu.ifsp.scl.sc3043983.postviewer.data.remote.model

data class Post (
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)