package com.dicoding.latihan.submission2.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//setup database class
@Entity(tableName = "fav_user")
@Parcelize
data class FavoriteUser(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "login")
    var name: String? = null,

    @ColumnInfo(name = "avatar")
    var avatar: String? = null
):Parcelable