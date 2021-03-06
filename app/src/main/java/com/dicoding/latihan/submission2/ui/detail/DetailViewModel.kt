package com.dicoding.latihan.submission2.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.submission2.api.ApiConfig
import com.dicoding.latihan.submission2.FollowerResponseItem
import com.dicoding.latihan.submission2.FollowingResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    //Initiate variable for checking followerData
    private val _user1 = MutableLiveData<List<FollowerResponseItem>>()
    val user1: LiveData<List<FollowerResponseItem>> = _user1

    //Initiate variable for checking followingData
    private val _user2 = MutableLiveData<List<FollowingResponseItem>>()
    val user2: LiveData<List<FollowingResponseItem>> = _user2

    //Initiate from receiving data
    private var _key: String = ""

    //Initiate variable for checking data in function followerDetail adn followingDetail
    private val _login = MutableLiveData<String>()
    val login: LiveData<String> = _login

    //Initiate variable for checking data in showLoading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //function for ViewModel followerDetail
    fun followerDetail(username: String) {
        _isLoading.value = true
        _key = username
        val client = ApiConfig.getApiService().getFollowers(_key)
        client.enqueue(object : Callback<ArrayList<FollowerResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<FollowerResponseItem>>,
                response: Response<ArrayList<FollowerResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user1.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowerResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    //function for ViewModel followingDetail
    fun followingDetail(username: String) {
        _isLoading.value = false
        _key = username
        val client = ApiConfig.getApiService().getFollowing(_key)
        client.enqueue(object : Callback<ArrayList<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<FollowingResponseItem>>,
                response: Response<ArrayList<FollowingResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user2.value = response.body()
                } else {
                    Log.e("TAG", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("TAG", "onFailure: ${t.message}")
            }
        })
    }

    //making variable TAG
    companion object {
        private const val TAG = "DetailViewModel"
    }
}