package br.edu.ifsp.scl.sc3043983.postviewer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_comments")
data class LocalCommentEntity (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val postId: Int,
    val body: String
)