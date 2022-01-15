package com.ceiba.prueba.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ceiba.prueba.data.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Int): User

    @Insert
    fun insertAll(vararg users: User)

}