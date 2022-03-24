package com.dicoding.latihan.submission2.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.submission2.api.ApiConfig
import com.dicoding.latihan.submission2.ItemsItem
import com.dicoding.latihan.submission2.repository.UserRepository
import com.dicoding.latihan.submission2.UserResponse
import retrofit2.Call
import retrofit2.Response

class MainViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)
    //initiate variable for checking setUserData
    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user

    //Initiate from Receiving Data
    private var _key: String = ""

    //initiate variable for checking data in function findUser
    private val _login = MutableLiveData<String>()
    val login: LiveData<String> = _login

    //initiate variable for checking data in showLoading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //function for ViewModel getUser when the App start
    private fun getUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser()
        client.enqueue(object : retrofit2.Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                    Log.d("message", "${response.body()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    init {
        //start getUser() when app start
        getUser()
    }

    //function for ViewModel findUser
    fun findUser(query: String) {
        _isLoading.value = true
        _key = query
        Log.d("class", "_key")
        val client = ApiConfig.getApiService().getUser(_key)
        client.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    //making Variable TAG
    companion object {
        private const val TAG = "MainViewModel"
    }
}