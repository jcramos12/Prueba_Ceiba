package com.ceiba.prueba.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ceiba.prueba.activities.UserPostsActivity
import com.ceiba.prueba.data.entities.User
import com.ceiba.prueba.databinding.ItemUserBinding

class UsersAdapter(val context: Context, private val users: List<User>) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>(), Filterable {

    var usersFilterList: List<User> = users

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setData(usersFilterList[position])
    }

    override fun getItemCount(): Int {
        return usersFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                var resultList = ArrayList<User>()
                if (charSearch.isEmpty()) {
                    resultList = ArrayList(users)
                } else {
                    for (user in users) {
                        println(user.name)
                        if (user.name.lowercase().contains(charSearch.lowercase())) {
                            resultList.add(user)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = resultList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                usersFilterList = results?.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private lateinit var user: User

        init{
            binding.btnShowPosts.setOnClickListener(this)
        }

        fun setData(user: User){
            this.user = user
            binding.tvUserName.text = user.name
            binding.tvUserEmail.text = user.email
            binding.tvUserPhone.text = user.phone
        }

        override fun onClick(v: View?) {
            when(v){
                binding.btnShowPosts -> {
                    val intent = Intent(context, UserPostsActivity::class.java)
                    intent.putExtra("userId", user.id)
                    context.startActivity(intent)
                }
            }
        }
    }
}