package com.dicoding.latihan.submission2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.submission2.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //recyclerView setup
        binding = FragmentFollowingBinding.inflate(layoutInflater)

        //viewModel
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
        detailViewModel.user2.observe(viewLifecycleOwner) { user2 -> followingData((user2 as ArrayList<FollowingResponseItem>)) }
        detailViewModel.login.observe(viewLifecycleOwner) { login -> followingDetail(login) }
        detailViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }

        //receive data from Detail Activity
        val bundle = requireActivity().intent.getStringExtra(DetailActivity.EXTRA_DATA)
        val username: String? = bundle
        if (username != null) {
            Log.d("username", username.toString())
            detailViewModel.followingDetail(username)
        }
        return binding.root
    }
    //fetch data from following API
    private fun followingDetail(username: String) {
        showLoading(true)
        Log.d("status", username)
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<ArrayList<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<FollowingResponseItem>>,
                response: Response<ArrayList<FollowingResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        followingData(responseBody)
                    }
                } else {
                    Log.e("TAG", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ArrayList<FollowingResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e("TAG", "onFailure: ${t.message}")
            }
        })
    }
    //ProgressBar visibility
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun followingData(log: ArrayList<FollowingResponseItem>) {
        //assign data to correspondence variable
        val listUserAdapter = FollowingAdapter(log, this)
        Log.d("Adapter", log.toString())
        val layoutManager = LinearLayoutManager(context)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)
        binding.rvFollowing.layoutManager = layoutManager
        binding.rvFollowing.adapter = listUserAdapter
        listUserAdapter.setOnItemClickListener(object : FollowingAdapter.OnItemClickListener {
            override fun onItemClick(v: Int) {
            }
        })
    }

}
