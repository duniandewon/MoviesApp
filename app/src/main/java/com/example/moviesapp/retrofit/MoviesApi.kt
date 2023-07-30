package com.example.moviesapp.retrofit

import com.example.moviesapp.model.MoviesResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface MoviesApi {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzZGMzZTY4ODM5NzFhODgwY2I5YjdiOWVlN2NmMzMzNCIsInN1YiI6IjVlOTMyZjdkY2E0ZjY3NzJjMzdmYTQ0ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.QQdS7X3ndAbkmAXoMDr-A5d1vK9Jjls2JpMxWtwqNzc"
    )
    @GET("popular")
    fun getPopularMovies(): Call<MoviesResult>
}