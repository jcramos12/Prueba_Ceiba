package com.ceiba.prueba.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceiba.prueba.R
import com.ceiba.prueba.adapters.EmptyDataObserver
import com.ceiba.prueba.adapters.UsersAdapter
import com.ceiba.prueba.data.AppDatabase
import com.ceiba.prueba.data.api.APIClient
import com.ceiba.prueba.data.entities.User
import com.ceiba.prueba.databinding.ActivityUsersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class UsersActivity : AppCompatActivity(), Callback<List<User>>, TextWatcher {

    private lateinit var binding: ActivityUsersBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = AppDatabase(this)
        getUsersList()
    }

    private fun getUsersList(){
        binding.rvUsersList.visibility = View.GONE
        binding.llUsersLoading.visibility = View.VISIBLE
        thread {
            val users = db.UserDao().getAll()
            runOnUiThread {
                if(users.isEmpty()){
                    APIClient.getUsers(this)
                }else {
                    fillUsersList(users)
                }
            }
        }
    }

    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
        thread {
            db.UserDao().insertAll(*response.body()!!.toTypedArray())
            val users = db.UserDao().getAll()
            runOnUiThread {
                fillUsersList(users)
            }
        }
    }

    override fun onFailure(call: Call<List<User>>, t: Throwable) {
        binding.llUsersLoading.visibility = View.GONE
        binding.rvUsersList.visibility = View.GONE
        binding.tvEmptyList.visibility = View.VISIBLE
        Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_LONG).show()
    }

    private fun fillUsersList(users: List<User>){
        binding.llUsersLoading.visibility = View.GONE
        binding.rvUsersList.visibility = View.VISIBLE
        binding.rvUsersList.layoutManager = LinearLayoutManager(this)
        val adapter = UsersAdapter(this, users)
        binding.rvUsersList.adapter = adapter
        adapter.registerAdapterDataObserver(EmptyDataObserver(binding.rvUsersList, binding.tvEmptyList))
        binding.tietUsersSearch.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        (binding.rvUsersList.adapter as UsersAdapter).filter.filter(s)
    }

    override fun afterTextChanged(s: Editable?) {
    }

}