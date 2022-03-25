package com.dicoding.latihan.submission2.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.submission2.database.FavoriteUser
import com.dicoding.latihan.submission2.repository.UserRepository

class FavoriteViewModel(application: Application):ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUsers(): LiveData<List<FavoriteUser>> = mUserRepository.getAllUsers()
}