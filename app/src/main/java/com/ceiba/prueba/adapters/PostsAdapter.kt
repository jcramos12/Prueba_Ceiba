package com.ceiba.prueba.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ceiba.prueba.data.entities.Post
import com.ceiba.prueba.data.entities.User
import com.ceiba.prueba.databinding.ItemPostBinding
import com.ceiba.prueba.databinding.ItemUserBinding

class PostsAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.setData(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(post: Post){
            binding.tvPostTitle.text = post.title
            binding.tvPostBody.text = post.body
        }

    }
}