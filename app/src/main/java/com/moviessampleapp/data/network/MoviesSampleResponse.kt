package com.moviessampleapp.data.network

import com.moviessampleapp.data.Search


data class MoviesSampleResponse (

    //val data = listOf<Movie>()
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)