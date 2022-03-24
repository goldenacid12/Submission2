package com.dicoding.latihan.submission2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query ("SELECT * from FavoriteUser ORDER BY login ASC")
    fun getAllUsers(): LiveData<List<FavoriteUser>>
}