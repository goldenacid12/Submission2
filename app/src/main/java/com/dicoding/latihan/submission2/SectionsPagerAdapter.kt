package com.dicoding.latihan.submission2

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    //return Tab Count
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        //call fragment when position
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowerFragment()
        }
        return fragment as Fragment
    }

}