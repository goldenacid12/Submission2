package com.dicoding.latihan.submission2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //get for mainActivity before search
    @GET("users")
    fun getUser(
    ): Call<ArrayList<ItemsItem>>

    //get for mainActivity after search
    @GET("search/users")
    fun getUser(
        @Query("q") q: String
    ): Call<UserResponse>

    //get data for DetailActivity
    @GET("users/{id}")
    fun getDetail(
        @Path("id") id: String
    ): Call<DetailUserResponse>

    //get follower for Follower Fragment
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<FollowerResponseItem>>

    //get follower for Following Fragment
    @GET("users/{id}/following")
    fun getFollowing(
        @Path("id") id: String
    ): Call<ArrayList<FollowingResponseItem>>
}