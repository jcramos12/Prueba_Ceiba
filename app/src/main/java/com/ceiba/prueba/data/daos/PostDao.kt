package com.ceiba.prueba.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ceiba.prueba.data.entities.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    fun getAll(): List<Post>

    @Query("SELECT * FROM post WHERE userId = :userId")
    fun getUserPosts(userId: Int): List<Post>

    @Insert
    fun insertAll(vararg posts: Post)

}