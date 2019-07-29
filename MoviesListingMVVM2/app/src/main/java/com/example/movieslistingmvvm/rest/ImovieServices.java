package com.example.movieslistingmvvm.rest;



import com.example.movieslistingmvvm.model.movieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImovieServices {

    @GET("/3/movie/now_playing")
    Call<movieList> getCurMovies(@Query("api_key") String api_key,
                                 @Query("page") int page
    );


    @GET("/3/movie/top_rated")
    Call<movieList> getTopMovies(@Query("api_key") String api_key,
                                 @Query("page") int page
    );

}
