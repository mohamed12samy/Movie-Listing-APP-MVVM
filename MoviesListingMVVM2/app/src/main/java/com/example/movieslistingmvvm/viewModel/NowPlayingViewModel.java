package com.example.movieslistingmvvm.viewModel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieslistingmvvm.SingleLiveEvent;
import com.example.movieslistingmvvm.model.Movie;
import com.example.movieslistingmvvm.model.movieList;
import com.example.movieslistingmvvm.repository.MoviesOperations;


public class NowPlayingViewModel extends ViewModel {

    private MutableLiveData<movieList> moviesToobserve = new SingleLiveEvent<>();//MutableLiveData<>();
    private MoviesOperations mMoviesOperations;

    public NowPlayingViewModel(){
        mMoviesOperations = MoviesOperations.getInstance();
    }

    public LiveData<movieList> getNowPlayingMovies(int page){
        moviesToobserve = (SingleLiveEvent<movieList>) mMoviesOperations.getNowPlayingMovies(page);

        return moviesToobserve;
    }

    public void insertFav(Movie movie/*String title*/){
        mMoviesOperations.InsertFavMovie(movie);

    }

    public void deleteFav(String title){

        mMoviesOperations.DeleteFavMovie(title);
    }

}
