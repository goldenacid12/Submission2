package com.dicoding.latihan.submission2.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.latihan.submission2.R
import com.dicoding.latihan.submission2.database.FavoriteUser
import com.dicoding.latihan.submission2.helper.UserDiffCallback

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {
    private val listUsers = ArrayList<FavoriteUser>()
    fun setListUsers(listUsers: List<FavoriteUser>){
        val diffCallback = UserDiffCallback(this.listUsers, listUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUsers.clear()
        this.listUsers.addAll(listUsers)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return FavViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val data = listUsers[position]
        holder.tvItem1.text = data.name
        Glide.with(holder.itemView.context).load(data.profile).circleCrop().into(holder.tvItem2)
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    inner class FavViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvItem1: TextView = view.findViewById(R.id.tvUsername)
        val tvItem2: ImageView = view.findViewById(R.id.ivAvatar)
    }
}