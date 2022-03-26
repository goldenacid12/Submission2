package com.dicoding.latihan.submission2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    //for inputting favorite
    fun insertFavorite(favoriteUser: FavoriteUser)

    //for get favorite list
    @Query ("SELECT * FROM fav_user")
    fun getFavorite() : LiveData<List<FavoriteUser>>

    //for checking user already favorited or not
    @Query ("SELECT Count(*) FROM fav_user WHERE fav_user.id = :id")
    fun checkFavorite (id: Int): Int

    //for remove favorite
    @Query ("DELETE FROM fav_user WHERE fav_user.id = :id")
    fun removeFavorite (id:Int): Int
}