package com.dicoding.latihan.submission2.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.latihan.submission2.database.FavoriteUser
import com.dicoding.latihan.submission2.database.UserDao
import com.dicoding.latihan.submission2.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUsersDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUsersDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<FavoriteUser>> = mUsersDao.getAllUsers()

    fun insert (user: FavoriteUser){
        executorService.execute { mUsersDao.insert(user) }
    }

    fun delete (user: FavoriteUser){
        executorService.execute { mUsersDao.delete(user) }
    }
}