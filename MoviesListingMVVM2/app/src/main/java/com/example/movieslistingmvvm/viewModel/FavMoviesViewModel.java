package com.example.movieslistingmvvm.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieslistingmvvm.model.Movie;
import com.example.movieslistingmvvm.repository.MoviesOperations;

import java.util.List;

public class FavMoviesViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> FavMovies = new MutableLiveData<>();
    private MoviesOperations mMoviesOperations;

    public FavMoviesViewModel(){
        mMoviesOperations = MoviesOperations.getInstance();
    }

    public LiveData<List<Movie>> getFavMovies(){

        FavMovies = (MutableLiveData<List<Movie>>) mMoviesOperations.getFavmovies();
//        Log.d("okok",FavMovies.getValue().get(0).getTitle());

        return FavMovies;
    }

    public void insertFav(String title){
        mMoviesOperations.insertFavMovie(title);

    }

    public void deleteFav(String title){

        mMoviesOperations.DeleteFavMovie(title);

    }

}
