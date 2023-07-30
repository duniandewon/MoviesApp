package com.example.moviesapp.model

data class MoviesResult(
    val dates: Dates?,
    val results: Movies,
    val page: Int,
    val total_pages: Int,
    val total_results: Int
)