package com.moviessampleapp.data.network

import retrofit2.http.GET

interface MoviesSampleApi {
    //?apikey=b9bd48a6&s=Marvel&type=movie
    @GET("/?apikey=b9bd48a6&s=Marvel&type=movie")
        //("?apikey=b9bd48a6&s=Marvel&type=movie")
        //("/api/movies")
    suspend fun getMoviesSample(): MoviesSampleResponse

}