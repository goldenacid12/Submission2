package com.dicoding.latihan.submission2.ui.follower

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.latihan.submission2.FollowerResponseItem
import com.dicoding.latihan.submission2.R
import com.dicoding.latihan.submission2.ui.detail.DetailActivity

class FollowerAdapter(
    private val listReview: ArrayList<FollowerResponseItem>,
    private val context: FollowerFragment
) :
    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {
    private lateinit var mListener: OnItemClickListener

    //function when clicked
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(v: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: ViewHolder, position: Int) {
        //assign data to correspondence variable
        val data = listReview[position]
        Log.d("data", data.toString())
        val userLogin = viewholder.tvItem1
        val userAvatar = viewholder.tvItem2
        userLogin.text = data.login
        Glide.with(viewholder.itemView.context).load(data.avatarUrl).circleCrop().into(userAvatar)

        //if RecyclerView clicked
        viewholder.itemView.setOnClickListener {
            val intent = Intent(context.activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, data.login)
            context.startActivity(intent)
        }
    }

    //return list size
    override fun getItemCount(): Int = listReview.size

    //make variable for viewing
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem1: TextView = view.findViewById(R.id.tvUsername)
        val tvItem2: ImageView = view.findViewById(R.id.ivAvatar)
    }
}



