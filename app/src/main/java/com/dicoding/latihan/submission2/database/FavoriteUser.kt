package com.dicoding.latihan.submission2.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "login")
    var name: String? = null,

    @ColumnInfo(name = "profile")
    var profile: String? = null
):Parcelable