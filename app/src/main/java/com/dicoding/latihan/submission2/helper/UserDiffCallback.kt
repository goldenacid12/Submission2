package com.dicoding.latihan.submission2.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.latihan.submission2.database.FavoriteUser

class UserDiffCallback(
    private val mOldUserList: List<FavoriteUser>,
    private val mNewUserList: List<FavoriteUser>):
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].id  == mNewUserList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldUserList[oldItemPosition]
        val newEmployee = mNewUserList[newItemPosition]
        return oldEmployee.name == newEmployee.name && oldEmployee.profile == newEmployee.profile
    }

}