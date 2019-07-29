package com.example.movieslistingmvvm.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieslistingmvvm.SingleLiveEvent;
import com.example.movieslistingmvvm.model.Movie;
import com.example.movieslistingmvvm.model.movieList;
import com.example.movieslistingmvvm.repository.MoviesOperations;

public class TopMoviesViewModel extends ViewModel {

    private MutableLiveData<movieList> moviesToobserve = new SingleLiveEvent<>();
    private MoviesOperations mMoviesOperations;

    public TopMoviesViewModel(){
        mMoviesOperations = MoviesOperations.getInstance();
    }

    public LiveData<movieList> getTopMovies(int page){
        moviesToobserve = (MutableLiveData<movieList>) mMoviesOperations.getTopMovies(page);

        return moviesToobserve;
    }

    public void insertFav(Movie movie){
        mMoviesOperations.InsertFavMovie(movie);

    }

    public void deleteFav(String title){

        mMoviesOperations.DeleteFavMovie(title);
    }

}
