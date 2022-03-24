package com.dicoding.latihan.submission2.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.latihan.submission2.api.ApiConfig
import com.dicoding.latihan.submission2.R
import com.dicoding.latihan.submission2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    //make variable for binding RecyclerView
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Tabs Initiation
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        //receive data from Main Activity
        val userLogin = intent.getStringExtra(EXTRA_DATA)

        //start function detail user
        if (userLogin != null) {
            detailUser(userLogin)
        }
    }

    //start fetching data from Detail User API
    private fun detailUser(username: String) {
        showLoading(true)
        Log.d("url", username)
        val client = ApiConfig.getApiService().getDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setDetailData(
                            responseBody.avatarUrl,
                            responseBody.login,
                            responseBody.name,
                            responseBody.company,
                            responseBody.location,
                            responseBody.publicRepos
                        )
                    }
                } else {
                    showLoading(false)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setDetailData(
        profile: String,
        username: String,
        name: String,
        company: String,
        location: String,
        repository: Int
    ) {
        //assign variable to corresponding View
        val tvProfile: ImageView = findViewById(R.id.userProfile)
        val tvUsername: TextView = findViewById(R.id.username)
        val tvName: TextView = findViewById(R.id.name)
        val tvCompany: TextView = findViewById(R.id.company)
        val tvLocation: TextView = findViewById(R.id.location)
        val tvRepository: TextView = findViewById(R.id.reposit_number)

        //assign data and display them
        Glide.with(this).load(profile).circleCrop().into(tvProfile)
        tvUsername.text = username
        tvName.text = name
        tvCompany.text = company
        tvLocation.text = location
        tvRepository.text = repository.toString()
    }

    //ProgressBar Visibility
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    //List Tab Titles, TAG, and Key
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val TAG = "TAG"
        const val EXTRA_DATA = "EXTRA_DATA"
    }
}