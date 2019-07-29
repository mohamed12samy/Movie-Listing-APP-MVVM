package com.example.movieslistingmvvm.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieslistingmvvm.SingleLiveEvent;
import com.example.movieslistingmvvm.database.MoviesDatabase;
import com.example.movieslistingmvvm.model.Movie;
import com.example.movieslistingmvvm.model.movieList;
import com.example.movieslistingmvvm.rest.ImovieServices;
import com.example.movieslistingmvvm.rest.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesOperations {

    //api calls
    //database operations

    private static MoviesOperations mMoviesOperations;

    final MutableLiveData<movieList> curr_movies = new SingleLiveEvent<>();
    final MutableLiveData<movieList> top_movies = new SingleLiveEvent<>();
    final MutableLiveData<List<Movie>> fav_movies = new SingleLiveEvent<>();

    private MoviesOperations() {
    }

    public static synchronized MoviesOperations getInstance() {
        if (mMoviesOperations == null) {
            mMoviesOperations = new MoviesOperations();
        }
        return mMoviesOperations;
    }

    //[returning now playing movies]
    public LiveData<movieList> getNowPlayingMovies(final int page) {

        ImovieServices apiService = RestClient.getClient().create(ImovieServices.class);
        Call<movieList> calling = apiService.getCurMovies("94fd0367fe3409f7e819fa65831803ca", page);

        calling.enqueue(new Callback<movieList>() {
            @Override
            public void onResponse(Call<movieList> call, Response<movieList> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAGG", "ERROR :  " + response.raw().body().toString());

                } else if (response.isSuccessful()) {
                    for(Movie m : response.body().getMovies())
                        m.setFavourite(getFavState(m.getTitle()));

                    curr_movies.setValue(response.body());

                    if(page == 1){
                        saveToDatabase(response.body(),"Now_playing");
                    }
                }
            }

            @Override
            public void onFailure(Call<movieList> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());

                if(page == 1)
                    curr_movies.setValue(getNowPlaying());
            }
        });
        return curr_movies;
    }

    public LiveData<movieList> getTopMovies(final int page)
    {
        ImovieServices apiService = RestClient.getClient().create(ImovieServices.class);
        Call<movieList> calling = apiService.getTopMovies("94fd0367fe3409f7e819fa65831803ca", page);

        calling.enqueue(new Callback<movieList>() {
            @Override
            public void onResponse(Call<movieList> call, Response<movieList> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAGG", "ERROR :  " + response.raw().body().toString());

                } else if (response.isSuccessful()) {
                    for(Movie m : response.body().getMovies())
                        m.setFavourite(getFavState(m.getTitle()));

                    top_movies.setValue(response.body());
                    if(page == 1){
                        saveToDatabase(response.body(),"Top_movies");
                    }
                }
            }

            @Override
            public void onFailure(Call<movieList> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());

                if(page == 1)
                    top_movies.setValue(getTop());

            }
        });
        return top_movies;

    }

    private void saveToDatabase(movieList movies, String category){
        movies.setFlag(category);
         MoviesDatabase.getInstance().dao().insertMovies(movies.getMovies());
    }

    private movieList getNowPlaying(){
        List <Movie> movies = MoviesDatabase.getInstance().dao().getNowPlaying();
        movieList m = new movieList();
        m.setMovies(movies);

        return m;
    }

    private movieList getTop(){
        List <Movie> movies = MoviesDatabase.getInstance().dao().getTopMovies();
        movieList m = new movieList();
        m.setMovies(movies);

        return m;
    }

    public void insertFavMovie(String title){
        MoviesDatabase.getInstance().dao().UpdateFav(title);
    }

    public void DeleteFavMovie(String title){
        MoviesDatabase.getInstance().dao().DeleteFav(title);
    }

    public LiveData<List<Movie>> getFavmovies()
    {
        List<Movie> favMovies = MoviesDatabase.getInstance().dao().getFavMovies();
        fav_movies.setValue(favMovies);

        return fav_movies;
    }

    private boolean getFavState(String title){
        return MoviesDatabase.getInstance().dao().getFavState(title);
    }

    public void InsertFavMovie(Movie movie){
        MoviesDatabase.getInstance().dao().insertFavMovie(movie);
    }

    public void deleteFavMovie(Movie movie){
        MoviesDatabase.getInstance().dao().deleteMovie(movie);
    }

}



