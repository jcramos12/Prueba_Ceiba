package com.ceiba.prueba.data.api

import com.ceiba.prueba.data.entities.Post
import com.ceiba.prueba.data.entities.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("users")
    fun getUsers() : Call<List<User>>

    @GET("posts")
    fun getUserPosts(@Query("userId") id: Int) : Call<List<Post>>

}