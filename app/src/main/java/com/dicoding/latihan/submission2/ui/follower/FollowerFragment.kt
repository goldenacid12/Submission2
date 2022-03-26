package com.dicoding.latihan.submission2.ui.follower

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.submission2.api.ApiConfig
import com.dicoding.latihan.submission2.FollowerResponseItem
import com.dicoding.latihan.submission2.ui.detail.DetailActivity
import com.dicoding.latihan.submission2.ui.detail.DetailViewModel
import com.dicoding.latihan.submission2.databinding.FragmentFollowerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //recyclerView setup
        binding = FragmentFollowerBinding.inflate(inflater)
        val layoutManager = LinearLayoutManager(context)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollower.addItemDecoration(itemDecoration)

        //viewModel
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.user1.observe(viewLifecycleOwner) { user1 -> followerData((user1 as ArrayList<FollowerResponseItem>)) }
        detailViewModel.login.observe(viewLifecycleOwner) { login -> followerDetail(login) }
        detailViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }

        //receiveData from DetailActivity
        val bundle = requireActivity().intent.getStringExtra(DetailActivity.EXTRA_DATA)
        val username: String? = bundle
        if (username != null) {
            Log.d("username", username.toString())
            detailViewModel.followerDetail(username)
        }
        return binding.root
    }

    //start fetching data from follower API
    private fun followerDetail(username: String) {
        showLoading(true)
        Log.d("status", username)
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<ArrayList<FollowerResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<FollowerResponseItem>>,
                response: Response<ArrayList<FollowerResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        followerData(responseBody)
                    }
                } else {
                    Log.e("TAG", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowerResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e("TAG", "onFailure: ${t.message}")
            }
        })
    }

    //ProgressBar Visibility
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun followerData(log: ArrayList<FollowerResponseItem>) {
        //assign data to correspondence variable
        val listUserAdapter = FollowerAdapter(log, this)
        val layoutManager = LinearLayoutManager(context)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollower.addItemDecoration(itemDecoration)
        binding.rvFollower.layoutManager = layoutManager
        binding.rvFollower.adapter = listUserAdapter

        //if Recyclerview clicked
        listUserAdapter.setOnItemClickListener(object : FollowerAdapter.OnItemClickListener {
            override fun onItemClick(v: Int) {
            }
        })
    }
}
