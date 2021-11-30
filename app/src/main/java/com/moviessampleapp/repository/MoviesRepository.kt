package com.moviessampleapp.repository

import com.moviessampleapp.data.Search
import com.moviessampleapp.data.network.MoviesSampleApi
import javax.inject.Inject



class MoviesRepository @Inject constructor(private val api: MoviesSampleApi) {

    suspend fun retrieveMovies(): List<Search> {
        return api.getMoviesSample().Search
    }
}