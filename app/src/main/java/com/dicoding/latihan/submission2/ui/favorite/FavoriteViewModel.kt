package com.dicoding.latihan.submission2.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.latihan.submission2.database.FavoriteUser
import com.dicoding.latihan.submission2.database.UserDao
import com.dicoding.latihan.submission2.database.UserRoomDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    //database function setup
    private var favDatabase: UserRoomDatabase? = UserRoomDatabase.getDatabase(application)
    private var mUserDao: UserDao? = favDatabase?.userDao()

    //function to get favorite user for database
    fun getFavorite(): LiveData<List<FavoriteUser>>?= mUserDao?.getFavorite()
}