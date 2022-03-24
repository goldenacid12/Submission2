package com.dicoding.latihan.submission2.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.submission2.repository.UserRepository
import com.dicoding.latihan.submission2.database.FavoriteUser

class UserAddUpdateViewModel(application: Application): ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun insert(user: FavoriteUser){
        mUserRepository.insert(user)
    }

    fun delete(user: FavoriteUser){
        mUserRepository.delete(user)
    }
}