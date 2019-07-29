package com.example.movieslistingmvvm.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class movieList {

        @SerializedName("results")
        private List<Movie> movies = new ArrayList<>();


    public List<Movie> getMovies() {
        return movies;
    }

    public void setFlag(String flag){

        for(Movie m : movies)
        {
            m.setFlag(flag);
        }

    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    // ...

}
