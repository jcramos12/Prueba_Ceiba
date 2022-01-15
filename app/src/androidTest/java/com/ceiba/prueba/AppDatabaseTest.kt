package com.ceiba.prueba

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ceiba.prueba.data.AppDatabase
import com.ceiba.prueba.data.daos.PostDao
import com.ceiba.prueba.data.daos.UserDao
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase(){

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var postDao: PostDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = AppDatabase(context)
        userDao = db.UserDao()
        postDao = db.PostDao()
    }

    @Test
    fun readUsers(){
        val users = userDao.getAll()
        assert(users.isNotEmpty())
    }

    @Test
    fun readPosts(){
        val posts = postDao.getAll()
        assert(posts.isNotEmpty())
    }

}