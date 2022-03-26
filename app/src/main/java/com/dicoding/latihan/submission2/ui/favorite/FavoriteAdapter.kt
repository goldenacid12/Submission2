package com.dicoding.latihan.submission2.ui.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.latihan.submission2.R
import com.dicoding.latihan.submission2.database.FavoriteUser
import com.dicoding.latihan.submission2.ui.detail.DetailActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {
    private var listUser = ArrayList<FavoriteUser>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return FavViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        //assign data to correspondence variable

        val data = listUser[position]
        Log.d("data", "$data.name")
        holder.tvItem1.text = data.name
        Glide.with(holder.itemView.context).load(data.avatar).circleCrop().into(holder.tvItem2)

        //when recyclerview card view clicked
        holder.userClick.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, data.name)
            intent.putExtra(DetailActivity.EXTRA_FAV, data.id)
            intent.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    //set list from database
    @SuppressLint("NotifyDataSetChanged")
    fun setList(users: ArrayList<FavoriteUser>){
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    //make variable for viewing
    inner class FavViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvItem1: TextView = view.findViewById(R.id.tvUsername)
        val tvItem2: ImageView = view.findViewById(R.id.ivAvatar)
        val userClick: ConstraintLayout = view.findViewById(R.id.userClick)
    }
}