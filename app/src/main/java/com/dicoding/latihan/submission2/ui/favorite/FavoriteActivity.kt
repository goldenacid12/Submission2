package com.dicoding.latihan.submission2.ui.favorite

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.submission2.database.FavoriteUser
import com.dicoding.latihan.submission2.database.UserDao
import com.dicoding.latihan.submission2.database.UserRoomDatabase
import com.dicoding.latihan.submission2.databinding.ActivityFavoriteBinding
import com.dicoding.latihan.submission2.ui.detail.DetailViewModel
import com.dicoding.latihan.submission2.ui.follower.FollowerAdapter
import com.dicoding.latihan.submission2.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter
    private val listFavorite = ArrayList<FavoriteUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //recyclerview setup
        adapter = FavoriteAdapter()
        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorites.setHasFixedSize(true)
        binding.rvFavorites.addItemDecoration(itemDecoration)
        binding.rvFavorites.layoutManager = layoutManager
        binding.rvFavorites.adapter = adapter

        //viewModel setup
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        //actionbar title
        supportActionBar?.title = "Favorite User List"

        //get list
        favoriteViewModel.getFavorite()?.observe(this){
            if (it == null){
                Toast.makeText(this,"No favorite user found", Toast.LENGTH_SHORT).show()
            } else{
                val list = favList(it)
                adapter.setList(list)
            }
        }
    }

    //move list from database to listFavorite
    private fun favList(favorite: List<FavoriteUser>): ArrayList<FavoriteUser>{
        listFavorite.clear()
        for (i in favorite.indices){
            val favoriteList = FavoriteUser(favorite[i].id, favorite[i].name, favorite[i].avatar)
            listFavorite.add(favoriteList)
        }
        return listFavorite
    }
}