package br.edu.ifsp.scl.sc3043983.postviewer.data.local

import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface LocalCommentDao {

    @Insert
    suspend fun insert(comment: LocalCommentEntity)

    @Query("SELECT * FROM local_comments WHERE postId = :postId ORDER BY id DESC")
    fun getByPost(postId: Int): Flow<List<LocalCommentEntity>>
}