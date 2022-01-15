package com.ceiba.prueba.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ceiba.prueba.data.daos.PostDao
import com.ceiba.prueba.data.daos.UserDao
import com.ceiba.prueba.data.entities.Post
import com.ceiba.prueba.data.entities.User

@Database(
    entities = [User::class, Post::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun UserDao() : UserDao
    abstract fun PostDao() : PostDao

    companion object{
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "database.db")
            .build()
    }

}