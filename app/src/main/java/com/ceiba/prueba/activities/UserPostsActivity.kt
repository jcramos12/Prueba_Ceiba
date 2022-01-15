package com.ceiba.prueba.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceiba.prueba.R
import com.ceiba.prueba.adapters.EmptyDataObserver
import com.ceiba.prueba.adapters.PostsAdapter
import com.ceiba.prueba.adapters.UsersAdapter
import com.ceiba.prueba.data.AppDatabase
import com.ceiba.prueba.data.api.APIClient
import com.ceiba.prueba.data.entities.Post
import com.ceiba.prueba.data.entities.User
import com.ceiba.prueba.databinding.ActivityUserPostsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class UserPostsActivity : AppCompatActivity(), Callback<List<Post>> {

    private lateinit var binding: ActivityUserPostsBinding
    private lateinit var db: AppDatabase
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = AppDatabase(this)
        userId = intent.getIntExtra("userId", 0)
        fillUserCard()
        getUserPostsList()
    }

    private fun fillUserCard() {
        thread {
            val user = db.UserDao().getUser(userId)
            runOnUiThread{
                binding.tvPostsUserName.text = user.name
                binding.tvPostsUserEmail.text = user.email
                binding.tvPostsUserPhone.text = user.phone
            }
        }
    }

    private fun getUserPostsList(){
        binding.rvUserPostsList.visibility = View.GONE
        binding.llUserPostsLoading.visibility = View.VISIBLE
        thread {
            val posts = db.PostDao().getUserPosts(userId)
            runOnUiThread {
                if(posts.isEmpty()){
                    APIClient.getUserPosts(this, userId)
                }else {
                    fillUserPostsList(posts)
                }
            }
        }
    }

    override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
        thread {
            db.PostDao().insertAll(*response.body()!!.toTypedArray())
            val posts = db.PostDao().getUserPosts(userId)
            runOnUiThread {
                fillUserPostsList(posts)
            }
        }
    }

    override fun onFailure(call: Call<List<Post>>, t: Throwable) {
        binding.llUserPostsLoading.visibility = View.GONE
        binding.rvUserPostsList.visibility = View.GONE
        binding.tvEmptyList.visibility = View.VISIBLE
        Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_LONG).show()
    }

    private fun fillUserPostsList(posts: List<Post>){
        binding.llUserPostsLoading.visibility = View.GONE
        binding.rvUserPostsList.visibility = View.VISIBLE
        binding.rvUserPostsList.layoutManager = LinearLayoutManager(this)
        val adapter = PostsAdapter(posts)
        binding.rvUserPostsList.adapter = adapter
    }
}