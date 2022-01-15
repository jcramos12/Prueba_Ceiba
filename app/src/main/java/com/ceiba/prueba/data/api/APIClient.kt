package com.ceiba.prueba.data.api

import com.ceiba.prueba.data.entities.Post
import com.ceiba.prueba.data.entities.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    companion object{
        private const val baseURL = "https://jsonplaceholder.typicode.com/"

        fun getClient(): APIInterface {
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(APIInterface::class.java)
        }

        fun getUsers(callback: Callback<List<User>>) {
            getClient().getUsers().enqueue(callback)
        }

        fun getUserPosts(callback: Callback<List<Post>>, userId: Int) {
            getClient().getUserPosts(userId).enqueue(callback)
        }
    }

}