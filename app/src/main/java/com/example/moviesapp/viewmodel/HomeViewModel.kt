package com.example.moviesapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.model.MoviesResult
import com.example.moviesapp.retrofit.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "HomeViewModel"

class HomeViewModel : ViewModel() {
    private val mutablePopularMovies = MutableLiveData<MoviesResult>()

    init {
        getPopularMovies()
    }

    fun observePopularMovies(): LiveData<MoviesResult> = mutablePopularMovies

    private fun getPopularMovies() {
        RetrofitClientInstance.moviesApi.getPopularMovies()
            .enqueue(object : Callback<MoviesResult?> {
                override fun onResponse(
                    call: Call<MoviesResult?>,
                    response: Response<MoviesResult?>
                ) {
                    mutablePopularMovies.value = response.body()
                }

                override fun onFailure(call: Call<MoviesResult?>, t: Throwable) {
                    Log.d(TAG, "something went wrong: ${t.message.toString()}")
                }
            })
    }
}