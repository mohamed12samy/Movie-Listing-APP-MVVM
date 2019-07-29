package com.example.movieslistingmvvm.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieslistingmvvm.ClickListener;
import com.example.movieslistingmvvm.R;
import com.example.movieslistingmvvm.RecyclerViewAdapter;
import com.example.movieslistingmvvm.model.Movie;
import com.example.movieslistingmvvm.viewModel.FavMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavouritsFragment extends Fragment implements ClickListener {

    private FavMoviesViewModel mFavMoviesViewModel = new FavMoviesViewModel();

    private RecyclerView recyclerView;

    private List<Movie> moMvieList = new ArrayList<>();

    private RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(getActivity(), moMvieList,this);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourits, container, false);

        recyclerView = view.findViewById(R.id.fav_movies_recycleerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        mFavMoviesViewModel = ViewModelProviders.of(this).get(FavMoviesViewModel.class);

        mFavMoviesViewModel.getFavMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movieList) {
                moMvieList.addAll(movieList);
                mAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void clickListen(Movie movie) {
        if(movie.isFavourite())
            mFavMoviesViewModel.insertFav(movie.getTitle());

        else if(!movie.isFavourite()){

            mFavMoviesViewModel.deleteFav(movie.getTitle());

            moMvieList.remove(movie);
            mAdapter.notifyDataSetChanged();

        }
    }
}

