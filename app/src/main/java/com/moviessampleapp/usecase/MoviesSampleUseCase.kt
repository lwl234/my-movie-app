package com.moviessampleapp.usecase

import com.moviessampleapp.data.Search
import com.moviessampleapp.repository.MoviesRepository
import javax.inject.Inject

class MoviesSampleUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(): List<Search> {
        return moviesRepository.retrieveMovies().sortedBy { it.Title }
    }

}